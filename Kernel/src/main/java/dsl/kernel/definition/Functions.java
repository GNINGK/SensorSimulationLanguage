package main.java.dsl.kernel.definition;

/**
 * @author user
 */
public class Functions extends Behavior {

    private double[] polynome;

    public Functions(double[] polynome) {
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
