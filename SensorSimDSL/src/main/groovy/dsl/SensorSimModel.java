package main.groovy.dsl;

import groovy.lang.Binding;
import main.java.dsl.kernel.Simulation;
import main.java.dsl.kernel.definition.Functions;
import main.java.dsl.kernel.definition.IntervalFunctions;
import main.java.dsl.kernel.definition.Polynomial;
import main.java.dsl.kernel.structure.Place;
import main.java.dsl.kernel.structure.Sensor;

import java.util.HashMap;
import java.util.Map;


public class SensorSimModel {
    private Simulation simulation;
    private Map<String, Place> places;

    private Binding binding;

    public SensorSimModel(Binding binding) {
        this.simulation = new Simulation();
        this.places = new HashMap<>();
        this.binding = binding;
    }

    public void createSimulation(String name, int totalTime) {
        simulation.setName(name);
        simulation.setTotalTime(totalTime);
    }

    public void createPlace(String name) {
        Place place = new Place();
        place.setName(name);
        places.put(name, place);
    }

    public void createLaw(String name, String type, Double[] coefficients) {
        Functions law;
        switch (type){
            case "polynome" : law = new Polynomial(coefficients, 20); break;
            case "ifs" : law = new IntervalFunctions(); break;
            default: law = new Polynomial(new Double[0], 30); break;
        }

        this.binding.setVariable(name, law);
    }

    public void createSensor(String sensorName, Functions function, int samples) {
        Sensor sensor = new Sensor(sensorName, function, samples);
        sensor.setName(sensorName);

        this.binding.setVariable(sensorName, sensor);
        // System.out.println("> sensor " + sensorName + " in place " + placeName);
    }

    public void addSensor(String placeName, Sensor sensor) {
       places.get(placeName).addSensor(sensor);
    }

    public void addSensors(String placeName, Sensor sensor, int sensorNb) {
        int i = 0;
        while(i < sensorNb){
            addSensor(placeName, sensor);
            i++;
        }
    }

    public void runSimulation(){
        for(Place place : places.values()){
            simulation.addPlaces(place);
        }
        simulation.run();
    }

}
