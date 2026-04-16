package com.example.exception;

/**
 * Exception thrown when an obfuscation process fails.
 */
public class ObfuscationException extends YGuarderException {
    public ObfuscationException(String message) {
        super(message);
    }

    public ObfuscationException(String message, Throwable cause) {
        super(message, cause);
    }
}
