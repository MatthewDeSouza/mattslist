package com.github.matthewdesouza.mattslist.service.impl;

import com.github.matthewdesouza.mattslist.entity.Post;
import com.github.matthewdesouza.mattslist.entity.Topic;
import com.github.matthewdesouza.mattslist.repository.TopicRepository;
import com.github.matthewdesouza.mattslist.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    private final TopicRepository repository;

    @Autowired
    public TopicServiceImpl(TopicRepository repository) {
        this.repository = repository;
    }

    /**
     * Generate a List of each {@link Topic} within the database.
     * @return {@link List<Topic>}
     */
    @Override
    public List<Topic> getAllTopics() {
        return repository.findAll();
    }

    /**
     * Finds {@link Topic} by name from the database.
     * @param name Name of topic.
     * @return {@link List<Topic>}
     */
    @Override
    public List<Topic> getTopicByName(String name) {
        return repository.findTopicByName(name);
    }

    /**
     * Saves {@link Topic} to the database.
     * @param topic Topic to save.
     */
    @Override
    public void saveTopic(Topic topic) {
        repository.save(topic);
    }

    @Override
    public void updateTopic(Post post, String name) {
        Topic temp = repository.findTopicByName(name).get(0);
        temp.addPost(post);
        repository.save(temp);
    }

    /**
     * Deletes {@link Topic} from the database.
     * @param topic Topic to delete.
     */
    @Override
    public void deleteTopic(Topic topic) {
        repository.delete(topic);
    }

    @Override
    public Topic getTopicById(Long id) {
        return repository.findTopicById(id);
    }
}
