package com.example.dailydictation.dto.request;

import com.example.dailydictation.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteRequest {
    private String content;
    private int userId;
}
