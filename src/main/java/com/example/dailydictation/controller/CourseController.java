package com.example.dailydictation.controller;

import com.example.dailydictation.dto.response.ApiResponse;
import com.example.dailydictation.entity.Course;
import com.example.dailydictation.entity.SentenceAudio;
import com.example.dailydictation.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping(value = "/create-course", consumes = "multipart/form-data")
    @ResponseBody
    public ApiResponse<Course> create (@RequestParam("name")  String name,
                                      @RequestParam("level")  String level,
                                      @RequestParam("countOfSentence") short countOfSentence,
                                      @RequestParam("mainAudio")MultipartFile mainAudio,
                                      @RequestParam("sentence")List<String> sentence,
                                      @RequestParam("sentenceAudio")  MultipartFile [] sentenceAudio,
                                      @RequestParam("transcript")  String transcript
                          ) throws IOException {
        List<SentenceAudio> sentenceAudioList = Arrays.stream(sentenceAudio)
                .filter(file -> !file.isEmpty()) // Loại bỏ file rỗng
                .map(file -> {
                    try {
                        return SentenceAudio.builder()
                                .audio(file.getBytes())
                                .build();
                    } catch (IOException e) {
                        throw new RuntimeException("Lỗi khi đọc file: " + file.getOriginalFilename(), e);
                    }
                }).toList();

        Course course = Course.builder()
                .name(name)
                .level(level)
                .countOfSentence(countOfSentence)
                .mainAudio(mainAudio.getBytes())
                .sentences(sentence)
                .sentenceAudios(sentenceAudioList)
                .transcript(transcript)
                .build();
        for (SentenceAudio audio : sentenceAudioList) {
            audio.setCourse(course);
        }

    return ApiResponse.<Course>builder()
            .result(courseService.create(course))
            .build();
    }

    @GetMapping("/get-course")
    public ApiResponse<Course> getCourse (@RequestParam int courseId ){
        return ApiResponse.<Course>builder()
                .result(courseService.getCourse(courseId))
                .build();
    }
}
