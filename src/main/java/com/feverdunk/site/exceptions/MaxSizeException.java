package com.feverdunk.site.exceptions;

public class MaxSizeException extends RuntimeException{
    public MaxSizeException() {
    }

    public MaxSizeException(String message) {
        super(message);
    }

    public MaxSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaxSizeException(Throwable cause) {
        super(cause);
    }
}
