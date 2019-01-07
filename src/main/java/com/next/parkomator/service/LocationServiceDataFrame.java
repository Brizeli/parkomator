package com.next.parkomator.service;

import com.next.parkomator.model.SensorData;
import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.*;

@Service
public class LocationServiceDataFrame implements LocationService {
    private static final String FREE = "free";
    private static final String LAT = "lat";
    private static final String LNG = "lng";
    private static final String DISTANCE = "distance";
    private static final String ID = "id";

    @Autowired
    private DataFrameCreator dfCreator;
    @Autowired
    private DistanceCalculatorImpl dCalc;

    @Override
    public List<SensorData> getNearbyLocations(double lat, double lng, int radius) {
        DataFrame df = dfCreator.getDataFrame();
        return df
                .withColumn(DISTANCE, callUDF(dCalc.getName(), col(LAT), col(LNG), lit(lat), lit(lng)))
                .where(col(FREE).equalTo(true))
                .where(col(DISTANCE).leq(radius))
                .select(ID, LAT, LNG, DISTANCE)
                .orderBy(col(DISTANCE))
                .collectAsList()
                .stream()
                .map(SensorData::fromRow)
                .collect(Collectors.toList());
    }
}
