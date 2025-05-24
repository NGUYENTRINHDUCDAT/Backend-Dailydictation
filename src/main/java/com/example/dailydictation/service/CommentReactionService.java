package com.example.dailydictation.service;

import com.example.dailydictation.dto.request.CommentReactRequest;
import com.example.dailydictation.dto.response.CommentReactionResponse;
import com.example.dailydictation.dto.response.CommentReactionShowResponse;
import com.example.dailydictation.entity.Comment;
import com.example.dailydictation.entity.CommentReaction;
import com.example.dailydictation.entity.Course;
import com.example.dailydictation.entity.User;
import com.example.dailydictation.enums.Reaction;
import com.example.dailydictation.mapper.CommentReactionMapper;
import com.example.dailydictation.repository.CommentReactionRepository;
import com.example.dailydictation.repository.CommentRepository;
import com.example.dailydictation.repository.CourseRepository;
import com.example.dailydictation.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentReactionService {

    @Autowired
    private CommentReactionRepository commentReactionRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CommentReactionMapper commentReactionMapper;

    public CommentReactionResponse reactOfUser(CommentReactRequest commentReactRequest) {
        User user = userRepository.findUserById(commentReactRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findCommentById(commentReactRequest.getCommentId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        Course course = courseRepository.findCourseById(commentReactRequest.getCourseId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        CommentReaction commentReaction = CommentReaction.builder()
                .user(user)
                .comment(comment)
                .course(course)
                .reaction(commentReactRequest.getReaction())
                .createDate(LocalDateTime.now())
                .build();
        return commentReactionMapper.toCommentReaction(commentReactionRepository.save(commentReaction));
    }

    public List<CommentReactionShowResponse> showReaction(int courseId) {
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        List<CommentReaction> reaction = commentReactionRepository.findByCourse(course);
        List<CommentReactionShowResponse> commentReactionShowResponses = new ArrayList<>();
        for (CommentReaction commentReaction : reaction) {
            CommentReactionShowResponse commentReactionShowResponse = new CommentReactionShowResponse();
            commentReactionShowResponse.setCommentId(commentReaction.getComment().getId());
            commentReactionShowResponse.setReaction(commentReaction.getReaction());
            commentReactionShowResponses.add(commentReactionShowResponse);
        }
        return commentReactionShowResponses;
    }

    @Transactional
    public void deleteReaction(int commentId, int userId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findCommentById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentReactionRepository.deleteByUserAndComment(user, comment);
    }

    public CommentReactionResponse changeReaction(CommentReactRequest commentReactRequest) {
        User user = userRepository.findUserById(commentReactRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findCommentById(commentReactRequest.getCommentId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        Course course = courseRepository.findCourseById(commentReactRequest.getCourseId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        CommentReaction commentReaction = commentReactionRepository.findByUserAndCommentAndCourse(user, comment,course);
        commentReaction.setReaction(commentReactRequest.getReaction());
        commentReaction.setCreateDate(LocalDateTime.now());
     CommentReaction commentReactionUpdate=  commentReactionRepository.save(commentReaction);

       CommentReactionResponse commentReactionResponse = new CommentReactionResponse();
       commentReactionResponse.setUserId(commentReactionUpdate.getUser().getId());
        commentReactionResponse.setCourseId(commentReactionUpdate.getCourse().getId());
        commentReactionResponse.setCommentId(commentReactionUpdate.getComment().getId());
        commentReactionResponse.setReaction(commentReactionUpdate.getReaction());
        commentReactionResponse.setCreateDate(commentReactionUpdate.getCreateDate());

        return commentReactionResponse;

    }
}
