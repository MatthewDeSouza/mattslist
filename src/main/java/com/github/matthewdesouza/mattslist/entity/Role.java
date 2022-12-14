package com.github.matthewdesouza.mattslist.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

/**
 * Model for the Role entity
 * @author Matthew DeSouza
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(exclude = "users")
@Entity
public class Role {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", unique = true)
    String name;

    @Setter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "roles")
    Set<User> users = new HashSet<>();

    public void removeUser(User user) {
        users.remove(user);
        user.getRoles().remove(this);
    }
}
