
package com.ticketingtool.dashboard;

public class DashboardResponse {

    private long totalTickets;
    private long todoTickets;
    private long inProgressTickets;
    private long completedTickets;

    private long highPriorityTickets;
    private long mediumPriorityTickets;
    private long lowPriorityTickets;

    public DashboardResponse(long totalTickets, long todoTickets,
                             long inProgressTickets, long completedTickets,
                             long highPriorityTickets, long mediumPriorityTickets,
                             long lowPriorityTickets) {

        this.totalTickets = totalTickets;
        this.todoTickets = todoTickets;
        this.inProgressTickets = inProgressTickets;
        this.completedTickets = completedTickets;
        this.highPriorityTickets = highPriorityTickets;
        this.mediumPriorityTickets = mediumPriorityTickets;
        this.lowPriorityTickets = lowPriorityTickets;
    }

    public long getTotalTickets() {
        return totalTickets;
    }

    public long getTodoTickets() {
        return todoTickets;
    }

    public long getInProgressTickets() {
        return inProgressTickets;
    }

    public long getCompletedTickets() {
        return completedTickets;
    }

    public long getHighPriorityTickets() {
        return highPriorityTickets;
    }

    public long getMediumPriorityTickets() {
        return mediumPriorityTickets;
    }

    public long getLowPriorityTickets() {
        return lowPriorityTickets;
    }
}
