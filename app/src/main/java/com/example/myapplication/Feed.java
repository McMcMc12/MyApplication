package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class Feed extends AppCompatActivity {

    VPadapter vPadapter;
    TabLayout tabLayout;
    ViewPager2 view_Pager2;
    private String[] titles= new String [] {"Items", "Cart", "Search"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        view_Pager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        vPadapter = new VPadapter(this);



        view_Pager2.setAdapter(vPadapter);
        new TabLayoutMediator(tabLayout,view_Pager2,((tab, position) -> tab.setText(titles[position]))).attach();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())   {
            case R.id.menu1:
                startActivity(new Intent(Feed.this,item_form.class));
                return true;

            case R.id.menu2:
                startActivity(new Intent(Feed.this,chats.class));
                return true;
            case R.id.menu3:
                startActivity(new Intent(Feed.this,myInventory.class));
                return true;
            case R.id.menu4:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Feed.this, Login.class));
                return true;


        }

        return super.onOptionsItemSelected(item);
    }



}