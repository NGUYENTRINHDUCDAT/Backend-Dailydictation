package com.example.dailydictation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class Auth2Controller {
    @GetMapping("/success")
    public ResponseEntity<?> success(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", principal.getAttribute("name"));
        data.put("email", principal.getAttribute("email"));
        data.put("picture", principal.getAttribute("picture"));
        data.put("token", principal.getAttributes().get("token"));
        return ResponseEntity.ok(data);
    }
    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
        return ResponseEntity.ok(principal.getAttributes());
    }
}
