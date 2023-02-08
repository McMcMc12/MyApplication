package com.example.myapplication;

public class Inventory{
    private String item_name, description, price, cat, user, imageUrl;

    public Inventory(String item_name, String description, String price, String cat, String user, String imageUrl) {
        this.item_name = item_name;
        this.description = description;
        this.price = price;
        this.cat = cat;
        this.user = user;
        this.imageUrl = imageUrl;
    }

    public Inventory() {
        super();
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
