package com.example.dailydictation.mapper;

import com.example.dailydictation.dto.response.CommentResponse;
import com.example.dailydictation.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "userName", source = "user.userName")
    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "parentId", source = "parent.id")
    CommentResponse toCommentResponse(Comment comment);
}
