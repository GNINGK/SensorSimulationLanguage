/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.dsl.kernel.definition;

import java.util.List;

/**
 *
 * @author Maxime
 */
public class SumAggregating extends AggregatingLaw<Float> {

    @Override
    public Float aggregate(List<Tuple> tuples) {
        float result = 0;
        for (Tuple t : tuples) {
            result += (float) t.getValue();
        }
        return result;
    }

}
