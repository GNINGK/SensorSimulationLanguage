simulation "simulation1" lasting 8

instantiate "buildingA" and "buildingB"

law "function1" function "polynome" with 0 2 -1/10 between 0 10
law "function2" function "polynome" with 0 2 -1/5 between 0 20
law "function3" function "polynome" with 10 0 -1/10 between -10 10

law "function_interval" function "interval" with "function1" between 0 40 and "function2" between 41 60 and "function3" between 61 100
law "function_csv" function "csv" from "dataSource.csv" with "sensor0" between 0 10
law "function_markov" function "markov" freq 5 with "Sunny" 0.1 0.9 and "Rainy" 0.5 0.5

sensor "sensor1" follows "function1" every 1
sensor "sensor2" follows "function2" every 1
sensor "sensor_interval" follows "function_interval" every 1
sensor "sensor_csv" follows "function_csv" every 1
sensor "sensor_markov" follows "function_markov" every 1

add 1 sensors "sensor1" to "buildingA"
add 1 sensors "sensor2" to "buildingA"

add 1 sensors "sensor_interval" to "buildingB"
add 1 sensors "sensor_csv" to "buildingB"
add 1 sensors "sensor_markov" to "buildingB"

launch "simulation1"