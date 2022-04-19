package com.company;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.Arrays;

import static jodd.util.StringPool.SPACE;


public class Assignment5b {

    public static void main(String[] args) throws InterruptedException {
        // write your code here
        SparkConf conf = new SparkConf()
                .setAppName("Assignment5")
                .setMaster("local[12]");


        JavaStreamingContext jssc = new JavaStreamingContext(conf, new Duration(2000)); // Change the batch interval to 2 seconds
        // These two lines remove a lot of unneccessary output that clutters the desired output
        JavaSparkContext sc = jssc.sparkContext();
        sc.setLogLevel("ERROR");

        JavaReceiverInputDStream<String> lines = jssc.socketTextStream("localhost", 9999);
        // Override the jssc window with one specified in the question.
        JavaDStream<String> windowTweets = lines.window(new Duration(6000), new Duration(2000));
        // Print the first ten elements of each RDD generated in this DStream to the console
        JavaDStream<String> tweets = windowTweets.map(
                new Function<String, String>() {
                    @Override
                    public String call(String tweets) throws Exception {
                        return tweets.toString();
                    }
                }
        );
        // Filter out the hashtags

        JavaDStream<String> hashTags = tweets.flatMap((String s) -> Arrays.asList(s.split(" ")).iterator()).filter(y->y.contains("#"));
        // Filter out the mentions
        JavaDStream<String> mentions = tweets.flatMap((String s) -> Arrays.asList(s.split(" ")).iterator()).filter(y->y.contains("@"));
        // Count by hashtag/mentiontag
        JavaPairDStream<String, Long> tagCounts = hashTags.countByValue();
        JavaPairDStream<String, Long> mentionsCount = mentions.countByValue();

        // combine the two streams into one
        JavaPairDStream<String, Long> counters = tagCounts.union(mentionsCount);
        counters.print();
        jssc.start();
        jssc.awaitTermination();
        jssc.stop();
        jssc.close();

    }
}
