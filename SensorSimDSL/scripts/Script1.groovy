simulation "simulation1" lasting 30

instantiate "buildingA" and "buildingB"

law "function1" function "Polynomial" between 0 and 20 with 0 and 2 and -1/10
law "function2" function "Polynomial" between 0 and 10 with 0 and 2 and -1/5
law "function3" function "Polynomial" between -10 and 10 with 10 and 0 and -1/10
law "function_markov" function "markov" frequency 5 with "Sunny" parameters ([0.1,0.9]) and "Rainy" parameters ([0.5,0.5])
law "function_csv" function "csv" from "../resources/result.csv" with "sensor0" between 0 and 10
law "function_json" function "json" from "../resources/result.json" with "sensor1" between 0 and 10

law "function_interval" function "Interval" follows function1 from 0 to 40 and "function2" from 41 to 60 and function3 from 61 to 100

sensor "sensor1" follows function every 1
sensor "sensor2" follows "function2" every 1
sensor "sensor3" follows "function3" every 1
sensor "sensor_csv" follows "function_csv" every 1
sensor "sensor_json" follows "function_json" every 1
sensor "sensor_markov" follows "function_markov" every 1
sensor "sensor_interval" follows "function_interval" every 1

add 1 sensors sensor1 to buildingA
add 1 sensors "sensor2" to "buildingA"
add 1 sensors "sensor3" to "buildingA"
add 1 sensors "sensor_markov" to "buildingA"
add 1 sensors "sensor_interval" to "buildingA"

add 1 sensors "sensor_csv" to "buildingB"
add 1 sensors "sensor_json" to "buildingB"

law "function_aggregate" function "Aggregate"
aggregate "buildingB" with "function_aggregate"

launch "simulation1"