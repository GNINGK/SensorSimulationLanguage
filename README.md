# DSL Team H

# Sensor Simulation Language

### Team composition
   * Dejeux Maxime
   * Giroud Anthonny
   * Gning Khadim

### RUN

    cd Kernel
    mvn install

    cd SensorSimDSL
    mvn clean compile assembly:single
    java -jar target/sensorsimdsl-1.0-jar-with-dependencies.jar scripts/Script1.groovy


### Influxdb

docker exec -it dsl-influxdb influx -database sensors_database

show measurements

insert sensor0 value=0.5

select * from sensor0



