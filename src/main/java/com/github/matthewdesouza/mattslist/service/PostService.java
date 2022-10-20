package com.github.matthewdesouza.mattslist.service;

import com.github.matthewdesouza.mattslist.entity.Post;

public interface PostService {
    Post findPostById(Long id);
    void savePost(Post post);
    void deletePost(Post post);
    void deletePostById(Long id);
}
