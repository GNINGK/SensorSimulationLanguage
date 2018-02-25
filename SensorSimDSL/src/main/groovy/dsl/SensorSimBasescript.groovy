package main.groovy.dsl

import main.groovy.utils.FunctionType
import main.java.dsl.kernel.definition.*
import main.java.dsl.kernel.structure.Place
import main.java.dsl.kernel.structure.Sensor
import org.apache.log4j.Logger

abstract class SensorSimBaseScript extends Script  {

    private static Logger logger = Logger.getLogger(SensorSimBaseScript.class)

    // simulation "name" lasting time
    def simulation(String name) {
        [lasting: { time ->
            int  duration = time
            ((SensorSimBinding) this.getBinding()).getSensorSimModel().createSimulation(name, duration) }]
    }

    // instantiate "name" means we create a place named "name" [and one more place named "place]*n
    def instantiate(String name) {
        ((SensorSimBinding) this.getBinding()).getSensorSimModel().createPlace(name)
        def closure
        closure = { place ->
            String placeValue = place
            ((SensorSimBinding) this.getBinding()).getSensorSimModel().createPlace(placeValue)
        }
        [and: closure]
    }

    // create a law named "name" of type "type" other options depend of the type of law
    def law(String name) {
        [function: { type ->
            FunctionType functionType
            try {
                functionType = FunctionType.valueOf(((String) type).toUpperCase())
            } catch (IllegalArgumentException iae) {
                functionType = FunctionType.UNKNOWN
            }

            ((SensorSimBinding) this.getBinding()).getSensorSimModel().createLaw(name, functionType)

            if (functionType == FunctionType.POLYNOMIAL) {
                [between: { a ->
                    [and: { b ->
                        double minTime = a
                        double maxTime = b
                        ((Polynomial) ((SensorSimBinding) this.getBinding()).getVariable(name)).setMinMax(minTime, maxTime)
                        def closure
                        closure = { coefficient ->
                            double coefficientValue = coefficient
                            ((Polynomial) ((SensorSimBinding) this.getBinding()).getVariable(name)).addCoefficient(coefficientValue)
                            [and: closure]
                        }
                        [with: closure]
                    }]
                }]
            } else if (functionType == FunctionType.INTERVAL) {
                def closure
                closure = { lawName ->
                    [from: { a ->
                        [to: { b ->
                            double minTime = a
                            double maxTime = b
                            Interval interval = new Interval(minTime, maxTime)
                            Functions function = (lawName instanceof String) ?
                                    ((Functions) ((SensorSimBinding) this.getBinding()).getVariable(lawName)) : (Functions) lawName
                            ((IntervalFunctions) ((SensorSimBinding) this.getBinding()).getVariable(name)).add(interval, function)
                            [and: closure]
                        }]
                    }]
                }
                [follows: closure]
            } else if (functionType == FunctionType.MARKOV) {
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
            } else if (functionType == FunctionType.CSV || functionType == FunctionType.JSON) {
                ////from "dataSource.csv" with "sensor0" between 0 and 10
                [from: { source ->
                    [with: { sensorName ->
                        String sourceValue = source
                        String sensorNameValue = sensorName
                        ((FileLoader) ((SensorSimBinding) this.getBinding()).getVariable(name)).setPath(sourceValue)
                        ((FileLoader) ((SensorSimBinding) this.getBinding()).getVariable(name)).setSensorName(sensorNameValue)
                        [between: { a ->
                            [and: { b ->
                                int minTime = a
                                int maxTime = b
                                ((FileLoader) ((SensorSimBinding) this.getBinding()).getVariable(name)).setTimeMinMax(minTime, maxTime)
                            }]
                        }]
                    }]
                }]
            }

        }]
    }

    // Create a sensor named "name" following law "lawName" at frequency "frequency"
    def sensor(String name) {
        [follows: { lawName ->
            [every: { frequency ->
                int frequencyValue = frequency
                Behavior law = (lawName instanceof String) ?
                        ((Behavior) ((SensorSimBinding) this.getBinding()).getVariable((String) lawName)) : (Behavior) lawName
                ((SensorSimBinding) this.getBinding()).getSensorSimModel().createSensor(name, law, frequencyValue)
            }]
        }]
    }

    // Add "nbSensors" of type "type" to place "placeName"
    def add(int nbSensors) {
        [sensors: { sensorType ->
            [to: { placeName ->
                Place place = (placeName instanceof String)?
                        ((Place) ((SensorSimBinding) this.getBinding()).getVariable(placeName)) : (Place) placeName
                Sensor sensor = (sensorType instanceof String)?
                        ((Sensor) ((SensorSimBinding) this.getBinding()).getVariable(sensorType)) : (Sensor) sensorType
                ((SensorSimBinding) this.getBinding()).getSensorSimModel().addSensors(place, sensor, nbSensors)
            }]
        }]
    }

    // Run simulation
    def launch(String simulationName) {
        ((SensorSimBinding)this.getBinding()).getSensorSimModel().runSimulation()
    }

    // disable run method while running
    int count = 0
    abstract void scriptBody()
    def run() {
        if(count == 0) {
            count++
            try{
                scriptBody()
            } catch (Exception e){
                logger.error("Simulation stop !")
            }
        } else {
            println "Run method is disabled"
        }
    }
}