package dsl.kernel.definition;

import java.util.Date;

/**
 *
 * @author Maxime
 */
public class Tuple {

    private int time;
    private String value;
    private String sensor;

    public Tuple(int time, String sensor, String value) {
        this.time = time;
        this.value = value;
        this.sensor = sensor;
    }

    @Override
    public String toString()
    {
        return time + "," + sensor + "," + value;
    }
    /**
     * @return the time
     */
    public int getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the sensor
     */
    public String getSensor() {
        return sensor;
    }

    /**
     * @param sensor the sensor to set
     */
    public void setSensor(String sensor) {
        this.sensor = sensor;
    }
}
