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

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;



public class NewCartFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<NewDisplayItem> itemArrayList;
    private String[] itemname;
    private int[] img;
    DatabaseReference dbref;
    RecyclerView recyclerView;

    public NewCartFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewItemFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static NewItemFrag newInstance(String param1, String param2) {
        NewItemFrag fragment = new NewItemFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialized();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setHasFixedSize(true);
        NewItemAdapter newItemAdapter = new NewItemAdapter(getContext(),itemArrayList);
        recyclerView.setAdapter(newItemAdapter);
        newItemAdapter.notifyDataSetChanged();

    }

    private void dataInitialized() {
        itemArrayList = new ArrayList<>();

        itemname = new String[] {
                "hi", "hello", "No"

        };
        img = new int[] {
                R.drawable.unimas_logo,R.drawable.unimas_logo,R.drawable.unimas_logo
        };



        for (int i = 0; i<itemname.length; i++)   {
            NewDisplayItem newDisplayItem = new NewDisplayItem(itemname[i], img[i]);
            itemArrayList.add(newDisplayItem);
        }
    }
}