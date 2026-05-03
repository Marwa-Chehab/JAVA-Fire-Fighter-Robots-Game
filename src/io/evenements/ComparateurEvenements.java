package io.evenements;

import java.util.*;


/**
 * Classe de comparaison de deux évènements
 * Probablement inutile et aurait pu être remplacée par une interface java de comparaison 
 */
public class ComparateurEvenements implements Comparator<Evenement>{

    
    @Override
    public int compare(Evenement e1, Evenement e2) {
        if (e1.getDate() < e2.getDate())
            return -1;
        else if (e1.getDate() > e2.getDate())
            return 1;
        return 0;
    }

}