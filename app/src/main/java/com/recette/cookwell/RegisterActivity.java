package com.recette.cookwell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    TextView haveAccount;
    EditText email, password, name;
    Button register;

    ProgressDialog progressDialog;

    // Firebase authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        name = findViewById(R.id.nameReg);
        email = findViewById(R.id.emailReg);
        password = findViewById(R.id.passwordReg);
        register = findViewById(R.id.registerButton);
        haveAccount = findViewById(R.id.have_acct);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = email.getText().toString().trim();
                String p = password.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(e).matches()){
                    //validate & set error
                    email.setError("Invalid Email");
                    email.setFocusable(true);
                } else if (password.length() < 6){
                    password.setError("Password length must be  at least 6 characters.");
                    password.setFocusable(true);
                } else{
                    registerUser(e, p);
                }
            }
        });

        // login text view on click listener
        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
            }
        });

    }

    private void registerUser(String email, String password) {
        progressDialog.show();

        // creates a user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, display message to user, go to profile page
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressDialog.dismiss();

                            String email = user.getEmail();
                            String uid = user.getUid();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            // store data
                            DatabaseReference reference = database.getReference().child("users");

                            // empty list for favorites
                            ArrayList<String> favorites = new ArrayList<>();
                            favorites.add("");

                            reference.push().setValue(new User(uid, name.getText().toString().trim(), email, favorites));

                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
