package com.example.dailydictation.mapper;

import com.example.dailydictation.dto.request.CommentRequest;
import com.example.dailydictation.dto.response.CommentResponse;
import com.example.dailydictation.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "course", source = "course")
    CommentResponse toCommentResponse(Comment comment);
}
