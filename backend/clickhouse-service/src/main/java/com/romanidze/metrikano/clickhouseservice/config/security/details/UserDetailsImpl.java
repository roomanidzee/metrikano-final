package com.romanidze.metrikano.clickhouseservice.config.security.details;

import com.romanidze.metrikano.clickhouseservice.config.security.enums.Role;
import com.romanidze.metrikano.clickhouseservice.config.security.enums.UserState;
import com.romanidze.metrikano.clickhouseservice.config.security.models.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 29.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl(User user){
        this.user = user;
    }

    public UserDetailsImpl(Long id, String role, String state, String username){

        boolean roleCondition = Arrays.stream(Role.values())
                                      .map(Role::toString)
                                      .anyMatch(userRole -> userRole.equals(role));

        if(!roleCondition){
            throw new IllegalArgumentException(MessageFormat.format("Роли {0} не существует", role));
        }

        boolean stateCondition = Arrays.stream(UserState.values())
                                       .map(UserState::toString)
                                       .anyMatch(userState -> userState.equals(state));

        if(!stateCondition){
            throw new IllegalArgumentException(MessageFormat.format("Состояния {0} не существует", state));
        }

        this.user = User.builder()
                        .id(id)
                        .role(role)
                        .state(state)
                        .username(username)
                        .build();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(this.user.getRole());
        return List.of(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !UserState.valueOf(this.user.getState()).equals(UserState.DELETED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !UserState.valueOf(this.user.getState()).equals(UserState.BANNED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !UserState.valueOf(this.user.getState()).equals(UserState.NOT_CONFIRMED);
    }

    @Override
    public boolean isEnabled() {
        return UserState.valueOf(this.user.getState()).equals(UserState.CONFIRMED);
    }

    public User getUser() {
        return this.user;
    }
}
