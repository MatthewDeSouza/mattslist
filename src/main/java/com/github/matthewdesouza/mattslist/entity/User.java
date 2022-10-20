package com.github.matthewdesouza.mattslist.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Model for the User entity
 * @author Matthew DeSouza
 */
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = "roles")
@Entity
public class User {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "creation_date", updatable = false)
    LocalDateTime creationDate;

    @Column(name = "username", unique = true, nullable = false)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_post_ref",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    Set<Post> posts;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_ref",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }
    )
    Set<Role> roles = new HashSet<>();

    public void addPost(Post post) {
        if (posts == null) {
            posts = new HashSet<>();
        }
        posts.add(post);
        post.setUser(this);
    }
}
