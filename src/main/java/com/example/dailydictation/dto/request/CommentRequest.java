package com.example.dailydictation.dto.request;

import com.example.dailydictation.entity.Course;
import com.example.dailydictation.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    private String content;
    private int userId;
    private int courseId;
}
