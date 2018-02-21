package main.java.dsl.kernel.definition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxime
 */
public class Polynomial extends Functions {

    private List<Double> polynomialCoefficients;
    private double xMax;

    public Polynomial(double xMax) {
        this.polynomialCoefficients = new ArrayList<>();
        this.xMax = xMax;
    }

    public Polynomial(List<Double> polynomialCoefficients, double xMax) {
        this.polynomialCoefficients = polynomialCoefficients;
        this.xMax = xMax;
    }

    public void addCoefficient(double coeff){
        polynomialCoefficients.add(coeff);
    }

    @Override
    public Float createData(float relativeTime, float noise) {
        float result = 0;
        for (int i = 0; i < polynomialCoefficients.size(); i++) {
            if (i == 0) {
                result += polynomialCoefficients.get(i);
            } else {
                if (polynomialCoefficients.get(i) == 0) {
                    result = (float) Math.pow(relativeTime % xMax, i);
                } else {
                    result += polynomialCoefficients.get(i) * Math.pow(relativeTime % xMax, i);
                }
            }
        }
        result = (float)Math.min(result,100000.0);
        return result;
    }
}
