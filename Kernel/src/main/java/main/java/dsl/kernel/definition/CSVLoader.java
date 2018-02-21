package main.java.dsl.kernel.definition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class CSVLoader extends Behavior<Float> {

    List<Tuple> dataSource;
    String pathCSV;
    String sensorName;
    long timeMin = -1;
    long timeMax = -1;
    float lastResult = 0;

    public CSVLoader(String pathCSV, String sensorName) {
        dataSource = new ArrayList<>();
        this.pathCSV = pathCSV;
        this.sensorName = sensorName;
        if (pathCSV != null) {
            getDataCSV();
        }
    }

    public CSVLoader(String pathCSV, String sensorName, int timeMin, int timeMax) {
        dataSource = new ArrayList<>();
        this.pathCSV = pathCSV;
        this.sensorName = sensorName;
        this.timeMax = timeMax;
        this.timeMin = timeMin;
        if (pathCSV != null) {
            getDataCSV();
            if (dataSource.isEmpty()) {
                throw new IllegalArgumentException("");
            }
        }

    }

    public void getDataCSV() {
        BufferedReader buffer = null;
        String[] tuple = null;
        try {
            String filePath = new File("").getAbsolutePath();
            buffer = new BufferedReader(new FileReader(filePath + "/resources/" + pathCSV));

            String line;
            while ((line = buffer.readLine()) != null) {
                tuple = line.split(",");
                int time = Integer.parseInt(tuple[0]);
                String sensor = tuple[1];
                float value = Float.parseFloat(tuple[2]);
                if (sensor.equals(sensorName) && timeMin >= 0 && timeMax > 0 && time >= timeMin && time <= timeMax) {
                    Tuple<Float> t = new Tuple<>(time, sensor, value);
                    dataSource.add(t);
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                if (buffer != null) {
                    buffer.close();
                }
            } catch (IOException exception) {
            }
        }
    }

    /**
     *
     * @param relativeTime Valeur du temps a chercher dans le CSV
     * @param noise Borne maximale a ajouter/soustraire du resultatpour créer un
     * bruit.
     * @return Si le CSV contient la valeur pour le temps et le sensor demandé
     * alors retourne la valeur. Si le temps indiqué est entre 2 temps dans le
     * csv pour ce sensor alors retourne la moyenne entre le temps inférieure et
     * le temps supérieure au temps demandé. Si le temps demandé est inférieur a
     * tous les temps dans le csv alors retourne Nan.
     */
    @Override
    public Float createData(float relativeTime, float noise) {
        float result = 0;
        boolean valeurTrouve = false;
        for (Tuple d : dataSource) {
            if (d.getTime() == relativeTime) {
                result = (float) d.getValue();
                valeurTrouve = true;
            }
        }
        //Si le point n'est pas dans la liste calcul la valeur moyenne entre la valeur precedente et la prochaine valeur
        if (valeurTrouve == false) {
            Tuple<Float> min = null, max = null;
            float valueBefore = Float.MAX_VALUE, valueAfter = Float.MAX_VALUE;
            for (Tuple d : dataSource) {
                if (valueBefore > relativeTime - d.getTime() && relativeTime - d.getTime() > 0) {
                    valueBefore = relativeTime - d.getTime();
                    min = d;
                } else if (valueAfter > d.getTime() - relativeTime && d.getTime() - relativeTime > 0) {
                    valueAfter = d.getTime() - relativeTime;
                    max = d;
                }
            }

            if (min == null) {
                return Float.NaN;
            } else if (max == null) {
                return lastResult;
            }

            result = (min.getValue() + max.getValue()) / 2;
        }

        float rand = generateNoise(noise);
        lastResult = result + rand;
        return lastResult;
    }
}
