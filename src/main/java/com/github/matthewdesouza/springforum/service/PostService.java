package com.github.matthewdesouza.springforum.service;

import com.github.matthewdesouza.springforum.entity.Post;

public interface PostService {
    Post findPostById(Long id);
    void savePost(Post post);
    void deletePost(Post post);
    void deletePostById(Long id);
}
