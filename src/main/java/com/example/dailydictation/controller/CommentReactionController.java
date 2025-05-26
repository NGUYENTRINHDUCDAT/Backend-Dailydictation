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
        System.out.println("👉 [Reaction Request] userId=" + commentReactRequest.getUserId() +
                ", commentId=" + commentReactRequest.getCommentId() +
                ", courseId=" + commentReactRequest.getCourseId() +
                ", reaction=" + commentReactRequest.getReaction());

        boolean hasReaction = commentReactionService.checkUserReaction(
                commentReactRequest.getCommentId(),
                commentReactRequest.getUserId()
        );

        // Nếu đã tồn tại reaction và người dùng gửi Unlike → XÓA
        if (hasReaction && commentReactRequest.getReaction() == Reaction.Unlike) {
            commentReactionService.deleteReaction(commentReactRequest.getCommentId(), commentReactRequest.getUserId());
            return ApiResponse.<CommentReactionResponse>builder()
                    .message("🗑 Reaction removed (unlike)")
                    .build();
        }

        // Nếu đã tồn tại reaction và vẫn là Like → không insert nữa
        if (hasReaction && commentReactRequest.getReaction() == Reaction.Like) {
            return ApiResponse.<CommentReactionResponse>builder()
                    .message("👍 Already liked — no action taken")
                    .build();
        }

        // Nếu chưa có → thực hiện like mới
        CommentReactionResponse response = commentReactionService.reactOfUser(commentReactRequest);

        System.out.println("✅ [Reaction Saved] " + response);

        return ApiResponse.<CommentReactionResponse>builder()
                .result(response)
                .build();
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
