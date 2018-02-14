package main.java.dsl.kernel;

import main.java.dsl.kernel.definition.*;

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
        Double[] polynome2 = {0.0, 5.0, -2.0};
        Double[] polynome = {0.0};
        Functions functions = new Polynomial(polynome, 20);
        Functions functions2 = new Polynomial(polynome2, 20);
        IntervalFunctions ifs = new IntervalFunctions();
        Interval i = new Interval(0.0, 0.22);
        Interval i2 = new Interval(0.22, 2.28);
        Interval i3 = new Interval(2.28, 3.0);
        ifs.add(i, functions);
        ifs.add(i2, functions2);
        ifs.add(i3, functions);
        System.out.println("0.21 : " + ifs.createData(0.21f));
        System.out.println("0.5 : " + ifs.createData(0.5f));
        System.out.println("1.0 : " + ifs.createData(1.0f));
        System.out.println("1.5 : " + ifs.createData(1.5f));
        System.out.println("2.29 : " + ifs.createData(2.29f));

    }
}
