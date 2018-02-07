/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.dsl.kernel.definition;

/**
 * @author user
 */
public class MarkovChain extends Comportement {

    private float[][] transitionMatrix;
    private int frequence;
    int state;//etat actuelle
    String[] nameState;
            
    public MarkovChain(int nbEtat, int etatInitial, String[] nameState) {
        transitionMatrix = new float[nbEtat][nbEtat];
        this.state = etatInitial;
        this.nameState = nameState;
    }

    @Override
    public String createData(int instantT) {
        String result = null;
        if (instantT % frequence == 0) {
            double r = Math.random();
            double sum = 0.0;

            // determine next state
            for (int j = 0; j < nameState.length; j++) {
                sum += transitionMatrix[state][j];
                if (r <= sum) {
                    state = j;
                    result = nameState[state];
                    break;
                }
            }
        } else {
            return nameState[state];
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
