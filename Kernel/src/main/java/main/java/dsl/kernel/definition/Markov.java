package main.java.dsl.kernel.definition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxime
 */
public class Markov extends Behavior<String> {

    private List<float[]> transitionMatrix;
    private int frequency = 1;
    int initialState;
    List<String> statesName;
    int statesNb;


    public Markov() {
        this.statesNb = 0;
        this.transitionMatrix = new ArrayList<>();
        this.statesName = new ArrayList<>();
    }

    /**
     *
     * @param statesNb nb etat
     * @param initialState numero de l'etat initial dans la matrice
     * @param statesName nom de tous les etats
     */
    public Markov(int statesNb, int initialState, List<String> statesName, List<float[]> transitionMatrix) {
        this.statesNb = statesNb;
        this.transitionMatrix = transitionMatrix;
        this.initialState = initialState;
        this.statesName = statesName;
    }

    @Override
    public String createData(float relativeTime, float noise) {
        String result = "";
        if (relativeTime % frequency == 0) {
            double r = Math.random();
            double sum = 0.0;
            // determine next state 
            for (int j = 0; j < statesNb; j++) {
                sum += transitionMatrix.get(initialState)[j];
                if (r <= sum) {
                    initialState = j;
                    result = statesName.get(initialState);
                    break;
                }
            }
        } else {
            return statesName.get(initialState);
        }
        return result;
    }

    /**
     * @param transitionMatrix the transitionMatrix to set
     */
    public void setTransitionMatrix(List<float[]> transitionMatrix) {
        this.transitionMatrix = transitionMatrix;
    }

    /**
     * @return the frequency
     */
    public int getFrequency() { return frequency; }

    /**
     * @param frequency the frequency to set
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void addState(String stateName, float[] matrix) {
        this.statesName.add(stateName);
        this.transitionMatrix.add(matrix);
        if(this.statesNb == 0)
            this.initialState = 1;
        this.statesNb++;
    }

}
