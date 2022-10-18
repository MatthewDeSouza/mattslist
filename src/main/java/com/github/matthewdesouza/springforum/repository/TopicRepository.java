package com.github.matthewdesouza.springforum.repository;

import com.github.matthewdesouza.springforum.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findTopicByName(String name);
}
