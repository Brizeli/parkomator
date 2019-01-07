package com.next.parkomator;

import com.next.parkomator.annotation.Dev;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MainConfig {

    @Bean
    @Dev
    public SparkConf sparkConf() {
        return new SparkConf().setAppName("parkomator").setMaster("local[*]");
    }

    @Bean
    public JavaSparkContext sc(SparkConf sc) {
        return new JavaSparkContext(sc);
    }

    @Bean
    public SQLContext sqlContext(JavaSparkContext sc) {
        return new SQLContext(sc);
    }
}
