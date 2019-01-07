package com.next.parkomator.service;

import com.next.parkomator.annotation.Dev;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Dev
public class DataFrameCreatorImpl implements DataFrameCreator {
    @Autowired
    private SQLContext sqlContext;
    @Autowired
    private DistanceCalculator dCalc;
    @Value("${localJsonPath}")
    private String path;

    @PostConstruct
    public void registerUDF() {
        sqlContext.udf().register(dCalc.getName(), dCalc, DataTypes.IntegerType);
    }

    @Override
    public DataFrame getDataFrame() {
        return sqlContext.read().json(path);
    }
}
