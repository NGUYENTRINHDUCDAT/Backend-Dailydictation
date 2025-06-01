package com.example.dailydictation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String message;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
    private List<Comment> comments;

    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate() {
        LocalDateTime createAt = LocalDateTime.now();
    }
}
