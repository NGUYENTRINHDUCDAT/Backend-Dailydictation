package com.example.dailydictation.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicRequest {
    private String type;
    private String level;
    private MultipartFile img;
}
