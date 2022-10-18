package com.github.matthewdesouza.springforum.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Entity
public class User {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    public User() {
        creationDate = LocalDateTime.now();
    }

    @Column(name = "creation_date", insertable = false, updatable = false)
    LocalDateTime creationDate;

    @Column(name = "email", unique = true, nullable = false)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    Set<Post> posts;

    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    Set<Comment> comments;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "role_id")
    Role role;

    public void addPost(Post post) {
        if (posts == null) {
            posts = new HashSet<>();
        }
        posts.add(post);
        post.setUser(this);
    }

    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new HashSet<>();
        }
        comments.add(comment);
        comment.setUser(this);
    }
}
