package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentItemDisplayBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class item_frag_notused extends Fragment{

    FragmentItemDisplayBinding binding;
    ArrayList<Inventory> inventoryArrayList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentItemDisplayBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        inventoryArrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("inventory");




        String[] itemname = {"1","2","3","4","5","6","7"};
        int[] itemimage = {R.drawable.unimas_logo,R.drawable.unimas_logo,R.drawable.unimas_logo,R.drawable.unimas_logo,
                R.drawable.unimas_logo,R.drawable.unimas_logo,R.drawable.unimas_logo

        };


        ItemFragmentAdapter itemFragmentAdapter = new ItemFragmentAdapter(requireContext(), itemname, itemimage, inventoryArrayList);
        binding.gridView.setAdapter(itemFragmentAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Inventory inventory = dataSnapshot.getValue(Inventory.class);
                    inventoryArrayList.add(inventory);
                }
                itemFragmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

}