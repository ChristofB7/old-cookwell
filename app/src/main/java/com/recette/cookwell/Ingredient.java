package com.recette.cookwell;

public class Ingredient {

    String name;
    double amount;
    String unit;

    public Ingredient(String name, double amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public Ingredient () { }

    public String toString(){
        return amount + " " + unit + " " + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setIngredient(String[] s){
        amount = Double.parseDouble(s[0]);
        unit = s[1];
        name = s[2];

    }

}
