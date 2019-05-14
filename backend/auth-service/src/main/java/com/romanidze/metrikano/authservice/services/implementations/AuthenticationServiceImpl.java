package com.romanidze.metrikano.authservice.services.implementations;

import com.romanidze.metrikano.authservice.config.security.authentication.JWTTokenAuthentication;
import com.romanidze.metrikano.authservice.config.security.properties.JWTProperties;
import com.romanidze.metrikano.authservice.domain.User;
import com.romanidze.metrikano.authservice.dto.request.LoginDTO;
import com.romanidze.metrikano.authservice.dto.response.LoginResponseDTO;
import com.romanidze.metrikano.authservice.dto.response.TokenInfoDTO;
import com.romanidze.metrikano.authservice.mappers.mybatis.UserDBMapper;
import com.romanidze.metrikano.authservice.services.interfaces.AuthenticationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 28.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDBMapper userDBMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTProperties jwtProperties;

    @Autowired
    public AuthenticationServiceImpl(UserDBMapper userDBMapper,
                                     PasswordEncoder passwordEncoder,
                                     JWTProperties jwtProperties) {
        this.userDBMapper = userDBMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public User getUserByAuthentication(Authentication authentication) {

        if(authentication == null){
            authentication = SecurityContextHolder.getContext().getAuthentication();
        }

        JWTTokenAuthentication jwtTokenAuthentication = (JWTTokenAuthentication) authentication;

        String token = jwtTokenAuthentication.getName();

        Claims body;

        try{
            body = Jwts.parser()
                       .setSigningKey(this.jwtProperties.getSecretKey())
                       .parseClaimsJws(token)
                       .getBody();
        }catch (MalformedJwtException | SignatureException e){
            throw new AuthenticationServiceException(
                    MessageFormat.format("Произошла ошибка при работе с токеном: {0}", e.getMessage())
            );
        }

        String username = body.get("username").toString();

        User user =
          this.userDBMapper.findByUsername(username)
                           .orElseThrow(() ->
         new IllegalArgumentException(MessageFormat.format("Пользователь с логином {0} не найден", username)));

        return user;

    }

    @Override
    public TokenInfoDTO checkToken(Authentication authentication) {

        if(authentication == null){
            authentication = SecurityContextHolder.getContext().getAuthentication();
        }

        JWTTokenAuthentication jwtTokenAuthentication = (JWTTokenAuthentication) authentication;

        String token = jwtTokenAuthentication.getName();
        boolean expired;

        try{
            Jwts.parser()
                .setSigningKey(this.jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

            expired = false;

        }catch (ExpiredJwtException e){
            expired = true;
        }catch (MalformedJwtException | SignatureException e){
            throw new AuthenticationServiceException(
                    MessageFormat.format("Произошла ошибка при работе с токеном: {0}", e.getMessage())
            );
        }

        return TokenInfoDTO.builder()
                           .token(token)
                           .expired(expired)
                           .build();

    }

    @Override
    public LoginResponseDTO loginUser(LoginDTO loginDTO) {

        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        User user =
         this.userDBMapper.findByUsername(username)
                         .orElseThrow(() ->
         new IllegalArgumentException(MessageFormat.format("Пользователь с логином {0} не найден", username)));

        if(this.passwordEncoder.matches(password, user.getPassword())){

            LocalDateTime time = LocalDateTime.now(ZoneId.of("Europe/Moscow"))
                                              .plusSeconds(this.jwtProperties.getExpiresAt());

            ZonedDateTime zonedTime = time.atZone(ZoneId.of("Europe/Moscow"));
            Date expirationTime = Date.from(zonedTime.toInstant());

            String token = Jwts.builder()
                               .setSubject(user.getId().toString())
                               .setExpiration(expirationTime)
                               .claim("role", user.getRole())
                               .claim("state", user.getState())
                               .claim("username", user.getUsername())
                               .signWith(SignatureAlgorithm.HS512, this.jwtProperties.getSecretKey())
                               .compact();

            String dateFormat = "dd.MM.yyyy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(dateFormat);

            return LoginResponseDTO.builder()
                                   .username(user.getUsername())
                                   .token(token)
                                   .expireTime(df.format(expirationTime))
                                   .build();


        }else{
            throw new IllegalArgumentException("Пользователь с таким логином и паролем не найден");
        }

    }
}
