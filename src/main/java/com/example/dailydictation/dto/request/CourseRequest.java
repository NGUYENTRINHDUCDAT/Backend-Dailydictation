package com.example.dailydictation.dto.request;


import lombok.*;

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
    private byte[] mainAudio;
    private List<String> sentences;
    private List<byte[]> sentenceAudios;
    private String transcript;
}
