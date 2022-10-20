package com.github.matthewdesouza.mattslist.service.impl;

import com.github.matthewdesouza.mattslist.entity.Post;
import com.github.matthewdesouza.mattslist.repository.PostRepository;
import com.github.matthewdesouza.mattslist.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link PostService}.
 *
 * @author Matthew DeSouza
 */
@Slf4j
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository repository;

    @Autowired
    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    /**
     * Find {@link Post} by id within the database.
     * @param id The id of the post.
     * @return {@link Post}
     */
    @Override
    public Post findPostById(Long id) {
        log.info("Finding post by id (id={}).", id);
        return repository.findPostById(id);
    }

    /**
     * Saves a {@link Post} to the database.
     * @param post Post to save.
     */
    @Override
    public void savePost(Post post) {
        log.info("Saving Post (id={}, title={}).", post.getId(), post.getTitle());
        repository.save(post);
    }

    /**
     * Deletes a {@link Post} from the database.
     * @param post Post to delete.
     */
    @Override
    public void deletePost(Post post) {
        log.info("Deleting Post (id={}, title={}).", post.getId(), post.getTitle());
        post.getTopic().removePost(post);
        repository.delete(post);
    }

    /**
     * Deletes a {@link Post} from the database by id.
     * @param id Post id to delete.
     */
    @Override
    public void deletePostById(Long id) {
        log.info("Deleting Post by id (id={}).", id);
        repository.deleteById(id);
    }
}
