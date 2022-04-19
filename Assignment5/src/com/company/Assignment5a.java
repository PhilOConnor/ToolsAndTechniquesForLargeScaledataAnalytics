package com.company;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;


import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;


public class Assignment5a {

    public static void main(String[] args) throws InterruptedException {
        // write your code here
        SparkConf conf = new SparkConf()
                .setAppName("Assignment5")
                .setMaster("local[12]");


        JavaStreamingContext jssc = new JavaStreamingContext(conf, new Duration(2000)); // Change the batch interval to 2 seconds
        // These two lines remove a lot of unneccessary output that clutters the desired output
        JavaSparkContext sc = jssc.sparkContext();
        sc.setLogLevel("ERROR");
        // Create the connection to Twotter
        JavaReceiverInputDStream<String> lines = jssc.socketTextStream("localhost", 9999);

        // Map the tweets to lines and return them
        JavaDStream<String> tweets = lines.map(
                new Function<String, String>() {
                    @Override
                    public String call(String tweets) throws Exception {
                        return tweets.toString();
                    }
                }
        );
        // Print the first ten elements of each RDD generated in this DStream to the console
        tweets.print();
        jssc.start();
        jssc.awaitTermination();
        jssc.stop();
        jssc.close();

    }
}
