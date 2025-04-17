package com.example.dailydictation.controller;

import com.example.dailydictation.dto.request.UserRequest;
import com.example.dailydictation.dto.response.ApiResponse;
import com.example.dailydictation.dto.response.UserResponse;
import com.example.dailydictation.entity.User;
import com.example.dailydictation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("create-user")
    public ApiResponse<UserResponse> testCreateUser(@RequestBody UserRequest userRequest) {
        // In dữ liệu nhận từ frontend
        System.out.println("Dữ liệu nhận từ frontend: " + userRequest);

        UserResponse userResponse = userService.createUser(userRequest);
        return ApiResponse.<UserResponse>builder()
                .result(userResponse)
                .build();
    }


    @GetMapping("/get-all-user")
    public ApiResponse<List<User> >getAllUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("userName :"+ authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> System.out.println(grantedAuthority.getAuthority()));
        return  ApiResponse.<List<User>>builder()
                .result(userService.getAllUser())
                .build();
    }

}