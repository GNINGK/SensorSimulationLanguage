simulation "simulation1" lasting 8

instantiate "buildingA" and "buildingB"

law "polLaw" function "polynome" coefficients "3"

sensor "tempSensor" follows "polLaw" every 2

add 5 sensors "tempSensor" to "buildingB"

launch "simulation1"