package com.recette.cookwell;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class DirectionAdapter extends RecyclerView.Adapter<DirectionAdapter.ViewHolder> {
    ArrayList<String> list;
    Context context;

    // This is a recycler adapter for contacts, every Firebase user + yourself appear in the recycler view
    public DirectionAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DirectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.direction_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectionAdapter.ViewHolder holder, int position) {
        holder.direction.setText(Integer.toString(position + 1) + ". " + list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageButton edit;
        TextView direction;

        public ViewHolder(View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.edit_direction);
            direction = itemView.findViewById(R.id.direction);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context).setTitle("Manage Ingredient").setMessage("Edit Ingredient");
                    final EditText input = new EditText(context);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setText(list.get(getAdapterPosition()));
                    alertDialog.setView(input);
                    alertDialog.setIcon(R.drawable.edit);
                    alertDialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int pos) {
                            AddDirectionsFragment.getDirections().set(getAdapterPosition(), input.getText().toString().trim());
                            notifyDataSetChanged();

                        }
                    });
                    alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int pos) {
                            AddDirectionsFragment.getDirections().remove(getAdapterPosition());
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
