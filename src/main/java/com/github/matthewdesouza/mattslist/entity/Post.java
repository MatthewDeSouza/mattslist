package com.github.matthewdesouza.mattslist.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"user", "topic"})
@ToString
@Entity
public class Post {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Setter(AccessLevel.NONE)
    @Column(name = "creation_date", updatable = false)
    LocalDateTime creationDate;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
    String content;

    @ManyToOne
    @JoinTable(
            name = "post_user_ref",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_topic_ref",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    Topic topic;
}
