package com.github.matthewdesouza.mattslist.service;

import com.github.matthewdesouza.mattslist.entity.Post;
import com.github.matthewdesouza.mattslist.entity.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopics();
    List<Topic> getTopicByName(String name);
    void saveTopic(Topic topic);
    void deleteTopic(Topic topic);
    void updateTopic(Post post, String name);
    Topic getTopicById(Long id);
}
