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
public class Polynomial extends Functions{
    
        private double[] polynome;
    public Polynomial(double[] polynome) {
        this.polynome = polynome;
    }
    
        @Override
    public String createData(int instantT) {
        double result = 0;
        for (int i = 0; i < polynome.length; i++) {
            if (i == 0) {
                result += polynome[i];
            } else {
                result += polynome[i] * Math.pow(instantT, i - 1);
            }

        }
        return "" + result;
    }
    
}
