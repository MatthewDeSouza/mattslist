package com.github.matthewdesouza.springforum.service.impl;

import com.github.matthewdesouza.springforum.entity.Post;
import com.github.matthewdesouza.springforum.repository.PostRepository;
import com.github.matthewdesouza.springforum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository repository;

    @Autowired
    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    /**
     * Find {@link Post} by id within the database.
     * @param id Id of post.
     * @return {@link Post}
     */
    @Override
    public Post findPostById(Long id) {
        return repository.findPostById(id);
    }

    /**
     * Saves a {@link Post} to the database.
     * @param post Post to save.
     */
    @Override
    public void savePost(Post post) {
        repository.save(post);
    }

    /**
     * Deletes a {@link Post} from the database.
     * @param post Post to delete.
     */
    @Override
    public void deletePost(Post post) {
        repository.delete(post);
    }

    /**
     * Deletes a {@link Post} from the database by id.
     * @param id Post id to delete.
     */
    @Override
    public void deletePostById(Long id) {
        repository.deleteById(id);
    }
}
