/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.dsl.kernel;

import main.java.dsl.kernel.definition.Tuple;
import main.java.dsl.kernel.structure.Capteur;
import main.java.dsl.kernel.structure.Lieu;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Maxime
 */
public class Simulation implements NamedElement {

    private static final Logger LOGGER = Logger.getLogger(Simulation.class);

    private String name;
    private List<Lieu> lieux;
    private int tempsTotalSimulation;

    Simulation(int tempsTotalSimulation) {
        BasicConfigurator.configure();

        this.tempsTotalSimulation = tempsTotalSimulation;
        lieux = new ArrayList<>();
    }

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

    public Lieu getLieuByName(String name) {
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
            List<Tuple> resultat = new ArrayList<>();
            LOGGER.info("Lieu : " + l.getName());
            for (Capteur c : listCapteur) {
                LOGGER.info("capteur : " + c.getName());

                for (int i = 0; i < tempsTotalSimulation; i++) {
                    if (i % c.getEchantillonnage() == 0) {
                        LOGGER.info(c.generationDonnees(i));
                        resultat.add(new Tuple(i, c.getName(), c.generationDonnees(i)));
                    }
                }
            }
            sauvegardeCSV(resultat, "resultat.csv");
        }
    }

    private void sauvegardeCSV(List<Tuple> resultat, String pathOutput) {
        if (pathOutput.isEmpty()) {
            LOGGER.error("Attention : Il n'y a pas de chemin pour le fichier");
            return;
        }

        File file = new File(pathOutput);

        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    LOGGER.error("Erreur : Il n'y a pas de chemin pour le fichier");
                }
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }

        try (FileWriter writer = new FileWriter(file)) {
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
        } catch (IOException e) {
            LOGGER.error("Erreur: impossible de créer le fichier '" + pathOutput + "'");
        }
    }

    /**
     * @return the tempsTotalSimulation
     */
    public int getTempsTotalSimulation() {
        return tempsTotalSimulation;
    }
}
