package main.java.dsl.kernel.definition;

import java.util.ArrayList;

/**
 * @author user
 */
public class IntervalFunctions extends Functions {

    private ArrayList<Functions> mChildGraphics = new ArrayList<Functions>();

    @Override
    public float createData(int instantT) {
        return -1;
    }

    //Ajoute le graphique à la composition.
    public void add(Functions function) {
        mChildGraphics.add(function);
    }

    //Retire le graphique de la composition.
    public void remove(Functions function) {
        mChildGraphics.remove(function);
    }
}
