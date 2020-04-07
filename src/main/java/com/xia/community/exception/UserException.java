package com.xia.community.exception;

/**
 * @author thisXjj
 * @date 2020/4/7 7:40 下午
 */
public class UserException extends RuntimeException {
    private String message;
    public UserException(String message) {
        this.message = message;
    }
    public UserException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }
    @Override
    public String getMessage() {
        return message;
    }
}
