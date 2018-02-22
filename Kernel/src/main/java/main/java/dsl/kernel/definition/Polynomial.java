package main.java.dsl.kernel.definition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxime
 */
public class Polynomial extends Functions {

    private List<Double> polynomialCoefficients;
    private double xMin;
    private double xMax;

    public Polynomial(double xMin, double xMax) {
        this.polynomialCoefficients = new ArrayList<>();
        this.xMin = xMin;
        this.xMax = xMax;
    }

    public Polynomial(List<Double> coefficients, double xMin, double xMax) {
        this.polynomialCoefficients = coefficients;
        this.xMin = xMin;
        this.xMax = xMax;
    }

    public void addCoefficient(double coeff){
        polynomialCoefficients.add(coeff);
    }

    public void setMinMax(double min, double max){
        this.xMin = min;
        this.xMax = max;
    }

    @Override
    public Float createData(float relativeTime, float noise) {
        float result = 0;
        for (int i = 0; i < polynomialCoefficients.size(); i++) {
            if (i == 0) {
                result += polynomialCoefficients.get(i);
            } else {
                if (polynomialCoefficients.get(i) != 0) {
                    result += polynomialCoefficients.get(i) * Math.pow(xMin + (relativeTime % (Math.abs(xMin) + Math.abs(xMax))), i);
                }
            }
        }
        
        //result = (float) Math.min(result, 100000.0);
        return result;
    }
}
