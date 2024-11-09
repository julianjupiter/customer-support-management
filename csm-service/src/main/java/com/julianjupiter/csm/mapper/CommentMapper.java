package com.julianjupiter.csm.mapper;

import com.julianjupiter.csm.dto.CommentDto;
import com.julianjupiter.csm.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Julian Jupiter
 */
@Component
public class CommentMapper {
    public CommentDto map(Comment comment) {
        return new CommentDto(
                UUID.fromString(comment.getId()),
                comment.getContent(),
                comment.getCreatedBy(),
                comment.getCreatedAt()
        );
    }
}
