package com.github.matthewdesouza.springforum.service;

import com.github.matthewdesouza.springforum.entity.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopics();
    Topic getTopicByName(String name);
    void saveTopic(Topic topic);
    void deleteTopic(Topic topic);
}
