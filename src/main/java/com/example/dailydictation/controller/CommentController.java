package com.example.dailydictation.controller;

import com.example.dailydictation.dto.request.CommentRequest;
import com.example.dailydictation.dto.response.ApiResponse;
import com.example.dailydictation.dto.response.CommentResponse;
import com.example.dailydictation.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public ApiResponse<CommentResponse> comment(@RequestBody  CommentRequest commentRequest) {
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.comment(commentRequest))
                .build();
    }

}
