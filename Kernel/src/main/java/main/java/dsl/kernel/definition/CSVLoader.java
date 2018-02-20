package main.java.dsl.kernel.definition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxime
 */
public class CSVLoader extends Behavior {

    List<Tuple> dataSource;
    String pathCSV;
    String sensorName;
    long timeMin = -1;
    long timeMax = -1;

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
        }

    }

    public void getDataCSV() {
        BufferedReader buffer = null;
        String[] tuple = null;
        try {
            String line;
            buffer = new BufferedReader(new FileReader(pathCSV));

            while ((line = buffer.readLine()) != null) {
                //Format (time,sensor,value)
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
        } finally {
            try {
                if (buffer != null) {
                    buffer.close();
                }
            } catch (IOException exception) {
            }
        }
    }

    @Override
    public float createData(float relativeTime, float noise) {
        float result = 0;
        if (!dataSource.isEmpty()) {
            boolean valeurTrouve = false;
            for (Tuple d : dataSource) {
                if (d.getTime() == relativeTime) {
                    result = (float)d.getValue();
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
                result = (min.getValue() + max.getValue()) / 2;
            }
        }
        float rand = generateNoise(noise);
        System.out.println("Noise = " + rand);
        return result + rand;
    }
}
