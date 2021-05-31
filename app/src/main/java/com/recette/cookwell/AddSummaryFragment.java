package com.recette.cookwell;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class AddSummaryFragment extends Fragment {
    ImageView imageView;
    EditText recipe_name, cookTime, prepTime, servingSize, notes;
    Button upload;




    private static final int PICK_IMAGE = 1;

    private Uri imageURI;
    private String URL;

    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> directions;


    public AddSummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_summary, container, false);

        imageView = view.findViewById(R.id.imageButton);
        recipe_name = view.findViewById(R.id.recipe_name);
        cookTime = view.findViewById(R.id.recipe_cook_time);
        prepTime = view.findViewById(R.id.recipe_prep_time);
        servingSize = view.findViewById(R.id.recipe_serving_size);
        notes = view.findViewById(R.id.recipe_notes);
        upload = view.findViewById(R.id.upload);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("recipes");;
                final StorageReference storage = FirebaseStorage.getInstance().getReference("recipes").child(recipe_name.getText().toString());
                if (imageURI == null) {
                    Recipe recipe = new Recipe(recipe_name.getText().toString(), "*", ingredients, directions, notes.getText().toString(), Integer.parseInt(prepTime.getText().toString()), Integer.parseInt(cookTime.getText().toString()), Integer.parseInt(servingSize.getText().toString()));
                    database.push().setValue(recipe);
                } else {
                    storage.putFile(imageURI).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return storage.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                URL = task.getResult().toString();
                                if (URL == null || URL == "")
                                    URL = "";
                                directions = AddDirectionsFragment.getDirections();
                                ingredients = AddIngredientsFragment.getIngredients();
                                Recipe recipe = new Recipe(recipe_name.getText().toString(), URL, ingredients, directions, notes.getText().toString(), Integer.parseInt(prepTime.getText().toString()), Integer.parseInt(cookTime.getText().toString()), Integer.parseInt(servingSize.getText().toString()));
                                database.push().setValue(recipe);
                            } else {
                            }
                        }
                    });
                }

                ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new RecipesFragment()).addToBackStack(null).commit();

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null)  {
            imageURI = data.getData();
            Picasso.get().load(imageURI).into(imageView);
        }
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }
}

