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
  private Integer userId;
  private Integer commentId;
  private Integer courseId;
  private Reaction reaction;
}
