package com.example.dailydictation.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SentenceAudio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Lob
    private byte[] audio;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
