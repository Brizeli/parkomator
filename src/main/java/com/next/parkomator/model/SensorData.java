package com.next.parkomator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.spark.sql.Row;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorData implements Serializable {
    Long id;
    Double lat, lng;
    boolean free;
    long timestamp;
    int distance;

    @Override
    public String toString() {
        return id + "," + lat + "," + lng + "," + free + "," + timestamp;
    }

    public static SensorData fromLine(String line) {
        String[] l = line.split(",");
        return SensorData.builder()
                .id(Long.valueOf(l[0]))
                .lat(Double.valueOf(l[1]))
                .lng(Double.valueOf(l[2]))
                .free(Boolean.parseBoolean(l[3]))
                .timestamp(Long.parseLong(l[4]))
                .build();
    }

    public static SensorData fromRow(Row r) {
        return SensorData.builder()
                .id(r.getLong(0))
                .lat(r.getDouble(1))
                .lng(r.getDouble(2))
                .distance(r.getInt(3)).build();
    }
}
