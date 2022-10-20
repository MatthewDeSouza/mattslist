package com.github.matthewdesouza.mattslist.service;

import com.github.matthewdesouza.mattslist.entity.Post;

/**
 * Interface of various CRUD operations.
 * @author Matthew DeSouza
 */
public interface PostService {
    /**
     * Finds posts by id.
     * @param id {@link Long}
     * @return {@link Post}
     */
    Post findPostById(Long id);

    /**
     * Saves {@link Post} to database.
     * @param post {@link Post}
     */
    void savePost(Post post);

    /**
     * Deletes {@link Post} from the database.
     * @param post
     */
    void deletePost(Post post);

    /**
     * Deletes {@link Post} from the database by id.
     * @param id {@link Long}
     */
    void deletePostById(Long id);
}
