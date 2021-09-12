package com.design.method.factory.security.jwt;



import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.factory.security.authentication.SecurityAuthenticationToken;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.design.method.api.errordict.ErrorMsgMap.*;


@Component
public class JwtFilter extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    private static final String Login_method = "/security/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (Login_method.equals(request.getRequestURI())) {
            return true;
        }
        String token = request.getHeader("token");
        if (Strings.hasText(token)) {
            jwtUtil.validateJwtToken(token);
            return true;
        } else {
            throw  new DesignMethodFactoryException(NOT_VALIDATA_TOKEN);
        }
    }
}
