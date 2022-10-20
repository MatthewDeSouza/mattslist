package com.github.matthewdesouza.mattslist.repository;

import com.github.matthewdesouza.mattslist.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findTopicByName(String name);
    Topic findTopicById(Long id);
}
