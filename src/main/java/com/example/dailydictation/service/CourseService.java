package com.example.dailydictation.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.dailydictation.dto.request.CourseRequest;
import com.example.dailydictation.entity.Course;
import com.example.dailydictation.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    private Cloudinary cloudinary;


    public Course createCourse(CourseRequest courseRequest) throws IOException {
        List<String> listSentenceAudio = new ArrayList<>();
        Map mainAudio = cloudinary.uploader().upload(
                courseRequest.getMainAudio().getBytes(),
                ObjectUtils.asMap("resource_type", "auto")
        );
        String audioUrl = mainAudio.get("secure_url").toString();

        for (MultipartFile sentenceAudioFile : courseRequest.getSentenceAudios()) {
            Map sentenceAudio = cloudinary.uploader().upload(
                    sentenceAudioFile.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto")
            );
            listSentenceAudio.add(sentenceAudio.get("secure_url").toString());
        }

        Course course = new Course();

        course.setName(courseRequest.getName());
        course.setLevel(courseRequest.getLevel());
        course.setCountOfSentence(courseRequest.getCountOfSentence());
        course.setMainAudio(audioUrl);
        course.setSentences(courseRequest.getSentences());
        course.setSentenceAudios(listSentenceAudio);
        course.setTranscript(courseRequest.getTranscript());

        return courseRepository.save(course);

    }


    public Course getCourse(int courseId) {

        return courseRepository.getCourseById(courseId);
    }

    public List<String> getListSentence(int courseId) {

        return courseRepository.findSentencesByCourseId(courseId);
    }

    private int currentIndex = 0;

    public String checkSentence(int courseId, String userSentence) {
        List<String> listSentenceOfProgram = getListSentence(courseId);
        if (currentIndex >= listSentenceOfProgram.size()) {
            return "All sentences completed!";
        }
        String correctSentence = listSentenceOfProgram.get(currentIndex);
        if (userSentence.trim().equalsIgnoreCase(correctSentence.trim())) {
            currentIndex++;
            if (currentIndex >= listSentenceOfProgram.size()) {
                return " Correct! You’ve completed all sentences!";
            }
            return " Correct! Go to next sentence.";
        } else {
            return " Incorrect. Try again";
        }
    }
   public String getAudioSentence(int courseId){
        List<String>listAudioSentence = courseRepository.findSentenceAudiosByCourseId(courseId);
        if(listAudioSentence.isEmpty()||currentIndex>listAudioSentence.size()){
            return "No audio available or all sentences completed!";
        }
        return listAudioSentence.get(currentIndex);
   }
}
