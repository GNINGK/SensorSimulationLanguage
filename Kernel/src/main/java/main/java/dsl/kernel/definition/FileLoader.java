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
    String filePath;
    String sensorName;
    long timeMin = -1;
    long timeMax = -1;
    float lastResult = 0;

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

    public FileLoader(String filePath, String sensorName, int timeMin, int timeMax) {
        dataSource = new ArrayList<>();
        this.filePath = filePath;
        this.sensorName = sensorName;
        this.timeMax = timeMax;
        this.timeMin = timeMin;
        if (filePath != null) {
            getFile();
        }
    }

    public void setSensorName(String sensorName){
        this.sensorName = sensorName;
    }

    public void setTimeMinMax(long min, long max){
        this.timeMin = min;
        this.timeMax = max;
    }

    private void getFile() {
        Pattern csv = Pattern.compile(".*.csv"); 
        Pattern json = Pattern.compile(".*.json"); 
        Matcher mcsv = csv.matcher(filePath) ;
        Matcher mjson = json.matcher(filePath) ;
        if (mcsv.find()) {
            getDataCSV();
        } else if (mjson.find()) {
            getdataJSON();
        } else {
            throw new IllegalArgumentException("Le fichier passe en parametre est inconnu.");
        }

        if (getDataSource().isEmpty()) {
            throw new IllegalArgumentException("Il n'y a aucune valeur pour le capteur passe en parametre.");
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
                    Tuple<Float> t = new Tuple<>(jsonObject.getInt("time"), sensor, (float)jsonObject.getDouble("value"));
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
                float value = Float.parseFloat(tuple[2]);
                if (sensor.equals(sensorName) && timeMin >= 0 && timeMax > 0 && time >= timeMin && time <= timeMax) {
                    Tuple<Float> t = new Tuple<>(time, sensor, value);
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
        for (Tuple d : getDataSource()) {
            if (d.getTime() == relativeTime) {
                result = (float) d.getValue();
                valeurTrouve = true;
            }
        }
        //Si le point n'est pas dans la liste calcul la valeur moyenne entre la valeur precedente et la prochaine valeur
        if (valeurTrouve == false) {
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
