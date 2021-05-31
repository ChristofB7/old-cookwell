package com.recette.cookwell;

import java.util.Comparator;

public class SizeComparator implements Comparator<Recipe> {
    @Override
    public int compare(Recipe t0, Recipe t1) {
        if (t0.getServingSize() > t1.getServingSize()) {
            return 1;
        } else if (t0.getServingSize() < t1.getServingSize()) {
            return -1;
        } else
            return 0;
    }
}
