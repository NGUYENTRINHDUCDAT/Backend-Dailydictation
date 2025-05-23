package com.example.dailydictation.dto.request;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionRequest {
    private String name;
    private int countOfCourse;
    private int topicId;
}
