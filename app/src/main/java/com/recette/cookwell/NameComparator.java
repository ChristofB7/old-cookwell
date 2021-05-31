package com.recette.cookwell;

import java.util.Comparator;

public class NameComparator implements Comparator<Recipe> {
    @Override
    public int compare(Recipe t0, Recipe t1) {
        return t0.getName().compareTo(t1.getName());
    }
}
