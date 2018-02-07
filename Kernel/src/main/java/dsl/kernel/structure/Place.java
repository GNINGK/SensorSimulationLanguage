package main.java.dsl.kernel.structure;

import main.java.dsl.kernel.NamedElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxime
 */
public class Place implements NamedElement {

    private String name;
    private List<Sensor> sensors;

    public Place() {
        sensors = new ArrayList<>();
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

}
