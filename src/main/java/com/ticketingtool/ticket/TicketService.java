
package com.ticketingtool.ticket;

import com.ticketingtool.ticket.dto.TicketResponse;
import com.ticketingtool.user.User;
import com.ticketingtool.user.UserRepository;
import com.ticketingtool.user.UserRole;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    // Convert Ticket entity into TicketResponse
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

    // Get tickets created by a specific customer
    public List<TicketResponse> getTicketsByUserId(Long userId) {
        return ticketRepository.findByUserId(userId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Get ticket only if it belongs to the specified customer
    public Optional<TicketResponse> getTicketByIdAndUserId(
            Long ticketId, Long userId) {

        return ticketRepository.findById(ticketId)
                .filter(ticket ->
                        ticket.getUser().getId().equals(userId))
                .map(this::convertToResponse);
    }

    // Update ticket
    public Optional<TicketResponse> updateTicket(
            Long id, Ticket ticketDetails) {

        return ticketRepository.findById(id).map(existingTicket -> {

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

    // Assign ticket to an Agent
    public Optional<TicketResponse> assignTicket(
            Long ticketId, Long agentId) {

        Optional<Ticket> ticketOptional =
                ticketRepository.findById(ticketId);

        if (ticketOptional.isEmpty()) {
            return Optional.empty();
        }

        Optional<User> agentOptional =
                userRepository.findById(agentId);

        if (agentOptional.isEmpty()) {
            throw new IllegalArgumentException("Agent not found");
        }

        User agent = agentOptional.get();

        if (agent.getRole() != UserRole.AGENT) {
            throw new IllegalArgumentException(
                    "Only users with AGENT role can be assigned tickets");
        }

        Ticket ticket = ticketOptional.get();

        ticket.setAssignedTo(agent);

        Ticket updatedTicket =
                ticketRepository.save(ticket);

        return Optional.of(convertToResponse(updatedTicket));
    }
}
