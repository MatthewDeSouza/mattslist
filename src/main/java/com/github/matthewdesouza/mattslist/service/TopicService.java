package com.github.matthewdesouza.mattslist.service;

import com.github.matthewdesouza.mattslist.entity.Post;
import com.github.matthewdesouza.mattslist.entity.Topic;

import java.util.List;

/**
 * Interface of various CRUD operations.
 * @author Matthew DeSouza
 */
public interface TopicService {
    /**
     * Gets all {@link Topic} from the database.
     * @return {@link List}
     */
    List<Topic> getAllTopics();

    /**
     * Gets {@link Topic} by name.
     * @param name {@link String}
     * @return {@link List}
     */
    List<Topic> getTopicByName(String name);

    /**
     * Saves {@link Topic} to the database.
     * @param topic {@link Topic}
     */
    void saveTopic(Topic topic);

    /**
     * Deletes a {@link Topic} from the database.
     * @param topic {@link Topic}
     */
    void deleteTopic(Topic topic);

    /**
     * Updates a {@link Topic} and persists it to the database.
     * @param post {@link Post}
     * @param name {@link String}
     */
    void updateTopic(Post post, String name);

    /**
     * Gets a {@link Topic} by id from the database.
     * @param id {@link Long}
     * @return {@link Topic}
     */
    Topic getTopicById(Long id);
}
