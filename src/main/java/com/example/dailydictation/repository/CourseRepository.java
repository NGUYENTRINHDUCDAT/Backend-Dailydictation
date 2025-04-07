package com.example.dailydictation.repository;

import com.example.dailydictation.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
     Course getCourseById(int id);
}
