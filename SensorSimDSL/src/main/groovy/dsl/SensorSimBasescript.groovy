package main.groovy.dsl

import main.java.dsl.kernel.definition.Behavior
import main.java.dsl.kernel.definition.FileLoader
import main.java.dsl.kernel.definition.Functions
import main.java.dsl.kernel.definition.Interval
import main.java.dsl.kernel.definition.IntervalFunctions
import main.java.dsl.kernel.definition.Markov
import main.java.dsl.kernel.definition.Polynomial
import main.java.dsl.kernel.structure.Sensor

abstract class SensorSimBasescript extends Script  {

    // simulation "name"
    def simulation(String name) {
        [lasting: { n -> ((SensorSimBinding)this.getBinding()).getSensorSimModel().createSimulation(name, n) }]
    }

    // instantiate "name" means we create a place name "name" [and one more place.]*n
    def instantiate(String name) {
        ((SensorSimBinding) this.getBinding()).getSensorSimModel().createPlace(name)
        def closure
        closure = { place ->
            ((SensorSimBinding) this.getBinding()).getSensorSimModel().createPlace((String) place)
        }
        [and: closure]
    }

    def law(String name) {
        [function: { type ->
            if(((String) type).equals("polynome")) {
                [between: { a->
                    [and: { b ->
                        ((SensorSimBinding) this.getBinding()).getSensorSimModel().createLaw((String) name, (String) type)
                        ((Polynomial) ((SensorSimBinding) this.getBinding()).getVariable(name)).setMinMax((double) a, (double) b);
                        def closure
                        closure = { coefficient ->
                            //System.out.print("test: " + (double) coefficient)
                            ((Polynomial) ((SensorSimBinding) this.getBinding()).getVariable(name)).addCoefficient((double) coefficient);
                            [and: closure]
                        }
                        [with: closure]
                    }]
                }]
            } else if (((String) type).equals("interval")) {
                ((SensorSimBinding) this.getBinding()).getSensorSimModel().createLaw((String) name, (String) type)
                def closure
                closure = { lawName ->
                    [from: { a ->
                        [to: { b ->
                            ((IntervalFunctions) ((SensorSimBinding) this.getBinding()).getVariable(name)).add(new Interval(a, b), ((Functions) ((SensorSimBinding) this.getBinding()).getVariable(lawName)))
                            [and: closure]
                        }]
                    }]
                }
                [follows: closure]
            } else if (((String) type).equals("markov")) {
                ((SensorSimBinding) this.getBinding()).getSensorSimModel().createLaw((String) name, (String) type)
                [frequency: { freq ->
                    ((Markov) ((SensorSimBinding) this.getBinding()).getVariable(name)).setFrequency((int) freq)
                    def closure
                    closure = { state ->
                        def closure1
                        closure1 = { matrix ->
                            ((Markov) ((SensorSimBinding) this.getBinding()).getVariable(name)).addState((String) state, (float[]) matrix)
                            [and: closure]
                        }
                        [parameters: closure1]
                    }
                    [with: closure]
                }]
            } else if (((String) type).equals("csv")) {
            ////from "dataSource.csv" with "sensor0" between 0 and 10
                ((SensorSimBinding) this.getBinding()).getSensorSimModel().createLaw((String) name, (String) type)
                [from: { source ->
                    [with: { sensorName ->
                        ((FileLoader) ((SensorSimBinding) this.getBinding()).getVariable(name)).addPath((String) source)
                        ((FileLoader) ((SensorSimBinding) this.getBinding()).getVariable(name)).setSensorName((String) sensorName)
                        [between: { a ->
                            [and: { b ->
                                ((FileLoader) ((SensorSimBinding) this.getBinding()).getVariable(name)).setTimeMinMax((long) a, (long) b)
                            }]
                        }]
                    }]
                }]
            }

        }]
    }

    // place "name" means actuator becomes signal [and actuator becomes signal]*n
    def sensor(String name) {
        [follows: { lawName ->
            [every: { frequency ->
                ((SensorSimBinding) this.getBinding()).getSensorSimModel().createSensor(name, ((Behavior) ((SensorSimBinding) this.getBinding()).getVariable(lawName)), frequency)}
            ]
        }]
    }
        // place "name" means actuator becomes signal [and actuator becomes signal]*n
    def add(int nbSensors) {
        [sensors: { name ->
            [to: { placeName ->
                ((SensorSimBinding) this.getBinding()).getSensorSimModel().addSensors(placeName, ((Sensor) ((SensorSimBinding) this.getBinding()).getVariable(name)), nbSensors)
            }]
        }]
    }

    def launch(String simulationName) {
        ((SensorSimBinding) this.getBinding()).getSensorSimModel().runSimulation()
    }

    // disable run method while running
    int count = 0
    abstract void scriptBody()
    def run() {
        if(count == 0) {
            count++
            scriptBody()
        } else {
            println "Run method is disabled"
        }
    }
}