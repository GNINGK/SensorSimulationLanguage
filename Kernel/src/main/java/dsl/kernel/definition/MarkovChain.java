/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.dsl.kernel.definition;

/**
 * @author user
 */
public class MarkovChain extends Behavior {

    private float[][] transitionMatrix;
    private int frequency;
    int currentState;//etat actuelle
    String[] statesNames;
            
    public MarkovChain(int stateNb, int initialState, String[] statesNames) {
        transitionMatrix = new float[stateNb][stateNb];
        this.currentState = initialState;
        this.statesNames = statesNames;
    }

    @Override
    public String createData(int instantT) {
        String result = null;
        if (instantT % frequency == 0) {
            double r = Math.random();
            double sum = 0.0;

            // determine next currentState
            for (int j = 0; j < statesNames.length; j++) {
                sum += transitionMatrix[currentState][j];
                if (r <= sum) {
                    currentState = j;
                    result = statesNames[currentState];
                    break;
                }
            }
        } else {
            return statesNames[currentState];
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
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * @param frequency the frequency to set
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
