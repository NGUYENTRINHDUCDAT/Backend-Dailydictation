package com.example.dailydictation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestRegister {
    private String nickName;
    private String username;
    private String email;
    private String password;
}
