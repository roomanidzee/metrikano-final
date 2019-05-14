package com.romanidze.metrikano.influxservice.config.security.providers;

import com.romanidze.metrikano.influxservice.config.security.authentication.JWTTokenAuthentication;
import com.romanidze.metrikano.influxservice.config.security.details.UserDetailsImpl;
import com.romanidze.metrikano.influxservice.config.security.properties.JWTProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;


/**
 * 29.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class JWTTokenAuthenticationProvider implements AuthenticationProvider {

    private final JWTProperties jwtProperties;

    @Autowired
    public JWTTokenAuthenticationProvider(JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JWTTokenAuthentication tokenAuthentication = (JWTTokenAuthentication) authentication;

        Claims body;

        try{
            body = Jwts.parser()
                       .setSigningKey(this.jwtProperties.getSecretKey())
                       .parseClaimsJws(tokenAuthentication.getName())
                       .getBody();
        }catch (MalformedJwtException | SignatureException e){
            throw new AuthenticationServiceException(
                    MessageFormat.format("Произошла ошибка при работе с токеном: {0}", e.getMessage())
            );
        }

        UserDetails userDetails = new UserDetailsImpl(
                Long.parseLong(body.get("sub").toString()),
                body.get("role").toString(),
                body.get("state").toString(),
                body.get("username").toString()
        );

        tokenAuthentication.setUserDetails(userDetails);
        tokenAuthentication.setAuthenticated(true);

        return tokenAuthentication;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTTokenAuthentication.class.equals(authentication);
    }
}
