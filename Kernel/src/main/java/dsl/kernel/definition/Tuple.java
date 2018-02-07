package main.java.dsl.kernel.definition;

/**
 * @author Maxime
 */
public class Tuple {

    private long time;
    private float value;
    private String sensor;

    public Tuple(long time, String sensor, float value) {
        this.time = time;
        this.value = value;
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return time + "," + sensor + "," + value;
    }

    /**
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * @return the value
     */
    public float getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(float value) {
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
