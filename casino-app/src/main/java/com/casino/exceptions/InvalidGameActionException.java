package com.casino.exceptions;

public class InvalidGameActionException extends Exception {
    public InvalidGameActionException(String message) {
        super(message);
    }

    public InvalidGameActionException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
