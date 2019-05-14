package com.romanidze.metrikano.clickhouseservice.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

/**
 * 04.03.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class VKUser {

    private String id;
    private String username;
    private Long userID;
    private String userType;
    private String firstName;
    private String lastName;
    private Boolean isClosed;
    private Boolean canAccessClosed;
    private Long cityID;
    private String cityTitle;
    private Long countryID;
    private String countryTitle;
    private Short hasPhoto;
    private Short online;
    private Short canPost;
    private Long lastSeenTime;
    private Short platform;
    private Long photoCounter;
    private Long friendCounter;
    private String creationTime;

}
