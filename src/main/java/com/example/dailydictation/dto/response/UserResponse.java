package com.example.dailydictation.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String nickName;
    private String userName;
    private LocalDateTime createDate;
    private Set<String> roles;
}
