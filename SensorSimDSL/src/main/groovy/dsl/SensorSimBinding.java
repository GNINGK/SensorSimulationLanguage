package main.groovy.dsl;

import java.util.Map;

import groovy.lang.Binding;
import groovy.lang.Script;
import org.apache.log4j.Logger;

public class SensorSimBinding extends Binding {
    private Script script;

    private SensorSimModel model;
    private static Logger logger = Logger.getLogger(SensorSimModel.class);

    public SensorSimBinding() {
        super();
    }

    @SuppressWarnings("rawtypes")
    public SensorSimBinding(Map variables) {
        super(variables);
    }

    public SensorSimBinding(Script script) {
        super();
        this.script = script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public void setSensorSimModel(SensorSimModel model) {
        this.model = model;
    }

    public Object getVariable(String name) {
        try {
            return super.getVariable(name);
        } catch (Exception e){
            logger.warn("Variable: " + name + " not found !");
            return null;
        }
    }

    public void setVariable(String name, Object value) {
        super.setVariable(name, value);
    }

    public SensorSimModel getSensorSimModel() {
        return this.model;
    }
}
