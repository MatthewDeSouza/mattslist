package com.github.matthewdesouza.mattslist.repository;

import com.github.matthewdesouza.mattslist.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for interacting with {@link JpaRepository} for the {@link Topic} model
 * @author Matthew DeSouza
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    /**
     * Finds {@link Topic} by name.
     * @param name {@link String}
     * @return {@link List}
     */
    List<Topic> findTopicByName(String name);

    /**
     * Finds {@link Topic} by id.
     * @param id {@link Long}
     * @return {@link Topic}
     */
    Topic findTopicById(Long id);
}
