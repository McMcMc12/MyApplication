package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class VPadapter extends FragmentStateAdapter {

    private String[] titles= new String [] {"Items", "Chats", "Search"};


    public VPadapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new NewItemFrag();
            case 1:
                return new NewCartFrag();
            case 2:
                return new Search_frag();
        }
        return new NewItemFrag();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
