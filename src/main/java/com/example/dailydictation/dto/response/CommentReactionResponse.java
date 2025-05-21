package com.example.dailydictation.dto.response;

import com.example.dailydictation.enums.Reaction;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReactionResponse {
    private int userId;
    private  int commentId;
    private int courseId;
    private Reaction reaction;
    private LocalDateTime createDate;
}
