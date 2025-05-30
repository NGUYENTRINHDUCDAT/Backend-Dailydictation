package com.example.dailydictation.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.dailydictation.dto.request.UserRequest;
import com.example.dailydictation.dto.response.UserResponse;
import com.example.dailydictation.dto.response.UserResponseShowNickName;
import com.example.dailydictation.entity.User;
import com.example.dailydictation.enums.ERole;
import com.example.dailydictation.mapper.UserMapper;
import com.example.dailydictation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Cloudinary cloudinary;

    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByUserName(userRequest.getUserName())) {
            throw new RuntimeException("User was exist");
        }
        User user = userMapper.toUser(userRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setCreateDate(LocalDateTime.now());
        Set<ERole> roles = new HashSet<>();
        roles.add(ERole.USER);
        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        return users;

    }

    public UserResponseShowNickName editNickName(int userId, String newNickName) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        user.setNickName(newNickName);
        User newUser = userRepository.save(user);
        UserResponseShowNickName userResponseShowNickName = new UserResponseShowNickName();
        userResponseShowNickName.setNickName(newUser.getNickName());
        return userResponseShowNickName;
    }

    public UserResponse showInformation(int userId) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        return userMapper.toUserResponse(user);
    }

    public UserResponse  editImage(int userId, MultipartFile image) throws IOException {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        Map img = cloudinary.uploader().upload(
                image.getBytes(),
                ObjectUtils.asMap("resource_type", "auto")
        );

        String newImage = img.get("secure_url").toString();

        user.setImg(newImage);

        return userMapper.toUserResponse( userRepository.save(user));
    }
}
