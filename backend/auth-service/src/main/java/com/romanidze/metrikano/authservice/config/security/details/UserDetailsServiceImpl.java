package com.romanidze.metrikano.authservice.config.security.details;

import com.romanidze.metrikano.authservice.domain.User;
import com.romanidze.metrikano.authservice.mappers.mybatis.UserDBMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * 28.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDBMapper userDBMapper;

    @Autowired
    public UserDetailsServiceImpl(UserDBMapper userDBMapper) {
        this.userDBMapper = userDBMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user =
         this.userDBMapper.findByUsername(username)
                          .orElseThrow(() ->
         new IllegalArgumentException(MessageFormat.format("Пользователь с логином {0} не найден", username)));

        return new UserDetailsImpl(user);
    }
}
