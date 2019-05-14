package com.romanidze.metrikano.postgresservice.config.security.filters;

import com.romanidze.metrikano.postgresservice.config.security.authentication.JWTTokenAuthentication;
import com.romanidze.metrikano.postgresservice.config.security.properties.JWTProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 29.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class JWTTokenAuthenticationFilter implements Filter {

    private final JWTProperties jwtProperties;

    @Autowired
    public JWTTokenAuthenticationFilter(JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String token = request.getHeader(this.jwtProperties.getHeader());

        JWTTokenAuthentication authentication = null;

        if(token == null){

            authentication = new JWTTokenAuthentication(null);
            authentication.setAuthenticated(false);

        }else{
            authentication = new JWTTokenAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(req, resp);

    }
}
