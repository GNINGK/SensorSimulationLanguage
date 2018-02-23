package main.java.dsl.kernel;

import main.java.dsl.kernel.definition.*;
import main.java.dsl.kernel.structure.Place;
import main.java.dsl.kernel.structure.Sensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Maxime
 */
public class Test {

    public static void main(String[] args) {



        // Définitions des fonctions
        Functions function1 = new Polynomial(new ArrayList<>(
                Arrays.asList(0.0, 2.0, -1.0 / 10.0)), 0, 20);
        Functions function2 = new Polynomial(new ArrayList<>(
                Arrays.asList(0.0, 2.0, -1.0 / 5.0)), 0, 10);

        Functions function3 = new Polynomial(new ArrayList<>(
                Arrays.asList(10.0, 0.0, -1.0 / 10.0)), -10, 10);

        IntervalFunctions function_interval = new IntervalFunctions();
        Interval i = new Interval(0.0, 40.0);
        Interval i2 = new Interval(41.0, 60.0);
        Interval i3 = new Interval(61.0, 100.0);
        function_interval.add(i, function1);
        function_interval.add(i2, function2);
        function_interval.add(i3, function3);

        int nbEtat = 2;
        List<String> nameEtat = new ArrayList<>(
                Arrays.asList("Sunny", "Rainy"));
        List<float[]> transition = new ArrayList<>(
                Arrays.asList(new float[]{0.1f, 0.9f}, new float[]{0.5f, 0.5f}));

        Markov function_markov = new Markov(nbEtat, 0, nameEtat, transition);
        function_markov.setFrequency(5);

        FileLoader csvLoader = new FileLoader("resultat.csv", "sensor0", 0, 10);
        FileLoader jsonLoader = new FileLoader("resultat.json", "sensor0", 0, 10);

        Place batA = new Place();
        function_markov.setFrequency(5);

        //batA.addSensor(new Sensor("sensor_markov", function_markov, 1));

        //Definition de la simulation
        Simulation simulation = new Simulation(30);

        //batA.addSensor(new Sensor<>("sensor_markov", function_markov, 1));
        //batA.addSensor(new Sensor("sensor0", jsonLoader, 1));
        batA.addSensor(new Sensor("sensor1", csvLoader, 1));
        //batA.addSensor(new Sensor("sensor2", function3, 1));
        //batA.addSensor(new Sensor("sensor2", function2, 1));
        //batA.addSensor(new Sensor("sensor_interval", function_interval, 1));

        simulation.addPlaces(batA);

        simulation.run();

        //Test load CSV and JSON
        /*FileLoader csvLoader = new FileLoader("resultat.csv", "sensor0", 0, 10);
        System.out.println(csvLoader.getDataSource().toString());
        FileLoader jsonLoader = new FileLoader("resultat.json", "sensor0", 0, 10);
        System.out.println(jsonLoader.getDataSource().toString());*/
    }

}
