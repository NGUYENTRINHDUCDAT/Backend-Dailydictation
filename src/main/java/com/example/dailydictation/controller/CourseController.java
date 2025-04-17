package com.example.dailydictation.controller;

import com.example.dailydictation.dto.request.CourseRequest;
import com.example.dailydictation.dto.response.ApiResponse;
import com.example.dailydictation.entity.Course;
import com.example.dailydictation.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
        return ApiResponse.<Void>builder()
                .message("Course created successfully!")
                .build();
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam String url) throws IOException {
        byte[] fileData = courseService.downloadFile(url);
        if (fileData == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Trích xuất tên file từ URL (vd: https://abc.com/audio.mp3 -> audio.mp3)
        String fileName = extractFileName(url);
        // Đặt kiểu nội dung file trả về là audio/mpeg (cho file mp3)
        String contentType = "audio/mpeg"; // Có thể đoán theo đuôi file hoặc tùy chỉnh động

        // Tạo header cho phản hồi HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));

        // Đặt Content-Disposition để trình duyệt hiểu rằng đây là file để tải xuống
        headers.setContentDisposition(ContentDisposition
                .attachment() // Đánh dấu là file đính kèm (tải về)
                .filename(UriUtils.encode(fileName, StandardCharsets.UTF_8))
                .build());

        // Trả về dữ liệu file, kèm header và mã HTTP 200 (OK)
        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }


    // Phương thức phụ dùng để trích xuất tên file từ URL
    private String extractFileName(String fileUrl) {
        try {
            // Lấy phần path từ URL (vd: /music/audio.mp3)
            String path = new URL(fileUrl).getPath();
            // Lấy phần cuối cùng sau dấu "/" (vd: audio.mp3)
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (Exception e) {
            // Nếu có lỗi (URL sai,...), trả tên mặc định
            return "audio.mp3";
        }
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
    public String getAudioSentence(@RequestParam int courseId) {
        return courseService.getAudioSentence(courseId);
    }
}
