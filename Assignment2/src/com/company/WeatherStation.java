package com.company;

import java.util.Arrays;
import java.util.List;

public class WeatherStation {
    String city;
    //static String [] strArray = {"time", "temperature"};
    //static List<Object> measurements = Arrays.asList(strArray);
    static String[] measurements = {"time", "temperature"};
    //Measurement measurements = new Measurement();
    static List<String> stations;

    double maxTemperature(int startTime, int endTime){
        return 2;

    };

    public static void main(String[] args) {
	// write your code here
        WeatherStation w = new WeatherStation();
        w.city = "Dublin";
        System.out.println(w.city);
        System.out.println(Arrays.toString(w.measurements));



    }

    static class Measurement{
        int time;
        double temperature;


    }
}
