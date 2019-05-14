package com.romanidze.metrikano.influxservice.config.influx;

import com.romanidze.metrikano.influxservice.domain.Record;

import org.influxdb.dto.Point;

import org.springframework.data.influxdb.converter.PointCollectionConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 28.02.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class RecordConverter implements PointCollectionConverter<Record> {

    private static final String MEASUREMENT_NAME = "group_metrics";

    @Override
    public List<Point> convert(Record source) {

        Point point = Point.measurement(MEASUREMENT_NAME)
                           .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                           .tag("post_type", source.getPostType())
                           .addField("id", source.getId())
                           .addField("username", source.getUsername())
                           .addField("group_id", source.getGroupID())
                           .addField("post_text", source.getPostText())
                           .addField("post_id", source.getPostID())
                           .addField("creation_time", source.getCreationTime())
                           .build();

        final List<Point> resultList = new ArrayList<>(1);
        resultList.add(point);
        return resultList;

    }
}
