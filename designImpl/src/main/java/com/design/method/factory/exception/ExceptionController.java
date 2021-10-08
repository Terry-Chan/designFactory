package com.design.method.factory.exception;

import com.design.method.api.Response.Response;
import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.api.errordict.ErrorMsgMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;

@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(DesignMethodFactoryException.class)
    public Response handleDesignException(DesignMethodFactoryException e){
        return new Response().ERROR(e.getErrorCode(), ErrorMsgMap.getOrDefault(e.getErrorCode()));
    }

    @ExceptionHandler(ServletException.class)
    public Response handleServletException(ServletException e) {
        return new Response().ERROR(e.hashCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e){
        return new Response().ERROR();
    }

}
