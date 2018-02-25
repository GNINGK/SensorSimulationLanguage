package main.java.dsl.kernel;

import main.java.dsl.kernel.definition.Tuple;
import main.java.dsl.kernel.structure.Place;
import main.java.dsl.kernel.structure.Sensor;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Maxime
 */
public class Simulation implements NamedElement {

    private static final Logger LOGGER = Logger.getLogger(Simulation.class);

    private InfluxDB influxDB;

    private String name;
    private List<Place> places;
    private int totalTime;

    public Simulation() {
        BasicConfigurator.configure();
        initDB("http://localhost:8086", "sensors_database");

        this.places = new ArrayList<>();
        this.totalTime = 30;
    }

    public Simulation(int totalTime) {
        BasicConfigurator.configure();
        initDB("http://localhost:8086", "sensors_database");
        this.places = new ArrayList<>();

        this.totalTime = totalTime;
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
     * @return the places
     */
    public List<Place> getPlaces() {
        return places;
    }

    public Place getPlaceByName(String name) {
        Place result = null;
        for (Place l : places) {
            if (l.getName().equals(name)) {
                result = l;
            }
        }
        return result;
    }

    /**
     * @param places the places to set
     */
    public void addPlaces(Place places) {
        this.places.add(places);
    }

    /**
     * @return totalTime
     */
    public int getTotalTime() {
        return this.totalTime;
    }

    /**
     * @param totalTime total time of the simulation
     */
    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public void run() {
        for (Place l : this.places) {
            if (l.getTotalTime() != this.totalTime) {
                l.setTotalTime(totalTime);
            }
            List<Tuple> result = l.getDatasSensor();
            for (Tuple t : result) {
                System.out.println("Sensor:" + t.getSensor() + "; time:" + t.getTime() + "; value:" + t.getValue());
                //saveToDB(new Tuple(t, sensor.getName(), sensor.generationDonnees(i)));
            }
        }
    }

    private void saveToCSV(List<Tuple> results, String outputPath) {
        if (outputPath.isEmpty()) {
            LOGGER.error("Attention : Il n'y a pas de chemin pour le fichier");
            return;
        }

        File file = new File(outputPath);

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
            for (int i = 0; i < results.size(); i++) {
                if (i == results.size() - 1) {
                    String res = results.get(i).toString();
                    writer.write(res);
                } else {
                    String res = results.get(i).toString() + "\n";
                    writer.write(res);
                }
            }
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("Erreur: impossible de créer le fichier '" + outputPath + "'");
        }
    }

    private void saveToDB(Tuple tuple) {
        Object value = tuple.getValue();
        if (value instanceof String) {
            influxDB.write(Point.measurement(tuple.getPlaceName() + "_infos")
                    .time(tuple.getTime(), TimeUnit.MILLISECONDS)
                    .addField("info", (String) value)
                    .build());
        } else {
            influxDB.write(Point.measurement(tuple.getPlaceName() + "_datas")
                    .time(tuple.getTime(), TimeUnit.MILLISECONDS)
                    .addField("value", (float) value)
                    .build());
        }

    }

    private void initDB(String dbLink, String dbName) {
        this.influxDB = InfluxDBFactory.connect(dbLink);
        Pong response = this.influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            LOGGER.error("Error pinging server.");
            return;
        }
        influxDB.createDatabase(dbName);
        influxDB.setDatabase(dbName);
    }

}
