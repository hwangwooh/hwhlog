package com.hawng.hawng.exception;

public  class PostNotFound extends MainException{

    private static final String message = "잘못된 요청입니다";

    public PostNotFound() {
        super(message);
    }

    public PostNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getstatsCod() {
        return 404;
    }
}
