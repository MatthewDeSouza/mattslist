package com.github.matthewdesouza.springforum.repository;

import com.github.matthewdesouza.springforum.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Set<Post> findPostByContentContains(String content);
    Post findPostById(Long id);
}
