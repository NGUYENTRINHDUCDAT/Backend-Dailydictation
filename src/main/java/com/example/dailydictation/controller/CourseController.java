package com.example.dailydictation.controller;

import com.example.dailydictation.dto.request.CourseRequest;
import com.example.dailydictation.dto.response.ApiResponse;
import com.example.dailydictation.entity.Course;
import com.example.dailydictation.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping(value = "/create-course")
    public ApiResponse<Void> createCourse(@RequestParam("name") String name,
                                       @RequestParam("level") String level,
                                       @RequestParam("countOfSentence") short countOfSentence,
                                       @RequestParam("mainAudio") MultipartFile mainAudio,
                                       @RequestParam("sentence") List<String> sentence,
                                       @RequestParam("sentenceAudio") List<MultipartFile> sentenceAudio,
                                       @RequestParam("transcript") String transcript) throws IOException {

        CourseRequest courseRequest = CourseRequest.builder()
                .name(name)
                .level(level)
                .countOfSentence(countOfSentence)
                .mainAudio(mainAudio)
                .sentences(sentence)
                .sentenceAudios(sentenceAudio)
                .transcript(transcript)
                .build();
        courseService.createCourse(courseRequest);
        return ApiResponse.<Void> builder()
                .message("Course created successfully!")
                .build();
    }


    @GetMapping("/get-course")
    public ApiResponse<Course> getCourse(@RequestParam int courseId) {
        return ApiResponse.<Course>builder()
                .result(courseService.getCourse(courseId))
                .build();
    }

    @GetMapping("/get-list-sentence")
    public List<String> getListSentence(@RequestParam int courseId) {
        return courseService.getListSentence(courseId);
    }

    @PostMapping("/check-sentence")
    public String checkSentence(@RequestParam int courseId, @RequestBody String userSentence) {
        return courseService.checkSentence(courseId, userSentence);
    }
    @GetMapping("/get-audio-sentence")
    public String getAudioSentence(@RequestParam int courseId){
        return courseService.getAudioSentence(courseId);
    }
}
