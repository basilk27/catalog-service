package com.mbsystems.catalogservice.exceptions;

public class BooAlreadyExistsException extends RuntimeException {

    public BooAlreadyExistsException(String isbn) {
        super("A book with ISBN " + isbn + " already exists.");
    }
}
