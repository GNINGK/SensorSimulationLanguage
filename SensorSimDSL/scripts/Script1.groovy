simulation "simulation1" lasting 8

instantiate "buildingA" and "buildingB"
instantiate "buildingC"

law "polLaw" function "polynome" with 1 //and 2 and 3

sensor "tempSensor" follows "polLaw" every 2

add 5 sensors "tempSensor" to "buildingA"

launch "simulation1"