package com.recette.cookwell;

public class Item {

    String item;
    Boolean checked;


    public Item() { }

    public Item(String item) {
        this.item = item;
        checked = false;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

}
