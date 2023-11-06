package com.feverdunk.site.exceptions;

public class EntityAlreadyExistsExeption extends RuntimeException{
    public EntityAlreadyExistsExeption(String message) {
        super(message);
    }

    public EntityAlreadyExistsExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyExistsExeption(Throwable cause) {
        super(cause);
    }
}
