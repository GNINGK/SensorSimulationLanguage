package main.java.dsl.kernel;

import main.java.dsl.kernel.definition.Functions;
import main.java.dsl.kernel.definition.Polynomial;
import main.java.dsl.kernel.structure.Place;
import main.java.dsl.kernel.structure.Sensor;

import java.util.ArrayList;
import java.util.List;
import main.java.dsl.kernel.definition.Interval;

/**
 * @author Maxime
 */
public class Test {

    public static void main(String[] args) {
        /*//csvLoader = new CSVLoader("dataSource.csv", "sensor1", 2, 10);
        double[] polynome = {0, 2, -1.0 / 10.0};
        Functions functions = new Polynomial(polynome, 20);

        Simulation simulation = new Simulation(100);
        Place batA = new Place();
        batA.setName("batA");

        List<Sensor> listC = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listC.add(new Sensor("sensor" + i, functions, 1));
        }

        batA.addSensors(listC);
        simulation.addPlaces(batA);

        simulation.run();*/
        
        Interval inter = new Interval(Double.NEGATIVE_INFINITY, 2.0);
    }
}
