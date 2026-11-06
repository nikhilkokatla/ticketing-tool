
package com.ticketingtool.dashboard;

import com.ticketingtool.ticket.TicketRepository;
import com.ticketingtool.ticket.TicketStatus;
import com.ticketingtool.ticket.TicketPriority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private TicketRepository ticketRepository;

    // Agent Dashboard
    public DashboardResponse getDashboardStatistics() {

        long totalTickets = ticketRepository.count();

        long todoTickets =
                ticketRepository.countByStatus(TicketStatus.TO_DO);

        long inProgressTickets =
                ticketRepository.countByStatus(TicketStatus.IN_PROGRESS);

        long completedTickets =
                ticketRepository.countByStatus(TicketStatus.DONE);

        long highPriority =
                ticketRepository.countByPriority(TicketPriority.HIGH);

        long mediumPriority =
                ticketRepository.countByPriority(TicketPriority.MEDIUM);

        long lowPriority =
                ticketRepository.countByPriority(TicketPriority.LOW);

        return new DashboardResponse(
                totalTickets,
                todoTickets,
                inProgressTickets,
                completedTickets,
                highPriority,
                mediumPriority,
                lowPriority
        );
    }

    // Customer Dashboard
    public DashboardResponse getCustomerDashboardStatistics(Long userId) {

        long totalTickets = ticketRepository.countByUserId(userId);

        long todoTickets = ticketRepository.countByUserIdAndStatus(
                userId, TicketStatus.TO_DO);

        long inProgressTickets = ticketRepository.countByUserIdAndStatus(
                userId, TicketStatus.IN_PROGRESS);

        long completedTickets = ticketRepository.countByUserIdAndStatus(
                userId, TicketStatus.DONE);

        long highPriority = ticketRepository.countByUserIdAndPriority(
                userId, TicketPriority.HIGH);

        long mediumPriority = ticketRepository.countByUserIdAndPriority(
                userId, TicketPriority.MEDIUM);

        long lowPriority = ticketRepository.countByUserIdAndPriority(
                userId, TicketPriority.LOW);

        return new DashboardResponse(
                totalTickets,
                todoTickets,
                inProgressTickets,
                completedTickets,
                highPriority,
                mediumPriority,
                lowPriority
        );
    }
}
