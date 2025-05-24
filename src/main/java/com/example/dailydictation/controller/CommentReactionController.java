package com.example.dailydictation.controller;

import com.example.dailydictation.dto.request.CommentReactRequest;
import com.example.dailydictation.dto.response.ApiResponse;
import com.example.dailydictation.dto.response.CommentReactionResponse;
import com.example.dailydictation.dto.response.CommentReactionShowResponse;
import com.example.dailydictation.entity.CommentReaction;
import com.example.dailydictation.enums.Reaction;
import com.example.dailydictation.service.CommentReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentReactionController {
    @Autowired
    private CommentReactionService commentReactionService;


    @PostMapping("/reaction")
    public ApiResponse<CommentReactionResponse> reactOfUser(@RequestBody CommentReactRequest commentReactRequest) {
        System.out.println("Received: " + commentReactRequest); // ← log ra object
        CommentReactionResponse response = commentReactionService.reactOfUser(commentReactRequest);
        return ApiResponse.<CommentReactionResponse>builder().result(response).build();
    }


    @GetMapping("/show-reaction")
    public ApiResponse<List<CommentReactionShowResponse>> showReaction(@RequestParam int courseId) {
        List<CommentReactionShowResponse> commentReaction = commentReactionService.showReaction(courseId);
        return ApiResponse.<List<CommentReactionShowResponse>>builder()
                .result(commentReaction)
                .build();
    }

    @DeleteMapping("/delete-reaction")
    public ApiResponse<Void> deleteReaction(@RequestParam int commentId,
                                            @RequestParam int userId
                                            ) {


        commentReactionService.deleteReaction(commentId, userId);
        return ApiResponse.<Void>builder()
                .message("Deleted reaction successfully")
                .build();
    }
    @PutMapping("/change-reaction")
    public ApiResponse<CommentReactionResponse> changeReaction(@RequestBody CommentReactRequest commentReactRequest) {
        CommentReactionResponse commentReactionResponse = commentReactionService.changeReaction(commentReactRequest);
        return ApiResponse.<CommentReactionResponse>builder()
                .result(commentReactionResponse)
                .build();
    }
}
