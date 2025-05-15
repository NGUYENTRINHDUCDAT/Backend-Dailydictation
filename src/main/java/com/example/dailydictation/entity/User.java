package com.example.dailydictation.entity;

import com.example.dailydictation.enums.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String nickName;
    private String userName;
    private String password;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;
    private Set<ERole> roles;
    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment>comments;

}
