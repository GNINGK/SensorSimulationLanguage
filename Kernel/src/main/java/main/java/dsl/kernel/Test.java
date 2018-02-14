package main.java.dsl.kernel;

import main.java.dsl.kernel.definition.*;

/**
 * @author Maxime
 */
public class Test {

    public static void main(String[] args) {
        CSVLoader csvLoader = new CSVLoader("dataSource.csv", "sensor0", 0, 10);
        System.out.println("0 : " + csvLoader.createData(0, 0));
        System.out.println("1 : " + csvLoader.createData(1, 0));
        System.out.println("2 : " + csvLoader.createData(2, 0));
        System.out.println("3 : " + csvLoader.createData(3, 0));
        /*
        Simulation simulation = new Simulation(100);
        Place batA = new Place();
        batA.setName("batA");

        List<Sensor> listC = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listC.add(new Sensor("sensor" + i, csvLoader, 1));
        }

        batA.addSensors(listC);
        simulation.addPlaces(batA);

        simulation.run();*/

        /*double[] polynome2 = {0.0, 5.0, -2.0};
         double[] polynome = {0.0};
         Functions functions = new Polynomial(polynome, 20);
         Functions functions2 = new Polynomial(polynome2, 20);
         IntervalFunctions ifs = new IntervalFunctions();
         Interval i = new Interval(0.0, 0.22);
         Interval i2 = new Interval(0.22, 2.28);
         Interval i3 = new Interval(2.28, 3.0);
         ifs.add(i, functions);
         ifs.add(i2, functions2);
         ifs.add(i3, functions);
         System.out.println("0.21 : " + ifs.createData(0.21f, 0));
         System.out.println("0.5 : " + ifs.createData(0.5f, 0));
         System.out.println("1.0 : " + ifs.createData(1.0f, 0));
         System.out.println("1.5 : " + ifs.createData(1.5f, 0));
         System.out.println("2.29 : " + ifs.createData(2.29f, 0));*/
    }
}
