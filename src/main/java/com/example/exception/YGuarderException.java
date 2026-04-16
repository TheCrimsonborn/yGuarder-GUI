package com.example.exception;

/**
 * Base checked exception for the yGuarder application.
 */
public class YGuarderException extends Exception {
    public YGuarderException(String message) {
        super(message);
    }

    public YGuarderException(String message, Throwable cause) {
        super(message, cause);
    }
}
