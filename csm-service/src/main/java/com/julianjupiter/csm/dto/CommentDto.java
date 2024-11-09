package com.julianjupiter.csm.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * @author Julian Jupiter
 */
public record CommentDto(UUID id, String content, String createdBy, Instant createdAt) {
}
