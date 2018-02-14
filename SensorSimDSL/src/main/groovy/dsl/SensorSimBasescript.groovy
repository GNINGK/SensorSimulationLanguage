package main.groovy.dsl

import main.java.dsl.kernel.definition.Functions
import main.java.dsl.kernel.definition.Interval
import main.java.dsl.kernel.definition.IntervalFunctions
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
        closure = { place -> ((SensorSimBinding) this.getBinding()).getSensorSimModel().createPlace(place)}
        [and: closure]
    }

    def law(String name) {
        [function: { type ->
            //if (((String) type).equals("polynome")){
                List<Double> coefficients = new ArrayList<>()
               /* def closure
                closure = { coefficient ->
                    coefficients.add(coefficient)
                    [and: closure]
                }
                [with: closure]
                ((SensorSimBinding)this.getBinding()).getSensorSimModel().createLaw(name, type, ((Double[]) coefficients.toArray()))
*/
            [with: { coeff ->
                coefficients.add(coeff)
                ((SensorSimBinding)this.getBinding()).getSensorSimModel().createLaw(name, type, ((Double[]) coefficients.toArray()))
            }]
           /* } else if (((String) type).equals("ifs")) {
                def closure
                closure = { coefficient ->
                    System.out.print(test2);
                    ((SensorSimBinding) this.getBinding()).getSensorSimModel().createLaw(name, "ifs", new Double[0])
                    [from: { a ->
                        [to: { b ->
                            [follows: { lawName ->
                                ((IntervalFunctions) ((SensorSimBinding) this.getBinding()).getVariable(name)).add(new Interval(a, b), ((Functions) ((SensorSimBinding) this.getBinding()).getVariable(lawName)))
                            }]
                        }]
                    }]
                    [and: closure]
                }
                [considering: closure]
            } else if (((String) type).equals("markov")) {

            }*/
        }]
    }

    // place "name" means actuator becomes signal [and actuator becomes signal]*n
    def sensor(String name) {

        [follows: { lawName ->
            [every: { frequency ->
                ((SensorSimBinding) this.getBinding()).getSensorSimModel().createSensor(name, ((Functions) ((SensorSimBinding) this.getBinding()).getVariable(lawName)), frequency)}
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