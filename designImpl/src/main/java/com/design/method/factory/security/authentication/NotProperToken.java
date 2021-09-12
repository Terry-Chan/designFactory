package com.design.method.factory.security.authentication;

import com.design.method.api.errordict.DesignMethodFactoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.design.method.api.errordict.ErrorMsgMap.NOT_VALIDATA_TOKEN;

/**
 * without login or no validate token while excute this method
 */
@Component
@Slf4j
public class NotProperToken implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("not validate taken:" + request.getRequestURI() + "/" + request.getMethod());
        throw new ServletException(authException);
    }

}
