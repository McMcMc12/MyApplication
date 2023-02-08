package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentCartFragBinding;


public class cart_frag extends Fragment {

    FragmentCartFragBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCartFragBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String[] itemname = {"1","2","3","4"};
        int[] itemimage = {R.drawable.unimas_logo,R.drawable.unimas_logo,R.drawable.unimas_logo,R.drawable.unimas_logo};

        CartFragmentAdapter cartFragmentAdapter = new CartFragmentAdapter(requireContext(), itemname, itemimage);
        binding.listView.setAdapter(cartFragmentAdapter);

        return view;
    }
}