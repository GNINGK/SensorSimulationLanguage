package main.java.dsl.kernel.structure;

import main.java.dsl.kernel.NamedElement;

import java.util.ArrayList;
import java.util.List;
import main.java.dsl.kernel.definition.AggregatingLaw;
import main.java.dsl.kernel.definition.Tuple;

/**
 * @author Maxime
 */
public class Place<T> implements NamedElement {

    private String name;
    private List<Sensor> sensors;
    private AggregatingLaw aggregLaw;
    private int totalTime;

    public Place() {
        this.sensors = new ArrayList<>();
        aggregLaw = null;
        totalTime = 0;
    }

    public Place(String name) {
        this.name = name;
        this.sensors = new ArrayList<>();
        aggregLaw = null;
        totalTime = 0;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return La liste des sensors
     */
    public List<Sensor> getSensors() {
        return sensors;
    }

    public Sensor getSensorByName(String name) {
        Sensor result = null;
        for (Sensor c : sensors) {
            if (c.getName().equals(name)) {
                result = c;
            }
        }
        return result;
    }

    public void addSensor(Sensor cap) {
        sensors.add(cap);
    }

    public void addSensors(List<Sensor> cap) {
        sensors.addAll(cap);
    }

    public List<Tuple> getDatasSensor() {

        if (aggregLaw != null) {
            return compositeSensor();
        } else {
            return classicSensor();
        }
    }

    public List<Tuple> classicSensor() {
        List<Tuple> result = new ArrayList<>();
        for (int i = 0; i < this.getTotalTime(); i++) {
            for (Sensor s : sensors) {
                if (i % s.getSampling() == 0) {
                    long t = System.currentTimeMillis() - this.getTotalTime() * 1000 + i * 1000;
                    result.add(new Tuple(t, s.getName(), this.name, s.generateData(i)));
                }
            }
        }
        return result;
    }

    public List<Tuple> compositeSensor() {
        List<Tuple> result = new ArrayList<>();
        for (int i = 0; i < this.getTotalTime(); i++) {
            List<Tuple> listTemp = new ArrayList<>();
            for (Sensor s : sensors) {
                if (i % s.getSampling() == 0) {

                    listTemp.add(new Tuple(i, s.getName(), this.name, s.generateData(i)));
                }

            }
            long t = System.currentTimeMillis() - this.getTotalTime() * 1000 + i * 1000;
            result.add(new Tuple(t, "compositeSensor" + getName(), this.name, aggregLaw.aggregate(listTemp)));
        }
        return result;
    }

    /**
     * @return the aggregLaw
     */
    public AggregatingLaw getAggregLaw() {
        return aggregLaw;
    }

    /**
     * @param aggregLaw the aggregLaw to set
     */
    public void setAggregLaw(AggregatingLaw aggregLaw) {
        this.aggregLaw = aggregLaw;
    }

    /**
     * @return the totalTime
     */
    public int getTotalTime() {
        return totalTime;
    }

    /**
     * @param totalTime the totalTime to set
     */
    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

}
