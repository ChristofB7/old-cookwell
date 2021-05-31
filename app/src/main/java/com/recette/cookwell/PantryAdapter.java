package com.recette.cookwell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ViewHolder> {

    ArrayList<Item> list;

    public PantryAdapter(ArrayList<Item> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PantryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantry_layout, parent, false);
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
        Button remove;

        public ViewHolder(View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.pantry_item);
            remove = itemView.findViewById(R.id.remove_pantry_item);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.isChecked()){
                        for (Item i : PantryFragment.getList()){
                            if (i.getItem().equals(item.getText().toString())){
                                i.setChecked(true);
                            }
                        }
                    } else {
                        for (Item i : PantryFragment.getList()){
                            if (i.getItem().equals(item.getText().toString())){
                                i.setChecked(false);
                            }
                        }
                    }
                    PantryFragment.updateList(list, view.getContext());
                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Item toRemove = new Item("");
                    for (Item i : PantryFragment.getList()){
                        if (i.getItem().equals(item.getText().toString())){
                            toRemove = i;
                        }
                    }
                    list.remove(toRemove);
                    PantryFragment.updateList(list, view.getContext());
                    notifyDataSetChanged();
                }
            });

        }

    }
}