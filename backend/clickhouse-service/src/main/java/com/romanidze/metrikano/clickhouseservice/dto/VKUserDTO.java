package com.romanidze.metrikano.clickhouseservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "user_id", "user_type", "first_name", "last_name", "is_closed", "can_access_closed", "city", "country",
                    "has_photo", "online", "can_post", "last_seen", "counters", "creation_time"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class VKUserDTO {

    private String id;

    private String username;

    @JsonProperty("user_id")
    private Long userID;

    @JsonProperty("user_type")
    private String userType;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("is_closed")
    private boolean isClosed;

    @JsonProperty("can_access_closed")
    private boolean canAccessClosed;

    @JsonProperty("city")
    private CityDTO cityDTO;

    @JsonProperty("country")
    private CountryDTO countryDTO;

    @JsonProperty("has_photo")
    private Short hasPhoto;

    @JsonProperty("online")
    private Short online;

    @JsonProperty("can_post")
    private Short canPost;

    @JsonProperty("last_seen")
    private LastSeenDTO lastSeenDTO;

    @JsonProperty("counters")
    private CountersDTO countersDTO;

    @JsonProperty("creation_time")
    private String creationTime;

}
