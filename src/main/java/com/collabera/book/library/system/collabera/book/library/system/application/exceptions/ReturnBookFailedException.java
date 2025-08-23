package com.collabera.book.library.system.collabera.book.library.system.application.exceptions;

public class ReturnBookFailedException extends RuntimeException {

    public ReturnBookFailedException(String message) {
        super(message);
    }

    public ReturnBookFailedException(String message, Exception inner) {
        super(message, inner);
    }
}
