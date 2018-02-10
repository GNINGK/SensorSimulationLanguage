/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.dsl.kernel.definition;

/**
 *
 * @author Maxime
 */
public class Interval {

    private final double min;
    private final double max;

    public Interval(double min, double max) {
        if (min <= max) {
            this.min = min;
            this.max = max;
        } else {
            throw new IllegalArgumentException("La borne max doit être >= a la borne min");
        }
    }

}
