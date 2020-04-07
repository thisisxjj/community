package com.xia.community.exception;

/**
 * @author thisXjj
 * @date 2020/4/7 6:01 下午
 */
public class QuestionException extends RuntimeException {
    private String message;
    public QuestionException(String message) {
        this.message = message;
    }
    public QuestionException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }
    @Override
    public String getMessage() {
        return message;
    }
}
