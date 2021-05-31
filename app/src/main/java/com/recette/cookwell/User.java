package com.recette.cookwell;

import java.util.ArrayList;

// container class for user
public class User {

    private String name, email, uid;
    private ArrayList<String> favorites;

    public User(String uid, String name, String email, ArrayList<String> favorites){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.favorites = favorites;
    }

    public User () { }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getFavorites() { return favorites; }

    public void setFavorites(ArrayList<String> favorites) { this.favorites = favorites; }
}
