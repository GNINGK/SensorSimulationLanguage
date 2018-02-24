package main.java.dsl.kernel.definition;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author user
 */
public class FileLoader extends Behavior<Float> {

    private List<Tuple> dataSource;
    private String filePath;
    private String sensorName;
    private long minTime = -1;
    private long maxTime = -1;
    private float lastResult = 0;

    public FileLoader() {
        dataSource = new ArrayList<>();
    }

    public FileLoader(String filePath, String sensorName) {
        dataSource = new ArrayList<>();
        this.filePath = filePath;
        this.sensorName = sensorName;
        if (filePath != null) {
            getFile();
        }
    }

    public FileLoader(String filePath, String sensorName, int minTime, int maxTime) {
        dataSource = new ArrayList<>();
        this.filePath = filePath;
        this.sensorName = sensorName;
        this.maxTime = maxTime;
        this.minTime = minTime;
        if (filePath != null) {
            getFile();
        }
    }

    public void setPath(String filePath){
        this.filePath = filePath;
    }

    public void setSensorName(String sensorName){
        this.sensorName = sensorName;
    }

    public void setTimeMinMax(long min, long max){
        this.minTime = min;
        this.maxTime = max;
    }

    private void getFile() {
        Pattern csv = Pattern.compile(".*.csv");
        Pattern json = Pattern.compile(".*.json");
        Matcher mcsv = csv.matcher(filePath);
        Matcher mjson = json.matcher(filePath);
        if (mcsv.find()) {
            getDataCSV();
        } else if (mjson.find()) {
            getdataJSON();
        } else {
            throw new IllegalArgumentException("File: " + filePath + " not found.");
        }

        if (getDataSource().isEmpty()) {
            throw new IllegalArgumentException("Could not found value for sensor: " + sensorName);
        }
    }

    private void getdataJSON() {

        File file = new File(filePath);
        if (file.isFile()) {
            JSONArray jsonArray = new JSONArray(loadFile(file));
            JSONObject jsonObject = null;
            for (int j = 0; j < jsonArray.length(); j++) {
                jsonObject = jsonArray.getJSONObject(j);
                String sensor = jsonObject.getString("sensorName");
                if (sensor.equals(sensorName))
                {
                    Tuple<Float> t = new Tuple<>(jsonObject.getInt("time"), sensor, jsonObject.getString("placeName"), (float)jsonObject.getDouble("value"));
                    getDataSource().add(t);
                }
            }
        }
    }

    public static String loadFile(File f) {
        BufferedInputStream in = null;
        StringWriter out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            out = new StringWriter();
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            out.flush();
            out.close();
            in.close();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return out.toString();
    }

    private void getDataCSV() {
        BufferedReader buffer = null;
        String[] tuple = null;
        try {
            String filePath = new File("").getAbsolutePath();
            buffer = new BufferedReader(new FileReader(this.filePath));

            String line;
            while ((line = buffer.readLine()) != null) {
                tuple = line.split(",");
                int time = Integer.parseInt(tuple[0]);
                String sensor = tuple[1];
                String placeName = tuple[2];
                float value = Float.parseFloat(tuple[3]);
                if (sensor.equals(sensorName) && minTime >= 0 && maxTime > 0 && time >= minTime && time <= maxTime) {
                    Tuple<Float> t = new Tuple<>(time, sensor, placeName, value);
                    getDataSource().add(t);
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
     * @param relativeTime Time value to look for in CSV data source.
     * @param noise  Pseudo random value to add/substract to created data to simulate noise.
     * @return If CSV data source contains value for the asked sensor with at the exact same time
     * then return value.
     * If time specified is between two times with known value then return mean of those values.
     * If time range below all known time return NaN
     */
    @Override
    public Float createData(float relativeTime, float noise) {
        float result = 0;
        boolean isValueFound = false;
        for (Tuple d : getDataSource()) {
            if (d.getTime() == relativeTime) {
                result = (float) d.getValue();
                isValueFound = true;
            }
        }
        //Si le point n'est pas dans la liste calcul la valeur moyenne entre la valeur precedente et la prochaine valeur
        if (!isValueFound) {
            Tuple<Float> min = null, max = null;
            float valueBefore = Float.MAX_VALUE, valueAfter = Float.MAX_VALUE;
            for (Tuple d : getDataSource()) {
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

    /**
     * @return the dataSource
     */
    public List<Tuple> getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(List<Tuple> dataSource) {
        this.dataSource = dataSource;
    }
}
