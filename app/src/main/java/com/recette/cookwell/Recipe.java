package com.recette.cookwell;

import java.util.ArrayList;

public class Recipe {

    String name;

    ArrayList<Ingredient> ingredients;
    ArrayList<String> directions;
    String image;
    String notes;
    int prepTime;
    int cookTime;
    int servingSize;

    // TODO reviews
    // TODO images


    public Recipe(String name, String image, ArrayList<Ingredient> ingredients, ArrayList<String> directions, String notes, int prepTime, int cookTime, int servingSize) {
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.directions = directions;
        this.notes = notes;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servingSize = servingSize;
    }

    public Recipe () { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<String> directions) {
        this.directions = directions;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

}
