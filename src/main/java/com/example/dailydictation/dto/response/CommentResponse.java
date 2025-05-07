package com.example.dailydictation.dto.response;

import com.example.dailydictation.entity.Course;
import com.example.dailydictation.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private String content;
    private User user;
    private Course course;
}
