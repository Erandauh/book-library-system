package com.collabera.book.library.system.application.exceptions;

public class BorrowBookFailedException extends RuntimeException {

    public BorrowBookFailedException(String message) {
        super(message);
    }

    public BorrowBookFailedException(String message, Exception inner) {
        super(message, inner);
    }
}
