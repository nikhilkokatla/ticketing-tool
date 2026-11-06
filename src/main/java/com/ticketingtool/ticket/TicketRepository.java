
package com.ticketingtool.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUserId(Long userId);

    // Count tickets by status
    long countByStatus(TicketStatus status);

    long countByUserId(Long userId);

    long countByUserIdAndStatus(Long userId, TicketStatus status);

    // Count tickets by priority
    long countByPriority(TicketPriority priority);

    long countByUserIdAndPriority(Long userId, TicketPriority priority);
}
