package com.xia.community.exception;

/**
 * @author thisXjj
 * @date 2020/4/7 7:30 下午
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("您找的问题不存在，要不要换个试试?"),
    QUESTION_CREATE_ERROR("创建问题出现了异常，请联系管理员"),
    USER_CREATE_OR_UPDATE_ERROR("用户登录出现了异常");
    private String message;
    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
