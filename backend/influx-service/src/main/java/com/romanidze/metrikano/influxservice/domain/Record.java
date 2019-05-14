package com.romanidze.metrikano.influxservice.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

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
@Measurement(name = "group_metrics")
public class Record {

    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "group_id")
    private String groupID;

    @Column(name = "post_text")
    private String postText;

    @Column(name = "post_type", tag = true)
    private String postType;

    @Column(name = "post_id")
    private String postID;

    @Column(name = "creation_time")
    private String creationTime;

}
