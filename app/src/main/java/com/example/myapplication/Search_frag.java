package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class Search_frag extends Fragment implements ItemViewInterface {

    private Spinner spinner;

    private ArrayList<CartInventory> itemArrayList;
    ItemAdapter itemAdapter;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    RecyclerView recyclerView;
    private ArrayList<CartInventory> filteredList;
    ItemSearchAdapter itemSearchAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SearchView searchView = view.findViewById(R.id.searchView1);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        itemArrayList = new ArrayList<>();
        itemAdapter = new ItemAdapter(getContext(), itemArrayList, this);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot childSnapshot : snapshot.child("Inventory").getChildren()) {
                    CartInventory inventory = childSnapshot.getValue(CartInventory.class);
                    inventory.setKey(childSnapshot.getKey());
                    itemArrayList.add(inventory);
                }
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        recyclerView.setAdapter(itemAdapter);

        //for search by keyword



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<CartInventory> filterOn = new ArrayList<CartInventory>();
                for(CartInventory cartInventory: itemArrayList) {
                    if (cartInventory.getItem_name().toLowerCase().contains(newText.toLowerCase()))
                    {
                        filterOn.add(cartInventory);
                    }
                }
                itemSearchAdapter = new ItemSearchAdapter(getContext(), filterOn, Search_frag.this::onItemClick);
                recyclerView.setAdapter(itemSearchAdapter);
                return false;
            }

        });



}





    @Override
    public void onItemClick(int position) {
        ItemDialog itemDialog = new ItemDialog();
        Bundle bundle = new Bundle();
        bundle.putString("Seller", itemArrayList.get(position).getUser());
        bundle.putString("key", itemArrayList.get(position).getKey());
        bundle.putString("Name",itemArrayList.get(position).getItem_name());
        bundle.putString("Category",itemArrayList.get(position).getCat());
        bundle.putString("Price",itemArrayList.get(position).getPrice());
        bundle.putString("Dis",itemArrayList.get(position).getDescription());
        bundle.putString("Url", itemArrayList.get(position).getImageUrl());
        itemDialog.setArguments(bundle);

        itemDialog.show(requireActivity().getSupportFragmentManager(), "item dialog");
    }
}