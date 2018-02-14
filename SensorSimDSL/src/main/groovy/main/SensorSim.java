package main.groovy.main;

import main.groovy.dsl.SensorSimDSL;

import java.io.File;

public class SensorSim {
    public static void main(String[] args) {
        SensorSimDSL dsl = new SensorSimDSL();
        if(args.length > 0) {
            dsl.eval(new File(args[0]));
        } else {
            System.out.println("/!\\ Missing arg: Please specify the path to a Groovy script file to execute");
        }
    }
}
