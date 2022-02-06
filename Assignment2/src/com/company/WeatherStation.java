package com.company;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

public class WeatherStation {
    static String city;
    // Creating a list of measurement objects
    List<Object> measurements = new ArrayList<Object>();

    //Measurement measurements = new Measurement();
    static List<String> stations = new ArrayList<String>();

    double maxTemperature(int startTime, int endTime){
        // Calculate the max temp between two timestamps using Java 8 streams, returning the max temp.
        // Will require a filter and a max function.
        //Stream<String> init = measurements.stream();
        //Stream<Double> minTime =init.filter(temperature -> time>startTime).count();
        //return minTime;
        return 2;
    };

    public static void main(String[] args) {
        // write your code here


        stations.add("Dublin Airport"); // Weather Station 1 - ws1
        stations.add("MET office");     // Weather Station 2 - ws2
        city = "Dublin";

        // Adding weather stations & adding measurements
        WeatherStation w1 = new WeatherStation();
        w1.measurements.add(new Measurement(2, 16d));
        w1.measurements.add(new Measurement(2, 16d));
        w1.measurements.add(new Measurement(3, 12d));
        w1.measurements.add(new Measurement(4, 20d));
        w1.measurements.add(new Measurement(5, 22d));


        WeatherStation w2 = new WeatherStation();
        w2.measurements.add(new Measurement(2, 13d));
        w2.measurements.add(new Measurement(2, 14d));
        w2.measurements.add(new Measurement(3, 16d));
        w2.measurements.add(new Measurement(4, 14d));
        w2.measurements.add(new Measurement(5, 25d));



        // Checking that the data is being stored properly
        System.out.println(city);
        System.out.println(stations);
        System.out.println("Station 1 readings: "+w1.measurements);
        System.out.println("Station 1 counts "+w1.maxTemperature(1,3));
        System.out.println("Station 2 readings: "+w2.measurements);
        System.out.println("Station 2 readings: "+w2.measurements.get(1));


        //w2.measurements.stream().allMatch(time -> valueInt(w2.measurements.get(0))>1);
        /*Integer max = w1.measurements
                .stream()
                .mapToInt(v -> v[0])
                .max().orElseThrow(NoSuchElementException::new);

         */
    }

    static class Measurement{
        // Class constructor
        public Measurement(int time, double temperature){
            this.time = time;
            this.temperature = temperature;
        };
        // Overriding toString so I can see the values instead of location in memory
        @Override
        public String toString(){
            return (this.time +":"+this.temperature);
       }
        public int time;
        public double temperature;
    }


}