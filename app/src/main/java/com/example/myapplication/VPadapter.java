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
        if(position == 0 )  {
            return new NewItemFrag();
        }else if(position == 1)  {
            return new cart_frag();
        }else if(position == 2) {
            return new Search_frag();
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
