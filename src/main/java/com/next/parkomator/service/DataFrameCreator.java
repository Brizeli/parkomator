package com.next.parkomator.service;

import org.apache.spark.sql.DataFrame;

public interface DataFrameCreator {
    DataFrame getDataFrame();
}
