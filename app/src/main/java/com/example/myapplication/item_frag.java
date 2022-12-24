package com.example.myapplication;

import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CursorAdapter;
import android.widget.GridView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class item_frag extends Fragment {

    GridView gridView;
    String name, description, category, price;
    URI uri;
    List<inventory> inventories = new ArrayList<>();
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("inventory");
        firebaseStorage = FirebaseStorage.getInstance();


        View view = inflater.inflate(R.layout.fragment_item_display, container, false);
        gridView = view.findViewById(R.id.grid_view);




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_display, container, false);
    }

    

}