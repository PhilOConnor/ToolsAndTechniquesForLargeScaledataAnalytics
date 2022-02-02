package com.company;

import java.util.Arrays;
import java.util.List;

public class WeatherStation {
    String city;
    static String [] strArray = {"time", "temperature"};
    static List<Object> measurements = Arrays.asList(strArray);
    //Measurement measurements = new Measurement();
    static List<String> stations;

    public static void main(String[] args) {
	// write your code here
        Measurement m = new Measurement();
        System.out.println(measurements);


    }

    static class Measurement{
        int time ;
        double temperature;
        double maxTemp(){
        return 2;

        };

    }
}
