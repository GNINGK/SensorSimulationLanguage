/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsl.kernel;

import dsl.kernel.definition.CSVLoader;
import dsl.kernel.definition.Comportement;
import dsl.kernel.definition.Functions;
import dsl.kernel.definition.Tuple;
import dsl.kernel.structure.Capteur;
import dsl.kernel.structure.Lieu;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maxime
 */
public class Test {

    int tempsTotal;
    static Comportement csvLoader;
    static Comportement functions;
    static Simulation simu;
    static Lieu batA;

    public static void main(String[] args) {
        //csvLoader = new CSVLoader("dataSource.csv", "sensor1", 2, 10);
        double[] polynome = new double[3];
        polynome[0] = 1.0;
        polynome[1] = 4.0;
        polynome[2] = 6.0;
        functions = new Functions(polynome);
        
        simu = new Simulation(5);
        batA = new Lieu();
        batA.setName("batA");

        List<Capteur> listC = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listC.add(new Capteur("sensor"+i,functions,2));
        }
        
        batA.addCapteur(listC);
        simu.setLieux(batA);
        
        simu.run();
    }
}
