package com.design.method.factory.security.authentication;


import lombok.Data;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;


public class SecurityAuthenticationToken extends AbstractAuthenticationToken {

    private String  userName;

    private String userPhone;

    public SecurityAuthenticationToken(String userName, String userPhone){
        super(null);
        this.userName = userName;
        this.userPhone = userPhone;
        super.setAuthenticated(false);
    }

    public SecurityAuthenticationToken(String userName, String userPhone,
                                               Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userName = userName;
        this.userPhone = userPhone;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return this.userName;
    }

    @Override
    public Object getPrincipal() {
        return this.userPhone;
    }


}
