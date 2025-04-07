package com.example.dailydictation.service;

import com.example.dailydictation.entity.Course;
import com.example.dailydictation.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public Course create (Course course){
        return courseRepository.save(course);
    }

    public Course getCourse(int courseId){
        return courseRepository.getCourseById(courseId);
    }

}
