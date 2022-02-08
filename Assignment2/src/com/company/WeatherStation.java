package com.company;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WeatherStation {
    String city;
    static List<String> stations = new ArrayList<String>();

    List<Measurement> measurements = new ArrayList<Measurement>();
    public WeatherStation (String city, String stations){
        this.city = city;
    };




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

        // Adding weather stations & adding measurements
        WeatherStation w1 = new WeatherStation("Dublin", "Airport");
        Measurement m1 = new Measurement();
        m1.setTime(1);
        m1.setTemp(22);

        Measurement m2 = new Measurement();
        m2.setTime(2);
        m2.setTemp(10);

        Measurement m3 = new Measurement();
        m3.setTime(3);
        m3.setTemp(50);

        Measurement m4 = new Measurement();
        m4.setTime(4);
        m4.setTemp(-274);

        w1.measurements.add(m1);
        w1.measurements.add(m2);
        w1.measurements.add(m3);
        w1.measurements.add(m4);

        System.out.println(m1.getTime());
        System.out.println(w1.measurements.get(0).getTime());

        w1.measurements.stream()
                .filter(x->x.getTime()>1 && x.getTime()<5)
                .mapToDouble(x->x.getTemp()).max()
                .forEach(x-> System.out.println(x));
        /// Fix this

        // Checking that the data is being stored properly

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