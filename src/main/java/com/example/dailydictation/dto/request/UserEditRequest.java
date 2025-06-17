package com.example.dailydictation.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class UserEditRequest {
    private Integer userId;
    private String userName;
    private String gmail;
    private String role;
    private MultipartFile avatar;

    // Getter và Setter
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getGmail() { return gmail; }
    public void setGmail(String gmail) { this.gmail = gmail; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public MultipartFile getAvatar() { return avatar; }
    public void setAvatar(MultipartFile avatar) { this.avatar = avatar; }
}
