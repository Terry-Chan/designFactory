package com.design.method.api.Response;

import com.design.method.api.errordict.ErrorMsgMap;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Response<T> {

    private int code;

    private String msg;

    private T data;

    public Response<T> SUCCESS(T data) {
        this.code = 0;
        this.msg ="成功";
        this.data = data;
        return this;
    }

    public Response<T> ERROR(int errorCode) {
        this.code = errorCode;
        this.msg = ErrorMsgMap.getOrDefault(errorCode);
        return this;
    }

    public Response<T> ERROR() {
        this.code = -1;
        this.msg = "Internal error";
        return this;
    }

    public Response<T> ERROR(int errorCode, String msg) {
        this.code = errorCode;
        this.msg = msg;
        return this;
    }

}
