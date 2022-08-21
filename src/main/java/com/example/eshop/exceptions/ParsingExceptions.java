package com.example.eshop.exceptions;

public class ParsingExceptions extends Exception {
    public ParsingExceptions(String message) {
        super(message);
    }

    public ParsingExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
