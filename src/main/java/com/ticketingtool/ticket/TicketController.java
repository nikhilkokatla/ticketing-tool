
package com.ticketingtool.ticket;

import com.ticketingtool.ticket.dto.TicketResponse;
import com.ticketingtool.user.User;
import com.ticketingtool.user.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final UserRepository userRepository;

    public TicketController(TicketService ticketService,
                            UserRepository userRepository) {
        this.ticketService = ticketService;
        this.userRepository = userRepository;
    }

    // Agents can view all tickets
    @GetMapping
    public List<TicketResponse> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // Customers can view their own tickets
    @GetMapping("/my-tickets")
    public ResponseEntity<List<TicketResponse>> getMyTickets(
            Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<TicketResponse> tickets =
                ticketService.getTicketsByUserId(user.getId());

        return ResponseEntity.ok(tickets);
    }

    // View individual ticket
    // Agents can view any ticket; Customers only their own
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(
            @PathVariable Long id,
            Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (user.getRole() == com.ticketingtool.user.UserRole.AGENT) {
            return ticketService.getTicketById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        return ticketService.getTicketByIdAndUserId(id, user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create ticket for the logged-in Customer
    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @RequestBody Ticket ticket,
            Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Set owner from authenticated user, not request body
        ticket.setUser(user);

        TicketResponse savedTicket =
                ticketService.saveTicket(ticket);

        return ResponseEntity.ok(savedTicket);
    }

    // Update ticket - Agent only
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> updateTicket(
            @PathVariable Long id,
            @RequestBody Ticket ticket) {

        return ticketService.updateTicket(id, ticket)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete ticket - Agent only
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(
            @PathVariable Long id) {

        if (ticketService.getTicketById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ticketService.deleteTicket(id);

        return ResponseEntity.noContent().build();
    }

    // Assign ticket to an Agent
    @PutMapping("/{ticketId}/assign/{agentId}")
    public ResponseEntity<TicketResponse> assignTicket(
            @PathVariable Long ticketId,
            @PathVariable Long agentId) {

        return ticketService.assignTicket(ticketId, agentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
