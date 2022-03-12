package com.company;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.api.java.JavaRDD;

import org.apache.spark.sql.sources.In;
import scala.Tuple2;

import javax.sound.midi.SysexMessage;
import java.util.Arrays;
import java.util.List;


public class kMeans {
    // Creating the javabean to store the tweets
    // 3 rules for java beans
    // 1 - implements Serializable
    // 2 - Blank constructor
    // 3 - All properties are private with public getters and setters
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

        // Parse the data into a tuple2 of vector AnnotatedTweet javabean
        JavaRDD<Tuple2<Vector, AnnotatedTweet>> parsedData = data
                .map(y -> {
                    // Bharath said this isn't the best way to split the data as there may be commas in the tweets,
                    // in this case there isn't but I'm aware it could be an issue. Split each tweet item into an array of strings
                    String[] values = y.split(",");
                    // Parse the tweet data into the needed features
                    int spam = Integer.parseInt(values[0]); // The spam feature
                    String tweet = values[1]; // The tweet main body
                    Double xCoord = Double.parseDouble(values[2]); // X coordinate
                    Double yCoord = Double.parseDouble(values[3]); // y coordinate
                    AnnotatedTweet annotatedTweet = new AnnotatedTweet(); // Create a new annotatedTweet from the Java Bean
                    annotatedTweet.setTweet(tweet); // Set the tweet and spam properties of the annotatedTweet object
                    annotatedTweet.setSpam(spam);

                    // Return the tuple from the code block as a tuple2 with the coordinates as a dense vector and the annotatedTweet
                    // Tuple2 won't allow for 3 items so tweet and spam flag had to be encoded in the annotatedTweet object.
                    return new Tuple2<Vector, AnnotatedTweet>(Vectors.dense(xCoord,yCoord), annotatedTweet);
                });
        // Bharath provided the below as a skeleton
        parsedData.cache();
        int numOfClusters = 4;
        int numOfIterations = 100;

        KMeansModel model = KMeans.train(parsedData.map(p -> p._1).rdd(), numOfClusters, numOfIterations);

        JavaRDD<Tuple2<AnnotatedTweet, Integer>> tweetsWithClusterIndices = parsedData.map(p -> new Tuple2<AnnotatedTweet, Integer>(p._2, model.predict(p._1)));
        // Sort by taken from https://www.tabnine.com/code/java/methods/org.apache.spark.api.java.JavaRDD/sortBy
        tweetsWithClusterIndices.sortBy(Tuple2::_2,true, 2).foreach(y-> System.out.println("Tweet " + y._1.getTweet() + " is in cluster " + y._2));

        // Create a filter for only the spam tweets
        JavaRDD<Tuple2<AnnotatedTweet, Integer>> spamOnly = tweetsWithClusterIndices.filter(y->y._1.getSpam()==1);
        // Below was inspired by the wordCount example when setting up Spark
        // For every spam tweet, create JavaPairRDD so that the rdd is (ClusterID, 1)
        JavaPairRDD<Integer/*Cluster*/, Integer/*Ones*/> ones =  spamOnly.mapToPair(y -> new Tuple2<Integer, Integer>(y._2, 1));
        // For each clusterID, sum up all the 1s and collect them.
        JavaPairRDD<Integer, Integer> counts = ones.reduceByKey((Integer i1, Integer i2) -> i1 + i2);
        List<Tuple2<Integer, Integer>> output = counts.collect();

        for (Tuple2<?, ?> tuple : output)
            System.out.println("Cluster " + tuple._1 + " contains " + tuple._2 + " spam tweets");
        sc.stop();
        sc.close();








        }

}

