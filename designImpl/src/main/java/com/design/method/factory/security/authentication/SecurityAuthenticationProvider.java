package com.design.method.factory.security.authentication;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SecurityAuthenticationToken result = new SecurityAuthenticationToken((String)authentication.getCredentials(), (String)authentication.getPrincipal());
        //todo 这里面可以实现登陆逻辑 入参从 authentication 里面取
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(SecurityAuthenticationToken.class);
    }

}
