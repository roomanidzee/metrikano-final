package com.romanidze.metrikano.clickhouseservice.config.security;

import com.romanidze.metrikano.clickhouseservice.config.security.filters.JWTTokenAuthenticationFilter;
import com.romanidze.metrikano.clickhouseservice.config.security.providers.JWTTokenAuthenticationProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * 29.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTTokenAuthenticationProvider jwtTokenAuthenticationProvider;
    private final JWTTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public SecurityConfig(JWTTokenAuthenticationProvider jwtTokenAuthenticationProvider,
                          JWTTokenAuthenticationFilter jwtTokenAuthenticationFilter) {
        this.jwtTokenAuthenticationProvider = jwtTokenAuthenticationProvider;
        this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.addFilterBefore(this.jwtTokenAuthenticationFilter, BasicAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/user/**").hasAuthority("USER")
            .antMatchers("/admin/**").hasAuthority("ADMIN")
            .and()
            .csrf()
            .disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(this.jwtTokenAuthenticationProvider);
    }

}
