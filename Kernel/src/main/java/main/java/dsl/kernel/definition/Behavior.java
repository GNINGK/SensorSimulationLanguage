package main.java.dsl.kernel.definition;

/**
 * @author Maxime
 */
public abstract class Behavior {

    public abstract float createData(float relativeTime, float noise);
    /***
     * Genere un bruit avec comme valeur max noise avec 33% de chance d'etre negatif/null/positif
     * @param noise Borne du bruit qui va etre ajoute/
     * @return 
     */
    public float generateNoise(float noise)
    {
        float result = 0;
        double r = Math.random();
        double randNoise = Math.random();
        if (r < 0.33) {
            result -= noise*randNoise;
        } else if (r > 0.66) {
            result += noise*randNoise;
        }
        return result;
    }
}
