package com.recette.cookwell;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {



    private static final String SHARED_PREF = "Shared Preferences";
    private static final String LIST_NAME = "Favorites";

    private RecyclerView favoritesRV;
    private RecipeAdapter favadapter;

    private RecyclerView getRV;
    private GroceryAdapter getadapter;

    private RecyclerView homeRV;
    private PantryAdapter homeadapter;

    private static ArrayList<Recipe> favorites;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Home");

        favoritesRV = view.findViewById(R.id.favoritesRV);
        favoritesRV.setHasFixedSize(true);
        favoritesRV.setLayoutManager(new LinearLayoutManager(view.getContext()));

        loadData();

        favadapter = new RecipeAdapter(favorites);
        favoritesRV.setAdapter(favadapter);

        getRV = view.findViewById(R.id.needRV);
        getRV.setHasFixedSize(true);
        getRV.setLayoutManager(new LinearLayoutManager(view.getContext()));

        GroceriesFragment.staticLoadData(view.getContext());

        getadapter = new GroceryAdapter(GroceriesFragment.getList());
        getRV.setAdapter(getadapter);

        homeRV = view.findViewById(R.id.homeRV);
        homeRV.setHasFixedSize(true);
        homeRV.setLayoutManager(new LinearLayoutManager(view.getContext()));

        PantryFragment.staticLoadData(view.getContext());

        homeadapter = new PantryAdapter(PantryFragment.getList());
        homeRV.setAdapter(homeadapter);

        return view;
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_NAME, null);
        Type type = new TypeToken<ArrayList<Recipe>>() {}.getType();
        favorites = gson.fromJson(json, type);

        if (favorites == null) {
            favorites = new ArrayList<>();
        }

    }

    public static void staticLoadData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_NAME, null);
        Type type = new TypeToken<ArrayList<Recipe>>() {}.getType();
        favorites = gson.fromJson(json, type);

        if (favorites == null) {
            favorites = new ArrayList<>();
        }
    }

    public static boolean checkInList(Recipe r, Context context) {

        staticLoadData(context);
        // check if are already in the favorites list

        // to do make equals to method
        for (Recipe i : favorites) {
            if (i.getName().equalsIgnoreCase(r.getName())){
                return true;
            }
        }

        return false;
    }

    public static void addToList(Recipe r, Context context){
        if (!checkInList(r, context))
            favorites.add(r);
        saveData(context);
    }

    public static ArrayList<Recipe> getFavorites(){
        return favorites;
    }

    public static void removeFromList(Recipe r, Context context){
        staticLoadData(context);

        Recipe remove = new Recipe();

        for (Recipe i : favorites) {
            if (i.getName().equalsIgnoreCase(r.getName())){
                remove = i;
            }
        }

        favorites.remove(remove);

        saveData(context);
    }

    public static void saveData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favorites);
        editor.putString(LIST_NAME, json);
        editor.apply();
    }

}