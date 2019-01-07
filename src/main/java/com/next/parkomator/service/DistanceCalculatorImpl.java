package com.next.parkomator.service;

import com.next.parkomator.annotation.Dev;
import com.next.parkomator.annotation.RegisterUDF;

@RegisterUDF
@Dev
public class DistanceCalculatorImpl implements DistanceCalculator {
    @Override
    public Integer call(Double lat1, Double lon1, Double lat2, Double lon2) throws Exception {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        return (int) Math.round(rad2deg(Math.acos(dist)) * 60 * 1.1515 * 1609.344);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public String getName() {
        return "dCalc";
    }
}
