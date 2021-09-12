package com.design.method.factory.security.authentication;

import com.design.method.api.errordict.DesignMethodFactoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.design.method.api.errordict.ErrorMsgMap.NOT_ALLOW_TO_ACCESS;

@Component
@Slf4j
/**
 *it will excute when have no rigth to access origin method
 */
public class NotPromitAccessHandle implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("no right to access:" + request.getRequestURI() + "/" + request.getMethod());
        throw new ServletException(accessDeniedException);
    }
}
