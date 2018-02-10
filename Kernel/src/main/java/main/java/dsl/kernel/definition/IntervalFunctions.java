package main.java.dsl.kernel.definition;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author user
 */
public class IntervalFunctions extends Functions {

    private HashMap<Interval,Functions> listFunctions = new HashMap<>();

    @Override
    public float createData(int instantT) {

        return -1;
    }

    public void add(Interval interval, Functions function) {
        listFunctions.put(interval, function);
    }

    public void remove(Interval interval) {
        listFunctions.remove(interval);
    }
}
