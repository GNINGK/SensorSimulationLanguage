package main.java.dsl.kernel;

import main.java.dsl.kernel.definition.*;
import main.java.dsl.kernel.structure.Place;
import main.java.dsl.kernel.structure.Sensor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Maxime
 */
public class Test {

    public static void main(String[] args) {

        Functions function1 = new Polynomial(new ArrayList<Double>(
                Arrays.asList(0.0, 2.0, -1.0 / 10.0)), 20);
        Functions function2 = new Polynomial(new ArrayList<Double>(
                Arrays.asList(0.0, 2.0, -1.0 / 5.0)), 10);

        Functions function3 = new Polynomial(new ArrayList<Double>(
                Arrays.asList(5.0)), 100);
        Functions function4 = new Polynomial(new ArrayList<Double>(
                Arrays.asList(0.0)), 100);

        IntervalFunctions ifs = new IntervalFunctions();
        Interval i = new Interval(0.0, 40.0);
        Interval i2 = new Interval(41.0, 60.0);
        Interval i3 = new Interval(61.0, 100.0);
        ifs.add(i, function4);
        ifs.add(i2, function3);
        ifs.add(i3, function4);

        CSVLoader csvLoader = new CSVLoader("dataSource.csv", "sensor0", 0, 10);
        int nbEtat = 2;
        String[] nameEtat = {"Sunny","Rainy"};
        float[][] transition = {{0.1f,0.9f},{0.5f,0.5f}};
        Markov markovLaw = new Markov(nbEtat, 0, nameEtat, transition);
        markovLaw.setFrequence(5);

        //Definitions des batiments
        Place batA = new Place("batA");

        //Definition de la simulation
        Simulation simulation = new Simulation(30);

        batA.addSensor(new Sensor<>("sensor_markov", markovLaw, 1));

        batA.addSensor(new Sensor("sensor_csv", csvLoader, 1));
        batA.addSensor(new Sensor("sensor1", function1, 1));
        batA.addSensor(new Sensor("sensor2", function2, 1));
        batA.addSensor(new Sensor("sensor_interval", ifs, 1));

        simulation.addPlaces(batA);

        simulation.run();
    }

}
