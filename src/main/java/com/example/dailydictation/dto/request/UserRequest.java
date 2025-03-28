package com.example.dailydictation.dto.request;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String nickName;
    private String userName;
    private String password;
}
