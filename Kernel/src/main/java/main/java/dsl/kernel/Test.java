package main.java.dsl.kernel;

import main.java.dsl.kernel.structure.Sensor;
import main.java.dsl.kernel.definition.Behavior;
import main.java.dsl.kernel.definition.Functions;
import main.java.dsl.kernel.structure.Place;
import main.java.dsl.kernel.structure.Sensor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxime
 */
public class Test {

    public static void main(String[] args) {
        //csvLoader = new CSVLoader("dataSource.csv", "sensor1", 2, 10);
        //functions = new Functions(polynome);
        double[] polynome = {0, 2, -1.0 / 5.0};
        Functions functions = new Functions(polynome, 10);

        Simulation simu = new Simulation(100);
        Place batA = new Place();
        batA.setName("batA");

        List<Sensor> listC = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
           // listC.add(new Sensor("sensor" + i, functions, 1));
        }

        batA.addSensors(listC);
        simu.addPlaces(batA);

        simu.run();
    }
}
