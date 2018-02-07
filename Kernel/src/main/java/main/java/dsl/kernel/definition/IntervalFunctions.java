/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.dsl.kernel.definition;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class IntervalFunctions extends Functions {

    private ArrayList<Functions> mChildGraphics = new ArrayList<Functions>();
    
    @Override
    public String createData(int instantT) {
        return null;
    }
    
    //Ajoute le graphique à la composition.
    public void add(Functions function)
    {
        mChildGraphics.add(function);
    }

    //Retire le graphique de la composition.
    public void remove(Functions function)
    {
        mChildGraphics.remove(function);
    }
}
