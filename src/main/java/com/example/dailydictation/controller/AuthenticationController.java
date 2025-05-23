package com.example.dailydictation.controller;

import com.example.dailydictation.dto.request.AuthenticationRequest;
import com.example.dailydictation.dto.request.IntrospectRequest;
import com.example.dailydictation.dto.response.ApiResponse;
import com.example.dailydictation.dto.response.AuthenticationResponse;
import com.example.dailydictation.dto.response.IntrospectResponse;
import com.example.dailydictation.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000") // Cho phép React gọi API

public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/test-log-in")
    public ApiResponse<AuthenticationResponse> testLogin(@RequestBody AuthenticationRequest authenticationRequest) {
        System.out.println("Dữ liệu nhận từ FE: " + authenticationRequest); // In dữ liệu nhận được

        AuthenticationResponse result = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> testLogin(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {

        var result = authenticationService.introspect(introspectRequest);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();

    }

}