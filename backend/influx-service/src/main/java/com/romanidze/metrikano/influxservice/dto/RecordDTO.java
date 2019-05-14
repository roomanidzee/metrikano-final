package com.romanidze.metrikano.influxservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

/**
 * 28.02.2019
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
public class RecordDTO {

    @JsonProperty("id")
    private String id;

    private String username;

    @JsonProperty("group_id")
    private String groupID;

    @JsonProperty("post_text")
    private String postText;

    @JsonProperty("post_type")
    private String postType;

    @JsonProperty("post_id")
    private String postID;

    @JsonProperty("creation_time")
    private String creationTime;

}
