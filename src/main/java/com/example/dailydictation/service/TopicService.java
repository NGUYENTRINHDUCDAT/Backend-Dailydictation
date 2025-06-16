package com.example.dailydictation.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.dailydictation.dto.request.TopicRequest;
import com.example.dailydictation.dto.response.TopicResponse;
import com.example.dailydictation.entity.Topic;
import com.example.dailydictation.mapper.TopicMapper;
import com.example.dailydictation.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private Cloudinary cloudinary;

    public TopicResponse createTopic(TopicRequest topicRequest) throws IOException {
        // lấy file audio từ người dunng gửi lên
        Map image = cloudinary.uploader().upload(
                topicRequest.getImg().getBytes(),
                ObjectUtils.asMap("resource_type", "auto")
        );
        // lấy đường dẫn ảnh từ gg
        String imageUrl = image.get("secure_url").toString();
        Topic topic = new Topic();
        topic.setType(topicRequest.getType());
        topic.setImg(imageUrl);
        topic.setLevel(topicRequest.getLevel());
        topic.setCountTopic(topicRequest.getCountTopic());
        return topicMapper.toTopicResponse(topicRepository.save(topic));
    }

    public List<TopicResponse> showAllTopic() {
        return topicRepository.findAll()
                .stream()
                .map(topicMapper::toTopicResponse)
                .collect(Collectors.toList());
    }

    public void deleteTopic(int id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy topic với id: " + id));
        topicRepository.delete(topic);
    }

}
