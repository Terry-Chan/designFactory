package com.design.method.factory.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.factory.security.authentication.SecurityAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Date;
import java.util.Map;

import static com.design.method.api.errordict.ErrorMsgMap.NOT_VALIDATA_TOKEN;

@Component
public class JwtUtil {

    private static final String jwtKey = "terry_chen";


    public   String creatJwtToken(Map<String, String> map, Long expireTime) {
        Date now = new Date();
       return JWT.create().withClaim("header",map)
                .withExpiresAt(new Date(now.getTime() + expireTime))
                .sign(Algorithm.HMAC256(jwtKey));

    }

    public  void validateJwtToken(String token) throws DesignMethodFactoryException {
        try {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtKey)).build().verify(token);
        Map<String, Object> map = decodedJWT.getClaim("header").asMap();
        String jwtName = map.get("userName").toString();
        SecurityAuthenticationToken st =(SecurityAuthenticationToken) authentication;
        String sessionName = st.getCredentials().toString();

         if (!jwtName.equals(sessionName)) {
             throw new DesignMethodFactoryException(NOT_VALIDATA_TOKEN);
         }

        }catch (JWTVerificationException e) {
            throw new DesignMethodFactoryException(NOT_VALIDATA_TOKEN);
        }
    }


    public static void main(String[] args) {
//        String token = creatJwtToken("hhhhh","500000",30000L);
//        validateJwtToken(token);
//        System.out.println("yes");
    }

}
