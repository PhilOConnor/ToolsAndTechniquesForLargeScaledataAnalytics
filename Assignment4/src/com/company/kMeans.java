package com.company;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.api.java.JavaRDD;

import scala.Tuple2;

import javax.sound.midi.SysexMessage;
import java.util.Arrays;


public class kMeans {
    // Creating the javabean to store the tweets
    public static class AnnotatedTweet implements java.io.Serializable{
        private String tweet;
        private int spam;

        public AnnotatedTweet(){

        }
        public void setTweet(String tweet){
            this.tweet=tweet;
        }

        public String getTweet() {
            return tweet;
        }

        public void setSpam(int spam) {
            this.spam = spam;
        }

        public int getSpam() {
            return spam;
        }
    }

    public static void main(String[] args) {
	// write your code here
        SparkConf sparkConf = new SparkConf()
                .setAppName("kMeans")
                .setMaster("local[4]");
        //.set("spark.executor.memory", "1g")
        JavaSparkContext sc = new JavaSparkContext(sparkConf);


        String path = "twitter2D_2.txt"; // if necessary amend path
        JavaRDD<String> data = sc.textFile(path);

        // Parse the data into a labelled point with a vector and the spam label - will need some other way to join
        // in the tweet text somehow
        JavaRDD<Tuple2<Vector, AnnotatedTweet>> parsedData = data
                .map(y -> {
                    String[] values = y.split(",");
                    int spam = Integer.parseInt(values[0]);
                    String tweet = values[1];
                    Double xCoord = Double.parseDouble(values[2]);
                    Double yCoord = Double.parseDouble(values[3]);
                    AnnotatedTweet annotatedTweet = new AnnotatedTweet();
                    annotatedTweet.setTweet(tweet);
                    annotatedTweet.setSpam(spam);


                    return new Tuple2<Vector, AnnotatedTweet>(Vectors.dense(xCoord,yCoord), annotatedTweet);
                });

        parsedData.cache();


        int numOfClusters = 4;

        int numOfIterations = 100;

        KMeansModel model = KMeans.train(parsedData.map(p -> p._1).rdd(), numOfClusters, numOfIterations);
        JavaRDD<Tuple2<AnnotatedTweet, Integer>> tweetsWithClusterIndices = parsedData.map(p -> new Tuple2<AnnotatedTweet, Integer>(p._2, model.predict(p._1)));
        tweetsWithClusterIndices.foreach(y-> System.out.println("Tweet " + y._1.getTweet() + " is in cluster " + y._2));

        // Now create another rdd that will reduce by key





        }

}

