package main.java.dsl.kernel;

import main.java.dsl.kernel.definition.Functions;
import main.java.dsl.kernel.definition.Polynomial;
import main.java.dsl.kernel.structure.Place;
import main.java.dsl.kernel.structure.Sensor;

/**
 * @author Maxime
 */
public class Test {

    public static void main(String[] args) {
        //csvLoader = new CSVLoader("dataSource.csv", "sensor1", 2, 10);
        Functions function1 = new Polynomial(new double[]{0, 2, -1.0 / 10.0}, 20);
        Functions function2 = new Polynomial(new double[]{0, 2, -1.0 / 5.0}, 10);

        Simulation simulation = new Simulation(100);

        Place batA = new Place("batA");
        batA.addSensor(new Sensor("sensor0", function1, 1));
        batA.addSensor(new Sensor("sensor1", function2, 1));

        simulation.addPlaces(batA);
        simulation.run();
    }
}
