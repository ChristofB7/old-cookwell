package com.recette.cookwell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SignInPage extends AppCompatActivity {

    private Button register, signin;
    private TextView continuebutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinpage);

        register = findViewById(R.id.registerButton);
        signin = findViewById(R.id.emailButton);
        continuebutton = findViewById(R.id.continuebutton);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start register activity
                startActivity(new Intent(SignInPage.this, RegisterActivity.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start signin activity
                startActivity(new Intent(SignInPage.this, SignInActivity.class));
            }
        });

        continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInPage.this, MainActivity.class));
            }
        });

    }

}
