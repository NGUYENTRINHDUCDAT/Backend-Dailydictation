package com.example.dailydictation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String content;

    @Column(name = "create_date", columnDefinition = "datetime(6)")
    private LocalDateTime createDate;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
    }
}
