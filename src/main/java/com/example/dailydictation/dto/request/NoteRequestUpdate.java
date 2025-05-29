package com.example.dailydictation.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteRequestUpdate {
    private String content;
    private int noteId;
}
