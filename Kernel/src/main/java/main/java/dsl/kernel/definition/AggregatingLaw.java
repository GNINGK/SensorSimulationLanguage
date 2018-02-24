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
public abstract class AggregatingLaw<T> {
    public abstract T aggregate(List<Tuple> tuples);  
}
