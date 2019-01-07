package com.next.parkomator.service;

import com.next.parkomator.model.SensorData;

import java.util.List;

public interface LocationService {
    List<SensorData> getNearbyLocations(double lat, double lng, int radius);
}
