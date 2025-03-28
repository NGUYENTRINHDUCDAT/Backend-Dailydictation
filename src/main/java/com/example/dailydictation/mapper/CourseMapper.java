package com.example.dailydictation.mapper;

import com.example.dailydictation.dto.request.CourseRequest;
import com.example.dailydictation.dto.response.CourseResponse;
import com.example.dailydictation.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    Course toCourse (CourseRequest courseRequest);
    CourseResponse toCourseResponse(Course course);
}
