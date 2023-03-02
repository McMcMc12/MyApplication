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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class cart_frag extends Fragment implements ItemViewInterface{

    private ArrayList<CartInventory> cartArrayList;
    CartAdapter cartAdapter;
    DatabaseReference cartref;
    FirebaseUser user;
    StorageReference storageReference;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.cart_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        cartArrayList = new ArrayList<>();
        cartAdapter = new CartAdapter(getContext(), cartArrayList, this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userId).child("Cart");
            storageReference = FirebaseStorage.getInstance().getReference("uploads");

            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String inventoryKey = snapshot.getValue(String.class);
                    DatabaseReference inventoryRef = FirebaseDatabase.getInstance().getReference("User").child(userId).child("Inventory").child(inventoryKey);

                    inventoryRef.addListenerForSingleValueEvent(new ValueEventListener() {

                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            cartArrayList.clear();
                            CartInventory inventory = snapshot.getValue(CartInventory.class);
                            cartArrayList.add(inventory);
                            cartAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    String inventoryKey = snapshot.getValue(String.class);
                    for (int i = 0; i < cartArrayList.size(); i++) {
                        if (cartArrayList.get(i).getKey().equals(inventoryKey)) {
                            cartArrayList.remove(i);
                            cartAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


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