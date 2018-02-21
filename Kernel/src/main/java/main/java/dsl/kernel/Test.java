package main.java.dsl.kernel;

import main.java.dsl.kernel.definition.*;
import main.java.dsl.kernel.structure.Place;
import main.java.dsl.kernel.structure.Sensor;

/**
 * @author Maxime
 */
public class Test {

    public static void main(String[] args) {

        //Definition des lois
        Functions function1 = new Polynomial(new Double[]{2.0, 5.0, -3.0});
        Functions function3 = new Polynomial(new Double[]{5.0});
        Functions function4 = new Polynomial(new Double[]{1.0});
        IntervalFunctions ifs = new IntervalFunctions();
        ifs.add(new Interval(0.0, 10.0), function4);
        ifs.add(new Interval(11.0, 20.0), function3);
        ifs.add(new Interval(21.0, 30.0), function4);
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
        //batA.addSensor(new Sensor<>("sensor0", csvLoader, 1));
        batA.addSensor(new Sensor<>("sensor0", markovLaw, 1));
        simulation.addPlaces(batA);

        simulation.run();
    }

}
