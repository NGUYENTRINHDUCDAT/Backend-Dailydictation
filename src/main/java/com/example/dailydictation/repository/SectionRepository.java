package com.example.dailydictation.repository;

import com.example.dailydictation.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section,Integer> {
    List<Section> findByTopicId(int topicId);
    Optional<Section> findById(int sectionId);
}
