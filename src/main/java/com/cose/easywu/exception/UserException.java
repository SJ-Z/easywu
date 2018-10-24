package com.cose.easywu.exception;

public class UserException extends Exception {
    //异常信息
    public String message;

    public UserException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
