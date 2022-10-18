package com.github.matthewdesouza.springforum.repository;

import com.github.matthewdesouza.springforum.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findTopicByName(String name);
}
