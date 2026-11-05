
package com.ticketingtool.ticket;

import com.ticketingtool.ticket.dto.TicketResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // Get all tickets
    @GetMapping
    public List<TicketResponse> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // Get ticket by ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(
            @PathVariable Long id) {

        return ticketService.getTicketById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create ticket
    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @RequestBody Ticket ticket) {

        TicketResponse savedTicket = ticketService.saveTicket(ticket);
        return ResponseEntity.ok(savedTicket);
    }

    // Update ticket
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> updateTicket(
            @PathVariable Long id,
            @RequestBody Ticket ticket) {

        return ticketService.updateTicket(id, ticket)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(
            @PathVariable Long id) {

        if (ticketService.getTicketById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}