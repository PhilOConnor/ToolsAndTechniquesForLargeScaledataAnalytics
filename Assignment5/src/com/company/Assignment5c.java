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


public class Assignment5c {

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
        //JavaPairDStream<String, Long> tagCounts = hashTags.countByValueAndWindow(new Duration(6000), new Duration(2000));
        JavaPairDStream<String, Long> tagCounts = hashTags.countByValue();
        JavaPairDStream<Long,String> swappedPair = tagCounts.mapToPair(Tuple2::swap);
        JavaPairDStream<Long,String> sortedStream = swappedPair.transformToPair(s -> s.sortByKey(false));
        JavaDStream<String> freqHashtag = sortedStream.map(y->y._2);




        jssc.checkpoint("Checkpoints");
        freqHashtag.print(1);


        jssc.start();
        jssc.awaitTermination();
        jssc.stop();
        jssc.close();

    }
}
