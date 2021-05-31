package com.recette.cookwell;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RecipesFragment extends Fragment {


    private FloatingActionButton add;
    private SearchView search;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;

    private ArrayList<Recipe> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipes, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Recipes");


        add = view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getActivity(), AddRecipeFragment.class);
                //startActivity(intent);
                (getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new AddRecipeFragment()).addToBackStack(null).commit();

            }
        });

        search = view.findViewById(R.id.searchBar);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.sorts, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = view.findViewById(R.id.sortingspinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    switch (text) {
                        case "Sort by Name":
                            list.sort(new NameComparator());
                            break;
                        case "Sort by Cook Time":
                            list.sort(new TimeComparator());
                            break;
                        case "Sort by Serving Size":
                            list.sort(new SizeComparator());
                            break;
                    }
                }

                // set list to adapter
                adapter = new RecipeAdapter(list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        recyclerView = view.findViewById(R.id.recipes_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        list = new ArrayList<>();

        if (!haveNetworkConnection()){
            HomeFragment.staticLoadData(view.getContext());
            list = HomeFragment.getFavorites();
        }

        // get database recipe reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("recipes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    // get the user instance of each user from the data snapshot(which is all files in our databas)
                    Recipe recipe = ds.getValue(Recipe.class);
                        list.add(recipe);
                    }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    list.sort(new NameComparator());
                }
                // set list to adapter
                adapter = new RecipeAdapter(list);
                recyclerView.setAdapter(adapter);

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }

        });

        // Inflate the layout for this fragment
        return view;

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) (getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
