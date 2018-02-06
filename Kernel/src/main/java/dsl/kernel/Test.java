/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsl.kernel;

import dsl.kernel.definition.CSVLoader;
import dsl.kernel.definition.Comportement;
import dsl.kernel.definition.Tuple;
import java.util.List;

/**
 *
 * @author Maxime
 */
public class Test {
    static Comportement csvLoader = new CSVLoader("dataSource.csv","sensor1",0,10);
    public static void main(String[] args) {
        List<Tuple> result = csvLoader.createData();
        System.out.println(result.size()); 
        for(Tuple  t : result)
        {
                 System.out.println(t.toString());   
        }
    }
}
