package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;


public class cart_frag extends Fragment implements ItemViewInterface{

    private ArrayList<Cart> cartArrayList;
    CartAdapter cartAdapter;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_new_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        cartArrayList = new ArrayList<>();
        cartAdapter = new CartAdapter(getContext(), cartArrayList, this);
        String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(user);
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Cart cart = dataSnapshot.getValue(Cart.class);

                    cartArrayList.add(cart);
                }
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setAdapter(cartAdapter);


    }


    @Override
    public void onItemClick(int position) {

        ItemCartDialog itemCartDialog = new ItemCartDialog();
        Bundle bundle = new Bundle();
        bundle.putString("Name",cartArrayList.get(position).getItem_name());
        bundle.putString("Category",cartArrayList.get(position).getCat());
        bundle.putString("Price",cartArrayList.get(position).getPrice());
        bundle.putString("Dis",cartArrayList.get(position).getDescription());
        bundle.putString("Url", cartArrayList.get(position).getImageUrl());
        itemCartDialog.setArguments(bundle);

        itemCartDialog.show(requireActivity().getSupportFragmentManager(), "item dialog");
    }
}