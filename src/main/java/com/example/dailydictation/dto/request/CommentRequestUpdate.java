package com.example.dailydictation.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestUpdate {
    private int userId;
    private String content;
    private int commentId;
}
