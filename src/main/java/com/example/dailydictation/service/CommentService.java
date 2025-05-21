package com.example.dailydictation.service;

import com.example.dailydictation.dto.request.CommentRequest;
import com.example.dailydictation.dto.response.CommentResponse;
import com.example.dailydictation.entity.Comment;
import com.example.dailydictation.entity.Course;
import com.example.dailydictation.entity.User;
import com.example.dailydictation.mapper.CommentMapper;
import com.example.dailydictation.repository.CommentRepository;
import com.example.dailydictation.repository.CourseRepository;
import com.example.dailydictation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;


    public CommentResponse comment(CommentRequest commentRequest) {
        User user = userRepository.findUserById(commentRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findCourseById(commentRequest.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        String content = commentRequest.getContent();
        Comment comment = Comment.builder()
                .content(content)
                .course(course)
                .createDate(LocalDateTime.now())
                .user(user)
                .build();
        if (commentRequest.getParentId() != null) {
            Comment parent = commentRepository.findCommentById(commentRequest.getParentId())
                    .orElseThrow(() -> new RuntimeException("parent id not found"));
            comment.setParent(parent);
        }


        commentRepository.save(comment);

        return commentMapper.toCommentResponse(comment);
    }

    public List<Comment> getAllComment(int courseId) {
        return commentRepository.findByCourseId(courseId);
    }


}
