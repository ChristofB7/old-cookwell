package com.recette.cookwell;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TabsAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;

    public TabsAdapter(FragmentManager manager){
        super(manager);
        this.fragments = new ArrayList<Fragment>();
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                AddSummaryFragment summaryFragment = new AddSummaryFragment();
                return summaryFragment;

            case 1:
                AddIngredientsFragment ingredientsFragment = new AddIngredientsFragment();
                return ingredientsFragment;

            case 2:
                AddDirectionsFragment directionsFragment = new AddDirectionsFragment();
                return directionsFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Summary";
            case 1: return "Ingredients";
            case 2: return "Directions";
            default: return null;
        }
    }
}
