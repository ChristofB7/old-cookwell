package com.recette.cookwell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewRecipeFragment extends Fragment {

    private ImageView image;
    private TextView recipe_name, serving_size, prep_time, cook_time, ingredients, directions, notes;
    private String name;
    FloatingActionButton add_shop, add_favorites;
    private boolean isFavorite;

    private Recipe recipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        name = getArguments().getString("recipe");

        final View view = inflater.inflate(R.layout.fragment_view_recipe, container, false);

        recipe_name = view.findViewById(R.id.recipe_name);
        serving_size = view.findViewById(R.id.view_serving_size);
        prep_time = view.findViewById(R.id.view_prep_time);
        cook_time = view.findViewById(R.id.view_cook_time);
        ingredients = view.findViewById(R.id.ingredients_list);
        directions = view.findViewById(R.id.directions_list);
        notes = view.findViewById(R.id.notes_view);
        image = view.findViewById((R.id.recipe_image));
        add_shop = view.findViewById(R.id.add_shop);
        add_favorites = view.findViewById(R.id.add_favorites);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("recipes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // get the current user - and create a user instance and put it in the list
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    // get the user instance of each user from the data snapshot(which is all files in our databas)
                    Recipe r = ds.getValue(Recipe.class);
                    if(r.getName().equals(name)){
                        recipe = r;
                        Picasso.get().load(recipe.getImage()).resize(100,100).centerCrop().into(image);
                        setIsFavorite(HomeFragment.checkInList(recipe, view.getContext()));
                        if (isFavorite) {
                            add_favorites.setImageResource(R.drawable.favorited);
                        } else {
                            add_favorites.setImageResource(R.drawable.favorite);
                        }
                        recipe_name.setText(recipe.getName());
                        serving_size.setText(Integer.toString(recipe.getServingSize()));
                        prep_time.setText(Integer.toString(recipe.getPrepTime()));
                        cook_time.setText(Integer.toString(recipe.getCookTime()));
                        notes.setText(recipe.getNotes());
                        String ingredients_list = "";
                        for (Ingredient i : recipe.getIngredients()){
                            ingredients_list = ingredients_list + "- " + i.toString() + "\n";
                        }
                        ingredients.setText(ingredients_list);
                        String directions_list = "";
                        int i = 1;
                        for (String s : recipe.getDirections()){
                            directions_list = directions_list + i + ". " + s + "\n";
                            i++;
                        }
                        directions.setText(directions_list);
                    }
                }
                if (recipe_name.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Recipe not found.", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new RecipesFragment()).addToBackStack(null).commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        add_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Item> itemsToAdd = new ArrayList<>();
                for (Ingredient i : recipe.getIngredients()) {
                    itemsToAdd.add(new Item(i.getName()));
                }

                // check if items are in pantry
                itemsToAdd = PantryFragment.itemsNotInPantry(itemsToAdd, view.getContext());

                GroceriesFragment.addToList(itemsToAdd, view.getContext());
                GroceriesFragment.saveData(getActivity());

                Toast.makeText(view.getContext(), "Ingredients added to Groceries.", Toast.LENGTH_SHORT);
            }
        });

        add_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipe != null) {
                    if (isFavorite) {
                        add_favorites.setImageResource(R.drawable.favorite);
                        HomeFragment.removeFromList(recipe, v.getContext());

                    } else {
                        add_favorites.setImageResource(R.drawable.favorited);
                        HomeFragment.addToList(recipe, v.getContext());
                    }
                }
                isFavorite = !isFavorite;
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    private void setIsFavorite(boolean b){
        isFavorite = b;
    }

}
