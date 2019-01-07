package com.next.parkomator.service;

import org.apache.spark.sql.api.java.UDF4;

public interface DistanceCalculator extends UDF4<Double, Double, Double, Double, Integer>  {
    String getName();
}
