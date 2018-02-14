simulation "simulation1" lasting 8

instantiate "buildingA"
instantiate "buildingB"
instantiate "buildingC"

law "polLaw" function "polynome" coefficients "3"

sensor "tempSensor" follows "polLaw" every 2

add 5 sensors "tempSensor" to "buildingA"

launch "simulation1"