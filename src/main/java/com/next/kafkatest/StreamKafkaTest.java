package com.next.kafkatest;

import kafka.serializer.StringDecoder;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StreamKafkaTest {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("parkomator").setMaster("local[*]");
        JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(10000));
        Set<String> topics = Collections.singleton("sensor");
        Map<String, String> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", "localhost:9092");

        JavaPairInputDStream<String, String> stream = KafkaUtils.createDirectStream(ssc,
                String.class, String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topics);
        stream.map(Tuple2::_2)
//                .filter(s -> Integer.parseInt(s.replaceAll("\\D+", "")) % 2 == 0)

                .print();
        ssc.start();
        ssc.awaitTermination();
    }
}
