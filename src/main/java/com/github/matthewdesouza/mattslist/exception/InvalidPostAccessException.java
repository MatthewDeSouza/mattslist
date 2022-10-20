package com.github.matthewdesouza.mattslist.exception;

/**
 * Thrown if a given {@link com.github.matthewdesouza.mattslist.entity.Post} does not belong to a supplied {@link com.github.matthewdesouza.mattslist.entity.Topic}.
 *
 * @author Matthew DeSouza
 */
public class InvalidPostAccessException extends Exception {
    public InvalidPostAccessException(String errorMessage) {
        super(errorMessage);
    }
}
