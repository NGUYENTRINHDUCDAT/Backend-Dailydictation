package com.example.dailydictation.repository;

import com.example.dailydictation.entity.FavoriteCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteCourseRepository extends JpaRepository<FavoriteCourse,Integer> {
    boolean existsByCourseIdAndUserId(int courseId, int userId);
    List<FavoriteCourse>findAllByUserId(int userId);
}
