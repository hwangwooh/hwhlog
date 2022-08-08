package com.hawng.hawng.exception;

import lombok.Getter;

@Getter
public class InvalidRequest extends MainException{

    private static final String MESSAGE = "잘못된 요청입니다";




    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName,message);


    }

    public InvalidRequest(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getstatsCod() {
        return 400;
    }
}
