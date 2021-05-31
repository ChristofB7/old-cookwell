package com.recette.cookwell;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    private DrawerLayout drawer;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // checks if the user is signed in
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null){
                    /*Query userQuery = FirebaseDatabase.getInstance().getReference("users").orderByChild("uid").equalTo(currentUser.getUid());
                    userQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()){ }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });*/
                } else {
                    Menu menu = navigationView.getMenu();
                    MenuItem nav_login = menu.findItem(R.id.logout);
                    nav_login.setTitle("Login");
                }
            }
        };

        //Init and attach
        mAuth.addAuthStateListener(authStateListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                    new HomeFragment()).addToBackStack(null).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new HomeFragment()).addToBackStack(null).commit();
                break;
            case R.id.pantry:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new PantryFragment()).addToBackStack(null).commit();
                break;
            case R.id.recipes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new RecipesFragment()).addToBackStack(null).commit();
                break;
            case R.id.groceries:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new GroceriesFragment()).addToBackStack(null).commit();
                break;
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, SignInPage.class));
                finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        // Check if user is signed in (non-null) and update UI accordingly.

        /*FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            //TODO
            email.setText(currentUser.getEmail());
        } else {
            startActivity(new Intent (MainActivity.this, SignInPage.class));
        }
*/
        super.onStart();
    }

}
