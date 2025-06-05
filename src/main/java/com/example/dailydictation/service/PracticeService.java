package com.example.dailydictation.service;

import com.example.dailydictation.dto.response.PracticeResponse;
import com.example.dailydictation.entity.Practice;
import com.example.dailydictation.enums.EStatus;
import com.example.dailydictation.repository.CourseRepository;
import com.example.dailydictation.repository.PracticeRepository;
import com.example.dailydictation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PracticeService {

    @Autowired
    private PracticeRepository practiceRepository;




    public PracticeResponse showPracticeUser(int userId) {
        int score = practiceRepository.getTotalScoreByUserId(userId);
        Long scoreFinish = practiceRepository.countByUserId(userId);
        PracticeResponse practiceResponse = PracticeResponse.builder()
                .status(EStatus.finished)
                .score(score)
                .scoreFinish(scoreFinish)
                .build();
        return practiceResponse;
    }
}
