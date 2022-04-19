/*
package com.company;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;


public class Assignment5d {

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

        JavaDStream<String> hashTags = tweets.flatMap((String s) -> Arrays.asList(s.split(" ")).iterator()).filter(y->y.contains("#"));
        JavaDStream<String> mentions = tweets.flatMap((String s) -> Arrays.asList(s.split(" ")).iterator()).filter(y->y.contains("@"));
        JavaPairDStream<String, Long> tagCounts = hashTags.countByValue();
        JavaPairDStream<String, Long> mentionsCount = mentions.countByValue();
        JavaPairDStream<String, Long> counters = tagCounts.union(mentionsCount);


        // Creating second 6-second window and interval to count the all words in the window
        JavaDStream<String> windowTweets2 = lines.window(new Duration(6000), new Duration(6000));
        // Print the first ten elements of each RDD generated in this DStream to the console
        JavaDStream<String> tweets2 = windowTweets2.map(
                new Function<String, String>() {
                    @Override
                    public String call(String tweets) throws Exception {
                        return tweets.toString();
                    }
                }
        );
        // Count all the words in the 6-second window
        JavaDStream<Long> wordCount = tweets2.count();

                /* hint from Aaron - create 2 streams - 1) word pairs, words : counts
                                     2) count of all words
                                     3) 'join' on key - create dummy keys to do this


        // Below code does not work, but I think it demonstrates the concept Aaron was talking about in the tutorial for this.
        // This is how I want to create my dummy keys - take the keys from the counters stream and give them the count of all words for values
        JavaPairDStream<String, Long> wordCountDummys = counters.map(y-> new Tuple2<String, Long>(y._1, wordCount));
        // Now join the counters and wordCountDummies streams
        JavaPairDStream<String, Tuple2<Long, Long>> joinedStream = counters.join(wordCountDummys);

        // Now calculate the frequencies of the word counts by dividing the 2 stored values and return them as wordFreq
        JavaDStream<Object> wordFreq = joinedStream.map(y-> new Tuple2<String, Long>(y._1, y._2._1/y._2._2));

        // This is how I had originally planned on calculating this but was getting an error message
        //JavaPairDStream<String, Long> wordFreq = counters.map(y->new Tuple2<String, Long>(y._1, y._2/wordCount));

        jssc.checkpoint("Checkpoints");
        wordFreq.print();

        jssc.start();
        jssc.awaitTermination();
        jssc.stop();
        jssc.close();

    }
}
*/