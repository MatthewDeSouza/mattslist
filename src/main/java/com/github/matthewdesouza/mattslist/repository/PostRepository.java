package com.github.matthewdesouza.mattslist.repository;

import com.github.matthewdesouza.mattslist.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findPostById(Long id);
}
