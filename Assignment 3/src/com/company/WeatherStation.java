package com.company;
import java.io.Serializable;
import java.util.*;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;

import scala.Tuple2;

public class WeatherStation implements Serializable {
    public static final long serialVersionUID = 1L;
    String city;
    static List<WeatherStation> stations = new ArrayList<WeatherStation>();

    List<Measurement> measurements = new ArrayList<Measurement>();
    public WeatherStation (String city){
        this.city = city;
    };




    double maxTemperature(int startTime, int endTime){
        return this.measurements.stream()
                .filter(x->x.getTime()>startTime && x.getTime()<endTime)
                .mapToDouble(x->x.getTemp())
                .max()
                .getAsDouble();
    };


    static int countTemperature(JavaSparkContext sc, Integer t){
         JavaPairRDD<Integer, Integer> distData = sc.parallelize(WeatherStation.stations)
                .flatMap(ws -> ws.measurements.iterator())
                .filter(m->m.getTemp()>= t-5 && m.getTemp()<= t+5)
                .mapToPair(m-> new Tuple2<Integer, Integer>(t,1))
                 .reduceByKey((Integer a,Integer b)-> a+b);
         List<Tuple2<Integer,Integer>> output= distData.collect();
        sc.stop();
        sc.close();
         return output.get(0)._2();


    }



    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "C:/winutils");
        SparkConf sparkConf = new SparkConf()
                .setAppName("WeatherStation")
                .setMaster("local[4]");
        //.set("spark.executor.memory", "1g")
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        // write your code here

        // Adding weather stations & adding measurements
        WeatherStation w1 = new WeatherStation("Dublin");
        WeatherStation.stations.add(w1);

        Measurement m1 = new Measurement();
        m1.setTime(1);
        m1.setTemp(22);

        Measurement m2 = new Measurement();
        m2.setTime(2);
        m2.setTemp(20);

        Measurement m3 = new Measurement();
        m3.setTime(3);
        m3.setTemp(25);

        Measurement m4 = new Measurement();
        m4.setTime(4);
        m4.setTemp(18);

        w1.measurements.add(m1);
        w1.measurements.add(m2);
        w1.measurements.add(m3);
        w1.measurements.add(m4);


        WeatherStation w2 = new WeatherStation("Dublin");
        WeatherStation.stations.add(w2);
        Measurement m5 = new Measurement();
        m5.setTime(1);
        m5.setTemp(17);

        Measurement m6 = new Measurement();
        m6.setTime(2);
        m6.setTemp(19);

        Measurement m7 = new Measurement();
        m7.setTime(3);
        m7.setTemp(22);

        Measurement m8 = new Measurement();
        m8.setTime(4);
        m8.setTemp(20);

        w2.measurements.add(m5);
        w2.measurements.add(m6);
        w2.measurements.add(m7);
        w2.measurements.add(m8);


        w1.countTemperature(sc, 20);
        //System.out.println(distData);


    }

    static class Measurement{
        public int time;
        public double temperature;
        // Class constructor
        //public Measurement(int time, double temperature){
        //   this.time = time;
        //    this.temperature = temperature;

        //};
        public void setTime(int time){
            this.time=time;
        }
        public int getTime(){
            return time;
        }
        public void setTemp(double temperature){
            this.temperature=temperature;
        }
        public double getTemp(){
            return temperature;
        }

        // Overriding toString so I can see the values instead of location in memory
        @Override
        public String toString(){
            return (this.time +":"+this.temperature);
        }





    }


}