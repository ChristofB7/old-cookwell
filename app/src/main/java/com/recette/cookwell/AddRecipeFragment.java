package com.recette.cookwell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class AddRecipeFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabsAdapter tabsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Summary"));
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.addTab(tabLayout.newTab().setText("Directions"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        // create an instance of my class tabs adapter
        tabsAdapter = new TabsAdapter(getChildFragmentManager());
        // create a ViewPager with this new adapter
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(tabsAdapter);
        // the viewpager allows the user to see each page(or tab) that's in the tab adapter
        tabLayout.setupWithViewPager(viewPager);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Add Recipe");

        // Inflate the layout for this fragment
        return view;
    }

}
