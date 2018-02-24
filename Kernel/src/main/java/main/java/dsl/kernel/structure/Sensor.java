package main.java.dsl.kernel.structure;

import main.java.dsl.kernel.NamedElement;
import main.java.dsl.kernel.definition.Behavior;

/**
 * Cette classe représente un ensemble (1 a n) de capteurs qui utiliseront la
 * même law.
 *
 * @author Maxime
 */
public class Sensor<T> implements NamedElement {

    private String name;
    private Behavior law;
    private int sampling;
    private T lastValue;
    private float noise;

    public Sensor(String name, Behavior law, int sampling) {
        this.noise = 0;
        this.name = name;
        this.law = law;
        if (sampling > 0) {
            this.sampling = sampling;
        } else {
            this.sampling = 1;
        }
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
     * @return the law
     */
    public Behavior getLaw() {
        return law;
    }

    /**
     * @param law the law to set
     */
    public void setLaw(Behavior law) {
        this.law = law;
    }

    public T generateData(int relativeTime) {
        if (relativeTime % sampling == 0) {
            T newData = (T) law.createData(relativeTime, this.noise);
            lastValue = newData;
            return newData;
        } else {
            return lastValue;
        }
    }

    /**
     * @return the sampling
     */
    public int getSampling() {
        return sampling;
    }

    /**
     * @param sampling the sampling to set
     */
    public void setSampling(int sampling) {
        this.sampling = sampling;
    }

    /**
     * @return the noise
     */
    public float getNoise() {
        return noise;
    }

    /**
     * @param noise the noise to set
     */
    public void setNoise(float noise) {
        this.noise = noise;
    }

}
