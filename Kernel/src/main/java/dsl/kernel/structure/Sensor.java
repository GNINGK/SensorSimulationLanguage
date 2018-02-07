package main.java.dsl.kernel.structure;

import main.java.dsl.kernel.NamedElement;
import main.java.dsl.kernel.definition.Comportement;

/**
 * Cette classe représente un ensemble (1 a n) de capteurs qui utiliseront la
 * même loi.
 *
 * @author Maxime
 */
public class Sensor implements NamedElement {

    private String name;
    private Comportement loi;
    private int echantillonnage;
    private float lastValue;

    public Sensor(String name, Comportement loi, int echantillonnage) {
        this.name = name;
        this.loi = loi;
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
     * @return the loi
     */
    public Comportement getLoi() {
        return loi;
    }

    /**
     * @param loi the loi to set
     */
    public void setLoi(Comportement loi) {
        this.loi = loi;
    }

    public float generationDonnees(int relativeTime) {
        if (relativeTime % echantillonnage == 0) {
            float temp = loi.createData(relativeTime);
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
