package main.java.dsl.kernel.definition;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author user
 */
public class IntervalFunctions extends Functions {

    private HashMap<Interval, Functions> listIntervals = new HashMap<>();

    @Override
    public float createData(float instantT) {

        float result = Float.NaN;
        for (Entry<Interval, Functions> entry : listIntervals.entrySet()) {
            Interval interval = entry.getKey();
            Functions function = entry.getValue();
            if (interval.contains(instantT)) {
                result = function.createData(instantT);
            }
        }
        return result;
    }

    public void add(Interval interval, Functions function) {
        Set key = listIntervals.keySet();
        Iterator it = key.iterator();
        while (it.hasNext()) {
            Interval cle = (Interval) it.next(); // tu peux typer plus finement ici
            if (cle.intersects(interval)) {
                throw new IllegalArgumentException("Intersection avec un autre interval");
            }
        }
        listIntervals.put(interval, function);
    }

    public void remove(Interval interval) {
        listIntervals.remove(interval);
    }
}
