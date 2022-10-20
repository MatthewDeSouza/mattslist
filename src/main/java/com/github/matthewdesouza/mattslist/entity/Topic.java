package com.github.matthewdesouza.mattslist.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(exclude = "posts")
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "name")
    String name;

    @OneToMany(cascade = CascadeType.ALL)
    Set<Post> posts = new HashSet<>();

    public void addPost(Post post) {
        posts.add(post);
        post.setTopic(this);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.setTopic(null);
    }
}
