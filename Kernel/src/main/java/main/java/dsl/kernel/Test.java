/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.dsl.kernel;

import main.java.dsl.kernel.structure.Sensor;
import main.java.dsl.kernel.definition.Behavior;
import main.java.dsl.kernel.definition.Functions;
import main.java.dsl.kernel.structure.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxime
 */
public class Test {

    int simulationTotalTime;
    static Behavior csvLoader;
    static Behavior functions;
    static Simulation simu;
    static Place batA;

    public static void main(String[] args) {
        //csvLoader = new CSVLoader("dataSource.csv", "sensor1", 2, 10);
        double[] polynome = {1.0, 4.0, 6.0};
        //functions = new Functions(polynome);

        simu = new Simulation(5);
        batA = new Place();
        batA.setName("batA");

        List<Sensor> listC = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
           // listC.add(new Sensor("sensor" + i, functions, 2));
        }

        batA.addSensor(listC);
        simu.addPlaces(batA);

        simu.run();
    }
}
