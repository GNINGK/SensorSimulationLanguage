package main.groovy.dsl;

import java.util.Map;

import groovy.lang.Binding;
import groovy.lang.Script;

public class SensorSimBinding extends Binding {
    private Script script;

    private SensorSimModel model;

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
        return super.getVariable(name);
    }

    public void setVariable(String name, Object value) {
        super.setVariable(name, value);
    }

    public SensorSimModel getSensorSimModel() {
        return this.model;
    }
}
