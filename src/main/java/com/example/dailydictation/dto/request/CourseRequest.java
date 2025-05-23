package com.example.dailydictation.dto.request;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequest {
    private String name;
    private String level;
    private short countOfSentence;
    private MultipartFile mainAudio;
    private List<String> sentences;
    private List<MultipartFile> sentenceAudios;
    private String transcript;
    private int sectionId;
}
