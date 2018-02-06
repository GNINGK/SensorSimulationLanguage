/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsl.kernel;

import dsl.kernel.definition.Tuple;
import dsl.kernel.structure.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Maxime
 */
public class Simulation implements NamedElement {

    private String name;
    private List<Lieu> lieux;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return the lieux
     */
    public List<Lieu> getLieux() {
        return lieux;
    }

    public Lieu getLieu(String name) {
        Lieu result = null;
        for (Lieu l : lieux) {
            if (l.getName().equals(name)) {
                result = l;
            }
        }
        return result;
    }

    /**
     * @param lieux the lieux to set
     */
    public void setLieux(Lieu lieux) {
        this.lieux.add(lieux);
    }

    public void run() {
        for (Lieu l : lieux) {
            List<Capteur> listCapteur = l.getCapteurs();
            List<Tuple> resultat = null;
            for (Capteur c : listCapteur) {

            }
            sauvegardeCSV(resultat, "");
        }
    }

    private void sauvegardeCSV(List<Tuple> resultat, String pathOutput) {
        File file = null;
        if (pathOutput.isEmpty()) {
            System.out.println("Erreur : Il n'y a pas de chemin pour le fichier");
        } else {
            file = new File(pathOutput);
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < resultat.size(); i++) {
                if (i == resultat.size() - 1) {
                    String res = resultat.get(i).toString();
                    writer.write(res);
                } else {
                    String res = resultat.get(i).toString() + "\n";
                    writer.write(res);
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Erreur: impossible de créer le fichier '" + pathOutput + "'");
        }
    }
}
