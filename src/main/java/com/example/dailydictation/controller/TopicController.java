package com.example.dailydictation.controller;

import com.example.dailydictation.dto.request.TopicRequest;
import com.example.dailydictation.dto.response.ApiResponse;
import com.example.dailydictation.dto.response.TopicResponse;
import com.example.dailydictation.entity.Topic;
import com.example.dailydictation.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TopicController {
    @Autowired
    private TopicService topicService;


    @PostMapping("/create-topic")
    public ApiResponse<TopicResponse> createTopic(@Valid @ModelAttribute TopicRequest topicRequest) throws IOException {
        TopicResponse topicResponse = topicService.createTopic(topicRequest);
        return ApiResponse.<TopicResponse>builder()
                .result(topicResponse)
                .build();
    }


    @GetMapping("/show-all-topic")
    public ApiResponse<List<TopicResponse>> showAllTopic (){
        List<TopicResponse> topic = topicService.showAllTopic();
        return ApiResponse.<List<TopicResponse>>builder()
                .result(topic)
                .build();
    }
    @DeleteMapping("/delete-topic/{id}")
    public ApiResponse<Void> deleteTopic(@PathVariable("id") int id) {
        topicService.deleteTopic(id);
        return ApiResponse.<Void>builder()
                .message("Xóa topic thành công!")
                .build();
    }

}
