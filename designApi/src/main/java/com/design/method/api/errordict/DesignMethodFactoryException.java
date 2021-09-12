package com.design.method.api.errordict;


import java.io.IOException;

public class DesignMethodFactoryException extends RuntimeException {

    private int errorCode;

    private String msg;


    public DesignMethodFactoryException(int errorCode, String msg) {
        super();
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public DesignMethodFactoryException() {
        super();
    }

    public DesignMethodFactoryException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
