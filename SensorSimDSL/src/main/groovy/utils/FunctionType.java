package main.groovy.utils;

public enum FunctionType {
    AGGREGATE("Aggregate"),
    CSV("Csv loading"),
    INTERVAL("Interval"),
    JSON("Json loading"),
    MARKOV("Markov"),
    POLYNOMIAL("Polynomial"),
    UNKNOWN("Unknown");

    private String name;

    // Constructor
    FunctionType(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
