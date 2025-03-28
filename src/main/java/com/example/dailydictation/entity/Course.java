package com.example.dailydictation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String name;
    private String level;
    private short countOfSentence;
    @Lob
    @Column(name = "main_audio", columnDefinition = "LONGBLOB")
    private byte[] mainAudio;

    @ElementCollection
    @CollectionTable(name = "exercise_audio_files", joinColumns = @JoinColumn(name = "exercise_id"))
    private List<String> sentences;

    @ElementCollection
    @CollectionTable(name = "exercise_audio_files", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "sentence_audio")
    private List<byte[]> sentenceAudios;

    private String transcript;
}
