package com.julianjupiter.csm.mapper;

import com.julianjupiter.csm.dto.TicketDto;
import com.julianjupiter.csm.entity.Ticket;
import com.julianjupiter.csm.util.Uuid;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @author Julian Jupiter
 */
@Component
public class TicketMapper {
    private final CommentMapper commentMapper;

    public TicketMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public TicketDto map(Ticket ticket) {
        return new TicketDto(
                UUID.fromString(ticket.getId()),
                ticket.getTitle(),
                ticket.getDescription(),
                this.customer(ticket),
                this.priority(ticket),
                this.status(ticket),
                this.assignedAent(ticket),
                ticket.getComments().stream()
                        .map(this.commentMapper::map)
                        .toList(),
                ticket.getCreatedBy(),
                ticket.getCreatedAt(),
                ticket.getUpdatedBy(),
                ticket.getUpdatedAt()
        );
    }

    private Map<String, Object> customer(Ticket ticket) {
        var customer = ticket.getCustomer();
        if (customer != null) {
            return Map.of(
                    "id", Uuid.fromString(customer.getId()),
                    "name", customer.getFirstName() + " " + customer.getLastName()
            );
        }

        return Map.of();
    }

    private Map<String, Object> priority(Ticket ticket) {
        var priority = ticket.getPriority();
        if (priority != null) {
            return Map.of(
                    "id", priority.getId(),
                    "name", priority.getName()
            );
        }

        return Map.of();
    }

    private Map<String, Object> status(Ticket ticket) {
        var status = ticket.getStatus();
        if (status != null) {
            return Map.of(
                    "id", status.getId(),
                    "name", status.getName()
            );
        }

        return Map.of();
    }

    private Map<String, Object> assignedAent(Ticket ticket) {
        var agent = ticket.getAgent();
        if (agent != null) {
            return Map.of(
                    "id", UUID.fromString(agent.getId()),
                    "name", agent.getFirstName() + " " + agent.getLastName()
            );
        }

        return Map.of();
    }
}
