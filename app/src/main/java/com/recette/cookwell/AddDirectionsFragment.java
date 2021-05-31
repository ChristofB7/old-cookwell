package com.recette.cookwell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public class AddDirectionsFragment extends Fragment {

    private RecyclerView.Adapter adapter_direction;

    private RecyclerView direction_rv;
    private EditText  direction_recipe;
    private Button addDirection;
    private static ArrayList<String> directions;

    public AddDirectionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_directions, container, false);
        direction_recipe = view.findViewById(R.id.direction_recipe);
        addDirection = view.findViewById(R.id.add_direction);


        addDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (direction_recipe.getText().toString() != "" && direction_recipe.getText().toString() != null) {
                    directions.add(direction_recipe.getText().toString().trim());
                    adapter_direction = new DirectionAdapter(directions, getContext());
                    direction_rv.setAdapter(adapter_direction);
                    direction_recipe.setText("");
                }
            }
        });

        directions = new ArrayList<>();

        direction_rv = view.findViewById(R.id.directionrv);
        direction_rv.setHasFixedSize(true);
        direction_rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;

    }

    public static ArrayList<String> getDirections() {
        return directions;
    }




}
