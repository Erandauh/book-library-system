package com.collabera.book.library.system.collabera.book.library.system.application.exceptions;

public class BookRegistrationFailureException extends RuntimeException {

    public BookRegistrationFailureException(String message) {
        super(message);
    }

    public BookRegistrationFailureException(String message, Exception inner) {
        super(message, inner);
    }
}
