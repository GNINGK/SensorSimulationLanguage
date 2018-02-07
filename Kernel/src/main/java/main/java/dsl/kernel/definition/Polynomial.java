package main.java.dsl.kernel.definition;

/**
 *
 * @author Maxime
 */
public class Polynomial extends Functions {

    private double[] polynome;
private double xMax;
    public Polynomial(double[] polynome, double xMax) {
        this.polynome = polynome;
        this.xMax = xMax;
    }

        @Override
    public float createData(int relativeTime) {
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
