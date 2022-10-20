package com.github.matthewdesouza.mattslist.repository;

import com.github.matthewdesouza.mattslist.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for interacting with {@link JpaRepository} for the {@link Post} model
 * @author Matthew DeSouza
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * Finds {@link Post} by id.
     * @param id {@link Long}
     * @return {@link Post}
     */
    Post findPostById(Long id);
}
