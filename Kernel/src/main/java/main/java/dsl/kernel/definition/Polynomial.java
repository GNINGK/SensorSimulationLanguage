package main.java.dsl.kernel.definition;

/**
 * @author Maxime
 */
public class Polynomial extends Functions {

    private Double[] polynome;
    private double xMin;
    private double xMax;

    public Polynomial(Double[] polynome, double xMin, double xMax) {
        this.polynome = polynome;
        this.xMin = xMin;
        this.xMax = xMax;
    }

    @Override
    public Float createData(float relativeTime, float noise) {
        float result = 0;
        for (int i = 0; i < polynome.length; i++) {
            if (i == 0) {
                result += polynome[i];
            } else {
                if (polynome[i] != 0) {
                    result += polynome[i] * Math.pow(xMin + (relativeTime % (Math.abs(xMin) + Math.abs(xMax))), i);
                }
            }
        }
        result = (float) Math.min(result, 100000.0);
        return result;
    }
}
