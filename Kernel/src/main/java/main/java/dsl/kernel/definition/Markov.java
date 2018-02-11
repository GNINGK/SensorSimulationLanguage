package main.java.dsl.kernel.definition;
 
/** 
 * @author Maxime 
 */ 
public class Markov extends Behavior { 
 
    private float[][] transitionMatrix; 
    private int frequence; 
    int state;//etat actuelle 
    float[] nameState; 
 
    public Markov(int nbEtat, int etatInitial, float[] nameState) { 
        transitionMatrix = new float[nbEtat][nbEtat]; 
        this.state = etatInitial; 
        this.nameState = nameState; 
    } 
 
    @Override 
    public float createData(float relativeTime) { 
        float result = 0; 
        if (relativeTime % frequence == 0) { 
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
