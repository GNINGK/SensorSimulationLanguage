package main.java.dsl.kernel.structure;

import main.java.dsl.kernel.NamedElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxime
 */
public class Lieu implements NamedElement {

    private String name;
    private List<Capteur> capteurs;

    public Lieu() {
        capteurs = new ArrayList<>();
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
     * @return La liste des capteurs
     */
    public List<Capteur> getCapteurs() {
        return capteurs;
    }

    public Capteur getCapteurByName(String name) {
        Capteur result = null;
        for (Capteur c : capteurs) {
            if (c.getName().equals(name)) {
                result = c;
            }
        }
        return result;
    }

    public void addCapteur(Capteur cap) {
        capteurs.add(cap);
    }

    public void addCapteur(List<Capteur> cap) {
        capteurs.addAll(cap);
    }

}
