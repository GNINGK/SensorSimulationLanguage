package main.java.dsl.kernel.structure;

import main.java.dsl.kernel.NamedElement;
import main.java.dsl.kernel.definition.Behavior;

/**
 * Cette classe représente un ensemble (1 a n) de capteurs qui utiliseront la
 * même law.
 *
 * @author Maxime
 */
public class Sensor implements NamedElement {

    private String name;
    private Behavior law;
    private int echantillonnage;
    private String lastValue = null;

    public Sensor(String name, Behavior law, int echantillonnage) {
        this.name = name;
        this.law = law;
        this.echantillonnage = echantillonnage;
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

    public String generationDonnees(int instantT) {
        if (instantT % echantillonnage == 0) {
            String temp = law.createData(instantT);
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

}
