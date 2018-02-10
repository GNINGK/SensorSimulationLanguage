package main.java.dsl.kernel.definition;

import java.util.ArrayList;

/**
 * @author user
 */
public class IntervalFunctions extends Functions {

    private ArrayList<Functions> interval = new ArrayList<Functions>();

    @Override
    public float createData(int instantT) {
        
        return -1;
    }

    public void add(Functions function) {
        interval.add(function);
    }

    public void remove(Functions function) {
        interval.remove(function);
    }
}
