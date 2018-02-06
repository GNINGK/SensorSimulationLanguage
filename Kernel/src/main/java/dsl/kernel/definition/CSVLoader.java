/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsl.kernel.definition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maxime
 */
public class CSVLoader extends Comportement {

    List<Tuple> dataSource;
    String pathCSV;
    String nameCapteur;
    int timeMin = -1;
    int timeMax = -1;

    public CSVLoader(String pathCSV, String nameCapteur) {
        dataSource = new ArrayList<>();
        this.pathCSV = pathCSV;
        this.nameCapteur = nameCapteur;
    }

    public CSVLoader(String pathCSV, String nameCapteur, int timeMin, int timeMax) {
        dataSource = new ArrayList<>();
        this.pathCSV = pathCSV;
        this.nameCapteur = nameCapteur;
        this.timeMax = timeMax;
        this.timeMin = timeMin;
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
                String value = tuple[2].replaceAll("\"", "");
                if (sensor.equals(nameCapteur) && timeMin >= 0 && timeMax > 0 && time >= timeMin && time <= timeMax) {
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
    public List<Tuple> createData() {
        if (pathCSV != null) {
            getDataCSV();
        }
        return dataSource;
    }
}
