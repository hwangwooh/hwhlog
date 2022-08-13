package com.hawng.hawng.exception;

public class CommentNotFound extends MainException{

    private static final String message = "잘못된 요청입니다";

    public CommentNotFound() {
        super(message);
    }

    public CommentNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getstatsCod() {
        return 404;
    }
}
