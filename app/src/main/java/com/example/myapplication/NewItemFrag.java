        package com.example.myapplication;

        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
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


        public class NewItemFrag extends Fragment implements ItemViewInterface{

            private ArrayList<CartInventory> itemArrayList;
            private ArrayList<CartInventory> dialogItemArrayList;
            ItemAdapter itemAdapter;
            DatabaseReference databaseReference;
            StorageReference storageReference;
            RecyclerView recyclerView;
            Spinner spinner;



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
                spinner = view.findViewById(R.id.spinner);

                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerView.setHasFixedSize(true);

                itemArrayList = new ArrayList<>();
                itemAdapter = new ItemAdapter(getContext(), itemArrayList, this);

                databaseReference = FirebaseDatabase.getInstance().getReference("User");
                storageReference = FirebaseStorage.getInstance().getReference("uploads");

                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        for (DataSnapshot childSnapshot: snapshot.child("Inventory").getChildren()) {
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


                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.searchcatergories, android.R.layout.simple_dropdown_item_1line);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinner.setAdapter(adapter);



                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ArrayList<CartInventory> filterOn = new ArrayList<>();
                        String selectedCategory = parent.getItemAtPosition(position).toString();

                        for (CartInventory item : itemArrayList) {
                            if (selectedCategory.equals("All")) {
                                filterOn.add(item);
                            }else{
                                if(item.getCat().equals(selectedCategory)){
                                    filterOn.add(item);
                                }
                            }
                        }

                        if (selectedCategory.equals("All")) {
                            dialogItemArrayList = itemArrayList;
                            itemAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(itemAdapter);
                        } else {
                            ItemSearchAdapter itemSearchAdapter = new ItemSearchAdapter(getContext(), filterOn, NewItemFrag.this::onItemClick);
                            itemSearchAdapter.notifyItemChanged(position);
                            itemSearchAdapter.notifyDataSetChanged();
                            dialogItemArrayList = filterOn;
                            recyclerView.setAdapter(itemSearchAdapter);
                        }

                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });

             }


            @Override
            public void onItemClick(int position) {

                ItemDialog itemDialog = new ItemDialog();
                Bundle bundle = new Bundle();
                bundle.putString("Seller", dialogItemArrayList.get(position).getUser());
                bundle.putString("key", dialogItemArrayList.get(position).getKey());
                bundle.putString("Name",dialogItemArrayList.get(position).getItem_name());
                bundle.putString("Category",dialogItemArrayList.get(position).getCat());
                bundle.putString("Price",dialogItemArrayList.get(position).getPrice());
                bundle.putString("Dis",dialogItemArrayList.get(position).getDescription());
                bundle.putString("Url", dialogItemArrayList.get(position).getImageUrl());
                itemDialog.setArguments(bundle);

                itemDialog.show(requireActivity().getSupportFragmentManager(), "item dialog");
            }
        }