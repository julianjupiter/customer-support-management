package com.julianjupiter.csm.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Julian Jupiter
 */
public record TicketDto(
        UUID id,
        String title,
        String description,
        Map<String, Object> customer,
        Map<String, Object> priority,
        Map<String, Object> status,
        Map<String, Object> assignedAgent,
        List<CommentDto> comments,
        String createdBy,
        Instant createdAt,
        String updatedBy,
        Instant updatedAt) {
}
