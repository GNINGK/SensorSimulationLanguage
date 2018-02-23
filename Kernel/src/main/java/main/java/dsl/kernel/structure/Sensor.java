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
    private int echantillonnage;
    private T lastValue;
    private float noise;

    public Sensor(String name, Behavior law, int echantillonnage) {
        this.noise = 0;
        this.name = name;
        this.law = law;
        if (echantillonnage > 0) {
            this.echantillonnage = echantillonnage;
        } else {
            this.echantillonnage = 1;//par defaut
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

    public T generationDonnees(int relativeTime) {
        if (relativeTime % echantillonnage == 0) {
            T temp = (T) law.createData(relativeTime, this.noise);
            lastValue = temp;
            return temp;
        } else {
            return lastValue;
        }
    }

    /**
     * @return the echantillonnage
     */
    public int getEchantillonnage() {
        return echantillonnage;
    }

    /**
     * @param echantillonnage the echantillonnage to set
     */
    public void setEchantillonnage(int echantillonnage) {
        this.echantillonnage = echantillonnage;
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
