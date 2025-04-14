package com.example.dailydictation.dto.request;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String nickName;
    private String userName;
    private String password;
}
