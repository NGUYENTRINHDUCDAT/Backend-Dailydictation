package com.example.dailydictation.controller;

import com.example.dailydictation.dto.response.ApiResponse;
import com.example.dailydictation.dto.response.NoteResponse;
import com.example.dailydictation.dto.response.PracticeResponse;
import com.example.dailydictation.service.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PracticeController {

    @Autowired
    private PracticeService practiceService;
    @GetMapping("/show-practice")
    public ApiResponse<PracticeResponse> showPractice (@RequestParam int userId){
        return ApiResponse.<PracticeResponse>builder()
                .result(practiceService.showPracticeUser(userId))
                .build();
    }
}
