package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ActivityMyInventoryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class myInventory extends AppCompatActivity implements ItemViewInterface{

    ActivityMyInventoryBinding binding;
    ArrayList<Inventory> myArraylisy;
    private RecyclerView myrecyclerView;
    DatabaseReference dbref;
    StorageReference stref;
    myInventoryAdapter itemAdapter;
    public Button edit, delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inventory);

        myArraylisy = new ArrayList<>();

        myrecyclerView = findViewById(R.id.myInventoryRecyclerView);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myInventoryAdapter itemAdapter = new myInventoryAdapter(this, myArraylisy, this);
        myrecyclerView.setAdapter(itemAdapter);



        dbref = FirebaseDatabase.getInstance().getReference("Inventory");
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
        });

    }

    @Override
    public void onItemClick(int position) {
        MyInventoryDialog myInventoryDialog = new MyInventoryDialog();
        //Intent intent = new Intent(this, MyInventoryDialog.class);
        //intent.putExtra("name", myArraylisy.get(position).getItem_name());
        //intent.putExtra("cat",myArraylisy.get(position).getCat());
        //intent.putExtra("price", myArraylisy.get(position).getPrice());
        //intent.putExtra("dis", myArraylisy.get(position).getDescription());
        //intent.putExtra("image", myArraylisy.get(position).getImageUrl());

        Bundle bundle = new Bundle();
        bundle.putString("Name",myArraylisy.get(position).getItem_name());
        bundle.putString("Category",myArraylisy.get(position).getCat());
        bundle.putString("Price",myArraylisy.get(position).getPrice());
        bundle.putString("Dis",myArraylisy.get(position).getDescription());
        bundle.putString("Url", myArraylisy.get(position).getImageUrl());
        myInventoryDialog.setArguments(bundle);

        myInventoryDialog.show(getSupportFragmentManager(), "inventory dialog");
    }
}