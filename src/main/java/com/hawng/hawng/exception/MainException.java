package com.hawng.hawng.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class MainException extends RuntimeException {
    public MainException(String message) {
        super(message);
    }

    public MainException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getstatsCod();

    public final Map<String, String> validation = new HashMap<>();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }

}
