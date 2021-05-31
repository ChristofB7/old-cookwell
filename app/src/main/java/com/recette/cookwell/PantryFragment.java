package com.recette.cookwell;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class PantryFragment extends Fragment {

    final static String LIST_NAME = "pantry";
    final static String SHARED_PREF = "shared preferences";
    private EditText pantry_item;
    private Button add, check;
    private RecyclerView recycler_view;
    private RecyclerView.Adapter adapter;

    public static ArrayList<Item> pantry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pantry, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("My Pantry");

        pantry_item = view.findViewById(R.id.pantry_to_add);
        add = view.findViewById((R.id.add_pantry_item));

        recycler_view = view.findViewById(R.id.pantry_rv);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(view.getContext()));

        loadData();

        adapter = new PantryAdapter(pantry);
        recycler_view.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pantry_item.getText().toString().equals("")) {
                    pantry.add(new Item(pantry_item.getText().toString().trim()));
                    pantry_item.setText("");
                    adapter = new PantryAdapter(pantry);
                    recycler_view.setAdapter(adapter);
                    saveData((MainActivity) getActivity());
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    public static void saveData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(pantry);
        editor.putString(LIST_NAME, json);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_NAME, null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        pantry = gson.fromJson(json, type);

        if (pantry == null){
            pantry = new ArrayList<>();
        }

    }

    public static void staticLoadData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_NAME, null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        pantry = gson.fromJson(json, type);

        if (pantry == null){
            pantry = new ArrayList<>();
        }

    }

    public static void addItems(ArrayList<Item> p, Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_NAME, null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        pantry = gson.fromJson(json, type);

        if (pantry == null){
            pantry = new ArrayList<>();
        }

        pantry.addAll(p);

        saveData(context);

    }

    public static ArrayList<Item> getList(){
        return pantry;
    }

    public static void updateList(ArrayList<Item> p, Context context){

        pantry = p;
        saveData(context);
    }

    public static ArrayList<Item> itemsNotInPantry(ArrayList<Item> list, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_NAME, null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        pantry = gson.fromJson(json, type);

        if (pantry == null){
            pantry = new ArrayList<>();
        }

        ArrayList<Item> itemsNotInPantry = new ArrayList<>();

        // check which items are in list
        for (Item l : list) {
            boolean inPantry = false;
            for (Item i : pantry) {
                if (i.getItem().equalsIgnoreCase(l.getItem())){
                    inPantry = true;
                    break;
                }
            }
            if (!inPantry)
                itemsNotInPantry.add(l);
        }

        return itemsNotInPantry;


    }
}
