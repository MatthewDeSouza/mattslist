package com.github.matthewdesouza.springforum.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Entity
public class Comment {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    public Comment() {
        creationDate = LocalDateTime.now();
    }

    @Column(name = "creation_date", insertable = false, updatable = false)
    LocalDateTime creationDate;

    @Lob
    @Column(name = "content", nullable = false)
    String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    Post post;
}
