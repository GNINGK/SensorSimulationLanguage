package main.java.dsl.kernel.definition;

/**
 * @author Maxime
 */
public class Polynomial extends Functions {

    private Double[] polynome;
    private double xMax;

    public Polynomial(Double[] polynome, double xMax) {
        this.polynome = polynome;
        this.xMax = xMax;
    }

    @Override
    public float createData(float relativeTime, float noise) {
        float result = 0;
        for (int i = 0; i < polynome.length; i++) {
            if (i == 0) {
                result += polynome[i];
            } else {
                if (polynome[i] == 0) {
                    result = (float) Math.pow(relativeTime % xMax, i);
                } else {
                    result += polynome[i] * Math.pow(relativeTime % xMax, i);
                }
            }
        }
        return result;
    }
}
