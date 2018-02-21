package main.groovy.dsl;

import groovy.lang.Binding;
import main.java.dsl.kernel.Simulation;
import main.java.dsl.kernel.definition.Functions;
import main.java.dsl.kernel.definition.IntervalFunctions;
import main.java.dsl.kernel.definition.Polynomial;
import main.java.dsl.kernel.structure.Place;
import main.java.dsl.kernel.structure.Sensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        System.out.println("Place " + name + " created");
        Place place = new Place();
        place.setName(name);
        places.put(name, place);
    }

    public void createLaw(String name, String type) {
        Functions law;

        switch (type) {
            case "polynome":
                law = new Polynomial(0, 20);
                break;
            case "interval":
                law = new IntervalFunctions();
                break;
            default:
                law = new Polynomial(0, 30);
                break;
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
        while (i < sensorNb) {
            Sensor sensorCopy = new Sensor(sensor.getName() + "_" + i, sensor.getLaw(), sensor.getEchantillonnage());
            addSensor(placeName, sensorCopy);
            i++;
        }
    }

    public void runSimulation() {
        for (Place place : places.values()) {
            simulation.addPlaces(place);
        }
        simulation.run();
    }

}
