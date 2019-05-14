package com.romanidze.metrikano.authservice.filters;

import lombok.SneakyThrows;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.Filter;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 06.05.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class CORSFilter implements Filter{

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain){

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("ORIGIN"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Expose-Headers",
                    "ticket, tempauth-token, auth-token, password, nickname, Confirm-Code");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, Accept, X-Requested-With, remember-me, " +
                "access-control-allow-origin, tempauth-token, Authorization, auth-token, ticket, " +
                "password, login, Confirm-Code, phone");
        if (!request.getMethod().equals("OPTIONS")) {
            chain.doFilter(req, res);
        }

    }
}
