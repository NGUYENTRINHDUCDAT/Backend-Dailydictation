package com.example.dailydictation.service;

import com.example.dailydictation.dto.request.CommentRequest;
import com.example.dailydictation.dto.response.CommentResponse;
import com.example.dailydictation.entity.Comment;
import com.example.dailydictation.entity.CommentClosure;
import com.example.dailydictation.entity.Course;
import com.example.dailydictation.entity.User;
import com.example.dailydictation.mapper.CommentMapper;
import com.example.dailydictation.repository.CommentClosureRepository;
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
    @Autowired
    private CommentClosureRepository closureRepository;

    public CommentResponse comment(CommentRequest commentRequest) {
        User user = userRepository.findUserById(commentRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findCourseById(commentRequest.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setCourse(course);
        comment.setUser(user);
        comment.setCreateDate(LocalDateTime.now());

        if (commentRequest.getParentId() != null) {
            Comment parent = commentRepository.findCommentById(commentRequest.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent not found"));
            comment.setParent(parent);
        }

        commentRepository.save(comment);

        // Closure table insert
        if (commentRequest.getParentId() != null) {
            List<CommentClosure> ancestors = closureRepository.findAllByIdDescendantId(commentRequest.getParentId());
            for (CommentClosure ac : ancestors) {
                closureRepository.save(new CommentClosure(ac.getAncestorId(), comment.getId(), ac.getDepth() + 1));
            }
        }
        // Always insert self link
        closureRepository.save(new CommentClosure(comment.getId(), comment.getId(), 0));

        return commentMapper.toCommentResponse(comment);
    }

    public List<Comment> getAllComment(int courseId) {
        return commentRepository.findByCourseId(courseId);
    }



    public List<CommentResponse> getAllCommentResponses(int courseId) {
        List<Comment> comments = commentRepository.findByCourseId(courseId);
        return comments.stream()
                .map(commentMapper::toCommentResponse)
                .toList();
    }

}
