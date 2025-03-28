package com.example.dailydictation.controller;

import com.example.dailydictation.dto.response.ApiResponse;
import com.example.dailydictation.dto.response.CourseResponse;
import com.example.dailydictation.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService courseService;


}
