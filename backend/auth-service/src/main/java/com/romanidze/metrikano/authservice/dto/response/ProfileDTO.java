package com.romanidze.metrikano.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

/**
 * 28.04.2019
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
public class ProfileDTO {

    @JsonProperty("user_id")
    private Long userID;

    private String surname;
    private String name;
    private String patronymic;
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

}
