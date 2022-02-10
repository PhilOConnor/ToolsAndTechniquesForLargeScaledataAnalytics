package com.company;
import java.util.ArrayList;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.summarizingDouble;

public class WeatherStation {
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
/*
    double  countTemperatures(int t1, int t2, int r){
        return this.stations.stream()
                .flatMap(x::stream)
                .map(x->x.toString())
                .mapToDouble(x-> x.getTemp())
                .filter(x->x>=t1-r && x<=t1+r)
                .count();



    }
*/
    public static void main(String[] args) {
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


        System.out.println(m1.getTime());
        System.out.println(w1.measurements.get(0).getTime());
        System.out.println(w1.stations);



        System.out.println(m1.getTime());
        System.out.println(w2.measurements.get(0).getTime());
        System.out.println(w2.stations);



        System.out.println(w1.maxTemperature(1,5));
        //
        //countTemperatures(1,2,3);
        System.out.println("Testing countTemperature");

        w1.stations.stream()
                .flatMap(x-> x.measurements)
                .


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