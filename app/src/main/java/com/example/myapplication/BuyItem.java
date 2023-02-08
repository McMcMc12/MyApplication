package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BuyItem extends AppCompatActivity {

    TextView name, category, price, discription;
    ImageView imageView;
    Button add, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_item);

        name = findViewById(R.id.textView12);
        category = findViewById(R.id.textView5);
        price = findViewById(R.id.textView13);
        discription  = findViewById(R.id.textView14);
        imageView = findViewById(R.id.imageView7);
        add = findViewById(R.id.button4);
        exit = findViewById(R.id.button5);




    }
}