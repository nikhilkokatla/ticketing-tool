package com.ticketingtool.ticket.dto;

import com.ticketingtool.ticket.TicketPriority;
import com.ticketingtool.ticket.TicketStatus;
import java.time.LocalDateTime;

public class TicketResponse {

    private Long id;
    private String subject;
    private String description;
    private TicketPriority priority;
    private TicketStatus status;
    private Long userId;
    private String userName;
    private Long assignedToId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TicketResponse() {
    }

    public TicketResponse(
            Long id,
            String subject,
            String description,
            TicketPriority priority,
            TicketStatus status,
            Long userId,
            String userName,
            Long assignedToId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.subject = subject;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.userId = userId;
        this.userName = userName;
        this.assignedToId = assignedToId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}