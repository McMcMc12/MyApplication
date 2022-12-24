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
                return new item_frag();
            case 1:
                return new chat_frag();
            case 2:
                return new Search_frag();
        }
        return new item_frag();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
