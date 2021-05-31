package com.recette.cookwell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder> {

    ArrayList<Item> list;

    // This is a recycler adapter for contacts, every Firebase user + yourself appear in the recycler view
    public GroceryAdapter(ArrayList<Item> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public GroceryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Item item = list.get(position);
        // set to the name of the grocery
        holder.item.setText(item.getItem());
        // if checked, check
        holder.item.setChecked(item.getChecked());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox item;

        public ViewHolder(View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.isChecked()){
                        for (Item i : GroceriesFragment.getList()){
                            if (i.getItem().equals(item.getText().toString())){
                                i.setChecked(true);
                            }
                        }
                    } else {
                        for (Item i : GroceriesFragment.getList()){
                            if (i.getItem().equals(item.getText().toString())){
                                i.setChecked(false);
                            }
                        }
                    }
                    GroceriesFragment.updateList(list, view.getContext());
                }
            });

        }

    }
}

