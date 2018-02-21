package main.java.dsl.kernel.definition;

/**
 * @author Maxime
 */
public class Markov extends Behavior<String> {

    private float[][] transitionMatrix;
    private int frequence = 1;
    int etatInitial;
    String[] nameState;
    int nbEtat;

    /**
     *
     * @param nbEtat nb etat
     * @param etatInitial numero de l'etat initial dans la matrice
     * @param nameState nom de tous les etats
     */
    public Markov(int nbEtat, int etatInitial, String[] nameState, float[][] transitionMatrix) {
        this.nbEtat = nbEtat;
        this.transitionMatrix = transitionMatrix;
        this.etatInitial = etatInitial;
        this.nameState = nameState;
    }

    @Override
    public String createData(float relativeTime, float noise) {
        String result = "";
        if (relativeTime % frequence == 0) {
            double r = Math.random();
            double sum = 0.0;
            // determine next state 
            for (int j = 0; j < nbEtat; j++) {
                sum += transitionMatrix[etatInitial][j];
                if (r <= sum) {
                    etatInitial = j;
                    result = nameState[etatInitial];
                    break;
                }
            }
        } else {
            return nameState[etatInitial];
        }
        return result;
    }

    /**
     * @param transitionMatrix the transitionMatrix to set
     */
    public void setTransitionMatrix(float[][] transitionMatrix) {
        this.transitionMatrix = transitionMatrix;
    }

    /**
     * @return the frequence
     */
    public int getFrequence() {
        return frequence;
    }

    /**
     * @param frequence the frequence to set
     */
    public void setFrequence(int frequence) {
        this.frequence = frequence;
    }
}
