package com.example.dailydictation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String username; // Tên đăng nhập
    private String password; // Mật khẩu

    public String getUserName() {
        return this.username;
    }

}