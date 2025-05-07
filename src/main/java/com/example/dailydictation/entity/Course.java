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
    private String mainAudio;
    @ElementCollection
    @CollectionTable(name = "exercise_sentence", joinColumns = @JoinColumn(name = "sentence_id"))
    private List<String> sentences;
    @ElementCollection
    @CollectionTable(name = "exercise_audio", joinColumns = @JoinColumn(name = "audio_id"))
    private List<String> sentenceAudios;
    private String transcript;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private List<Comment> comments;

}
