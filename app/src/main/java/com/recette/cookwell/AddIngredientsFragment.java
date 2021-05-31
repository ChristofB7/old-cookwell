package com.recette.cookwell;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import java.util.ArrayList;


public class AddIngredientsFragment extends Fragment {

    private RecyclerView.Adapter adapter_ingredient;
    private RecyclerView ingredient_rv;
    private EditText ingredient_amount, unit, ingredient_name;
    private Button addIngredient;
    private static ArrayList<Ingredient> ingredients;

    public AddIngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_ingredients, container, false);
        ingredient_amount = view.findViewById(R.id.ingredient_amount);
        unit = view.findViewById(R.id.unit);
        ingredient_name = view.findViewById(R.id.ingredient_name);
        addIngredient = view.findViewById(R.id.add_ingredient);

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ingredient_name.getText().toString().equals("") && !ingredient_amount.getText().toString().equals("")) {
                    ingredients.add(new Ingredient(ingredient_name.getText().toString().trim(), Double.valueOf(ingredient_amount.getText().toString().trim()), unit.getText().toString().trim()));
                    adapter_ingredient = new IngredientAdapter(ingredients, getContext());
                    ingredient_rv.setAdapter(adapter_ingredient);
                    ingredient_name.setText("");
                    unit.setText("");
                    ingredient_amount.setText("");
                }
            }
        });

        ingredients = new ArrayList<>();

        ingredient_rv = view.findViewById(R.id.ingredientrv);
        ingredient_rv.setHasFixedSize(true);
        ingredient_rv.setLayoutManager(new LinearLayoutManager(view.getContext()));


        return view;

    }

    public static ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

}
