package com.example.dailydictation.dto.request;

import com.example.dailydictation.enums.Reaction;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReactRequest {
  private int userId;
  private  int commentId;
  private int courseId;
  private Reaction reaction;
}
