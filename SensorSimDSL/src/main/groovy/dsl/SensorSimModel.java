package main.groovy.dsl;

import groovy.lang.Binding;
import main.groovy.utils.FunctionType;
import main.java.dsl.kernel.Simulation;
import main.java.dsl.kernel.definition.*;
import main.java.dsl.kernel.structure.Place;
import main.java.dsl.kernel.structure.Sensor;
import org.apache.log4j.*;

import java.util.HashMap;
import java.util.Map;


public class SensorSimModel {
    private Simulation simulation;
    private Map<String, Place> places;
    private static Logger logger = Logger.getLogger(SensorSimModel.class);

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

        this.binding.setVariable(name, place);
    }

    public void createLaw(String name, FunctionType type) {
        Behavior law = null;

        if(type == FunctionType.UNKNOWN){
            logger.error("Function type unknown thus law: " + name + " will not be defined !");
        } else if(type == FunctionType.AGGREGATE){
            this.binding.setVariable(name, new SumAggregating());
            return;
        }

        switch (type) {
            case POLYNOMIAL:
                law = new Polynomial(0, 20);
                break;
            case INTERVAL:
                law = new IntervalFunctions();
                break;
            case MARKOV:
                law = new Markov();
                break;
            case CSV:
            case JSON:
                law = new FileLoader();
                break;
        }

        this.binding.setVariable(name, law);
    }

    public void deleteLaw(String name){
        this.binding.setVariable(name, null);
    }

    public void createSensor(String sensorName, Behavior behavior, int samples) {
        if(behavior == null){
            logger.warn("Specified law for: " + sensorName + " not found thus sensor was not created !");
            return;
        }

        Sensor sensor = new Sensor(sensorName, behavior, samples);
        sensor.setName(sensorName);

        this.binding.setVariable(sensorName, sensor);
    }

    private void addSensor(Place place, Sensor sensor) {
        place.addSensor(sensor);
    }

    public void addSensors(Place place, Sensor sensor, int sensorNb) {
        if (place == null){
            logger.warn("Place specified is not defined thus sensor can't be added !");
            return;
        }

        if(sensor == null){
            logger.warn("Sensor was not found thus sensor won't be added to place: " + place.getName() + " !");
            return;
        }

        int i = 0;
        while (i < sensorNb) {
            Sensor sensorCopy = new Sensor(sensor.getName() + "_" + i, sensor.getLaw(), sensor.getSampling());
            addSensor(place, sensorCopy);
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
