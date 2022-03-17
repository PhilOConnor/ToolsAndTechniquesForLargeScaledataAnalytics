package com.company;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;


import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;


public class Assignment5 {

    public static void main(String[] args) throws InterruptedException {
        // write your code here
        SparkConf conf = new SparkConf()
                .setAppName("Assignment5")
                .setMaster("local[2]");


        JavaStreamingContext jssc = new JavaStreamingContext(conf, new Duration(2000)); // Change the batch interval to 2 seconds
        // These two lines remove a lot of unneccessary output that clutters the desired output
        JavaSparkContext sc = jssc.sparkContext();
        sc.setLogLevel("ERROR");


        JavaReceiverInputDStream<String> lines = jssc.socketTextStream("localhost", 9999);
        // Print the first ten elements of each RDD generated in this DStream to the console
        JavaDStream<String> tweets = lines.map(
                new Function<String, String>() {
                    @Override
                    public String call(String tweets) throws Exception {
                        return tweets.toString();
                    }
                }
        );
        tweets.print();

        jssc.start();
        jssc.awaitTermination();

    }
}
