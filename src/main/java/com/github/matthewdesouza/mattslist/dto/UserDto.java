package com.github.matthewdesouza.mattslist.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * DTO Object for performing CRUD operations on a User within the Database.
 * @author Matthew DeSouza
 */
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserDto {
    Long id;
    String username;
    String password;
    LocalDateTime creationDate;
}
