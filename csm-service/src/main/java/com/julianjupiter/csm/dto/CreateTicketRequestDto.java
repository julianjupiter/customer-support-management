package com.julianjupiter.csm.dto;

import java.util.UUID;

/**
 * @author Julian Jupiter
 */
public record CreateTicketRequestDto(
        String title,
        String description,
        UUID customerId,
        Integer priorityId,
        UUID agentId) {
}
