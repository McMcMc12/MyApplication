package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ActivityMyInventoryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class myInventory extends AppCompatActivity implements ItemViewInterface{

    ActivityMyInventoryBinding binding;
    ArrayList<Inventory> myArraylist;
    private RecyclerView myrecyclerView;
    DatabaseReference dbref;
    StorageReference stref;
    myInventoryAdapter itemAdapter;
    public Button edit, delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inventory);

        myArraylist = new ArrayList<>();

        myrecyclerView = findViewById(R.id.myInventoryRecyclerView);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myInventoryAdapter itemAdapter = new myInventoryAdapter(this, myArraylist, this);
        myrecyclerView.setAdapter(itemAdapter);

        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        String user = userAuth.getCurrentUser().getUid();
        dbref = FirebaseDatabase.getInstance().getReference("User");

        dbref.orderByKey().equalTo(user).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot childSnapshot: snapshot.child("Inventory").getChildren()) {
                    Inventory inventory = childSnapshot.getValue(Inventory.class);
                    myArraylist.add(inventory);
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

        /*dbref = FirebaseDatabase.getInstance().getReference("Inventory");
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        String user = userAuth.getCurrentUser().getUid();
        dbref.orderByChild("user").equalTo(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Inventory inventory = dataSnapshot.getValue(Inventory.class);

                    myArraylisy.add(inventory);
                }
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }

    @Override
    public void onItemClick(int position) {
        MyInventoryDialog myInventoryDialog = new MyInventoryDialog();

        Bundle bundle = new Bundle();
        bundle.putString("Name", myArraylist.get(position).getItem_name());
        bundle.putString("Category", myArraylist.get(position).getCat());
        bundle.putString("Price", myArraylist.get(position).getPrice());
        bundle.putString("Dis", myArraylist.get(position).getDescription());
        bundle.putString("Url", myArraylist.get(position).getImageUrl());
        myInventoryDialog.setArguments(bundle);

        myInventoryDialog.show(getSupportFragmentManager(), "inventory dialog");
    }
}