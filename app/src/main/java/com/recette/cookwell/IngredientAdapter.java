package com.recette.cookwell;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    ArrayList<Ingredient> list;
    Context context;

    // This is a recycler adapter for contacts, every Firebase user + yourself appear in the recycler view
    public IngredientAdapter(ArrayList<Ingredient> list, Context activity) {
        this.list = list;
        this.context = activity;
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        holder.ingredient.setText("- " + Double.toString(list.get(position).getAmount()) + " " + list.get(position).getUnit() + " " + list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ingredient;
        ImageButton edit;

        public ViewHolder(final View itemView) {
            super(itemView);

            ingredient = itemView.findViewById(R.id.ingredient);

            edit = itemView.findViewById(R.id.edit_ingredient);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context).setTitle("Manage Ingredient").setMessage("Edit Ingredient");
                    final EditText input = new EditText(context);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setText(list.get(getAdapterPosition()).toString());
                    alertDialog.setView(input);
                    alertDialog.setIcon(R.drawable.edit);
                    alertDialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int pos) {
                                    String [] s = input.getText().toString().trim().split("\\s+");
                                    AddIngredientsFragment.getIngredients().get(getAdapterPosition()).setIngredient(s);
                                    notifyDataSetChanged();

                                }
                            });
                    alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int pos) {
                            AddIngredientsFragment.getIngredients().remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                        }
                    });

                    alertDialog.setNeutralButton("Cancel", null);
                    alertDialog.show();
                }
            });

        }

    }
}
