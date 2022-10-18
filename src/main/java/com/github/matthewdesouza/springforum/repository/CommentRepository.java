package com.github.matthewdesouza.springforum.repository;

import com.github.matthewdesouza.springforum.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Set<Comment> findCommentsByContentContaining(String content);
}
