package com.example.myapplication;

public class category {

    public String Food, Clothing, Accessory, Books, Electronics;

    public category(String food, String clothing, String accessory, String books, String electronics) {
        Food = food;
        Clothing = clothing;
        Accessory = accessory;
        Books = books;
        Electronics = electronics;
    }


    public String getFood() {
        return Food;
    }

    public String getClothing() {
        return Clothing;
    }

    public String getAccessory() {
        return Accessory;
    }

    public String getBooks() {
        return Books;
    }

    public String getElectronics() {
        return Electronics;
    }
}
