package com.example.dailydictation.controller;

import com.example.dailydictation.dto.request.CommentRequest;
import com.example.dailydictation.dto.response.ApiResponse;
import com.example.dailydictation.dto.response.CommentResponse;
import com.example.dailydictation.entity.Comment;
import com.example.dailydictation.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/get-all-comment")
    public ApiResponse<List<Comment>>getAllComment ( @RequestParam int courseId){
        return ApiResponse.<List<Comment>>builder()
                .result(commentService.getAllComment( courseId))
                .build();
}

}
