package com.recette.cookwell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> implements Filterable {

    ArrayList<Recipe> list;
    ArrayList<Recipe> listFull;

    public RecipeAdapter(ArrayList<Recipe> list) {
        this.list = list;
        listFull = new ArrayList<>();
        for (Recipe r: list)
            listFull.add(r);
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getImage()).placeholder(R.drawable.add_image).resize(100,100).centerCrop().into(holder.image);
        holder.name.setText(list.get(position).getName());
        holder.time.setText(Integer.toString(list.get(position).getPrepTime() + list.get(position).getCookTime()) + " mins");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Recipe> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(listFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Recipe r : listFull){
                    if(r.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(r);
                    }
                }

            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            list.clear();
            list.addAll((List) filterResults.values);

            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, time;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.recipe_name);
            time = itemView.findViewById(R.id.time);
            image = itemView.findViewById(R.id.recipe_image);

            // set on click listener to view the chat
            itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    ViewRecipeFragment fragment = new ViewRecipeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("recipe", name.getText().toString());
                    fragment.setArguments(bundle);
                    ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).addToBackStack(null).commit();
                }
            });

        }

    }
}
