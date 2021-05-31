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


public class GroceriesFragment extends Fragment {

    private final static String LIST_NAME = "groceries";
    private final static String SHARED_PREF = "shared preferences";
    private EditText grocery;
    private Button add, done, check;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private static ArrayList<Item> grocery_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_groceries, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Groceries");

        grocery = view.findViewById(R.id.grocery_to_add);
        add = view.findViewById((R.id.add_grocery));
        done = view.findViewById(R.id.done_shopping);
        check = view.findViewById(R.id.check_pantry);

        recyclerView = view.findViewById(R.id.grocery_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        loadData();

        adapter = new GroceryAdapter(grocery_list);
        recyclerView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!grocery.getText().toString().equals("")) {
                    grocery_list.add(new Item(grocery.getText().toString().trim()));
                    grocery.setText("");
                    adapter = new GroceryAdapter(grocery_list);
                    recyclerView.setAdapter(adapter);
                    saveData(getActivity());
                }
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Item> checkedItems = new ArrayList<>();
                for (Item g : grocery_list){
                    if (g.getChecked())
                        checkedItems.add(g);
                }
                PantryFragment.addItems(checkedItems,getActivity());
                grocery_list.removeAll(checkedItems);
                saveData(getActivity());
                adapter = new GroceryAdapter(grocery_list);
                recyclerView.setAdapter(adapter);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new PantryFragment()).addToBackStack(null).commit();

            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    public static void saveData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(grocery_list);
        editor.putString(LIST_NAME, json);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_NAME, null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        grocery_list = gson.fromJson(json, type);

        if (grocery_list == null) {
            grocery_list = new ArrayList<>();
        }

    }

    public static void staticLoadData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_NAME, null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        grocery_list = gson.fromJson(json, type);

        if (grocery_list == null) {
            grocery_list = new ArrayList<>();
        }

    }

    public static ArrayList<Item> getList(){
        return grocery_list;
    }

    public static void updateList(ArrayList<Item> g, Context context){
        grocery_list = g;
        saveData(context);
    }

    public static void addToList(ArrayList<Item> list, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_NAME, null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        grocery_list = gson.fromJson(json, type);

        if (grocery_list == null){
            grocery_list = new ArrayList<>();
        }

        ArrayList<Item> itemsNotInGrocery = new ArrayList<>();

        // check which items are already in the grocery list
        for (Item l : list) {
            boolean inGrocery = false;
            for (Item i : grocery_list) {
                if (i.getItem().equalsIgnoreCase(l.getItem())){
                    inGrocery = true;
                    break;
                }
            }
            if (!inGrocery)
                itemsNotInGrocery.add(l);
        }

        grocery_list.addAll(itemsNotInGrocery);
        saveData(context);
    }

}
