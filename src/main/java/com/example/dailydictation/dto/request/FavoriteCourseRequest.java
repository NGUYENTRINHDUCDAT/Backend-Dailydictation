package com.example.dailydictation.dto.request;

import com.example.dailydictation.entity.Course;
import com.example.dailydictation.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteCourseRequest {
    private int courseId;
    private int userId;
}
