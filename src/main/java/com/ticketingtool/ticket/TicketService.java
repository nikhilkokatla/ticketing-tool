package com.ticketingtool.ticket;

import com.ticketingtool.ticket.dto.TicketResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // Convert Ticket entity into TicketResponse DTO
    private TicketResponse convertToResponse(Ticket ticket) {

        return new TicketResponse(
                ticket.getId(),
                ticket.getSubject(),
                ticket.getDescription(),
                ticket.getPriority(),
                ticket.getStatus(),
                ticket.getUser().getId(),
                ticket.getUser().getName(),
                ticket.getAssignedTo() != null
                        ? ticket.getAssignedTo().getId()
                        : null,
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
        );
    }

    // Create ticket
    public TicketResponse saveTicket(Ticket ticket) {

        Ticket savedTicket = ticketRepository.save(ticket);

        return convertToResponse(savedTicket);
    }

    // Get all tickets
    public List<TicketResponse> getAllTickets() {

        return ticketRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Get ticket by ID
    public Optional<TicketResponse> getTicketById(Long id) {

        return ticketRepository.findById(id)
                .map(this::convertToResponse);
    }

    // Update ticket
    public Optional<TicketResponse> updateTicket(
            Long id, Ticket ticketDetails) {

        return ticketRepository.findById(id)
                .map(existingTicket -> {

                    existingTicket.setSubject(ticketDetails.getSubject());
                    existingTicket.setDescription(ticketDetails.getDescription());
                    existingTicket.setPriority(ticketDetails.getPriority());
                    existingTicket.setStatus(ticketDetails.getStatus());

                    Ticket updatedTicket =
                            ticketRepository.save(existingTicket);

                    return convertToResponse(updatedTicket);
                });
    }

    // Delete ticket
    public void deleteTicket(Long id) {

        ticketRepository.deleteById(id);
    }
}
