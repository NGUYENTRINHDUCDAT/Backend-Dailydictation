package com.example.dailydictation.service;

import com.example.dailydictation.dto.request.CourseRequest;
import com.example.dailydictation.dto.response.CourseResponse;
import com.example.dailydictation.entity.Course;
import com.example.dailydictation.mapper.CourseMapper;
import com.example.dailydictation.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseMapper courseMapper;

    public CourseResponse createCourse (CourseRequest courseRequest){
        Course course = courseMapper.toCourse(courseRequest);
        return courseMapper.toCourseResponse(courseRepository.save(course));
    }
}
