# DSL Team H

# Sensor Simulation Language

### Team composition
   * Dejeux Maxime
   * Giroud Anthonny
   * Gning Khadim

### RUN

    docker-compose up -d
    
    cd Kernel
    mvn install

    cd SensorSimDSL
    mvn clean compile assembly:single
    java -jar target/sensorsimdsl-1.0-jar-with-dependencies.jar scripts/Script1.groovy


### Grafana

#### Login
- User: admin
- Password: admin

#### Add data source
- Name: sensors_database
- Type: InfluxDB
- URL: http://localhost:8086
- Access: direct
- Database: sensors_database

#### New dashboard (graph)
Click on Panel Title > edit

In Metrics tab, click on 'select measurements' to select a sensor to display.

In Display tab, check Points in Draw Modes.

In the top-right corner, click on Last 6 hours and select Last 5 minutes. You can also zoom out and select a period directly on the graph.


### Influxdb

To inspect database:

    docker exec -it dsl-influxdb influx -database sensors_database

Samples influx commands:

    show measurements
    insert sensor0 value=0.5
    select * from sensor0