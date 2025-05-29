package com.example.dailydictation.dto.response;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteResponse {
    private String content;
    private long daysSaved;
    private LocalDate createDate;
}
