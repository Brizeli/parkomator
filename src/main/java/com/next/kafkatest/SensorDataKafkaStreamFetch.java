package com.next.kafkatest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.parkomator.model.SensorData;
import kafka.serializer.StringDecoder;
import lombok.SneakyThrows;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class SensorDataKafkaStreamFetch {
    public static void main(String[] args) throws Exception {
        JavaStreamingContext ssc;
        try (PrintWriter w = new PrintWriter(new FileOutputStream("d:/sensordata/sensordata.json", true))) {
            SparkConf conf = new SparkConf().setAppName("parkomator").setMaster("local[*]");
            ssc = new JavaStreamingContext(conf, new Duration(10000));
            Set<String> topics = Collections.singleton("sensor");
            Map<String, String> kafkaParams = new HashMap<>();
            kafkaParams.put("metadata.broker.list", "localhost:9092");

            JavaPairInputDStream<String, String> stream = KafkaUtils.createDirectStream(ssc,
                    String.class, String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topics);
            ObjectMapper mapper = new ObjectMapper();
            stream.map(Tuple2::_2)
                    .map(s -> mapper.readValue(s, SensorData.class))
                    .foreachRDD((VoidFunction<JavaRDD<SensorData>>) sensorRDD -> {
                        List<SensorData> sensorData = sensorRDD.collect();
                        sensorData.forEach(sd -> {
                            System.out.println(sd);
                            try {
                                w.println(mapper.writeValueAsString(sd));
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    });
        }
        ssc.start();
        ssc.awaitTermination();
    }
}
