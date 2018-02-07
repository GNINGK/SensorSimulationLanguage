/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.dsl.kernel.definition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxime
 */
public class CSVLoader extends Comportement {

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
                String sensor = tuple[1].replaceAll("\"", "");
                float value = Float.parseFloat(tuple[2].replaceAll("\"", ""));
                if (sensor.equals(sensorName) && timeMin >= 0 && timeMax > 0 && time >= timeMin && time <= timeMax) {
                    Tuple t = new Tuple(time, sensor, value);
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
    public float createData(int relativeTime) {
        float result = 0;
        if (!dataSource.isEmpty()) {
            for (Tuple d : dataSource) {
                //if(d.)
            }
        }
        return result;
    }
}
