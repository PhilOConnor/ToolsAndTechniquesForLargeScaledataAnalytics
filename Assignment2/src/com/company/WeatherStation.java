package com.company;

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
        // Create a new empty stream to avoid returning nulls for streams with no outputs
        //Stream<String> streamEmpty = Stream.empty();
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



        // Checking that the daya is being stored properly
        System.out.println(city);
        System.out.println(stations);
        System.out.println("Station 1 readings: "+w1.measurements);
        System.out.println("Station 2 readings: "+w2.measurements);


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