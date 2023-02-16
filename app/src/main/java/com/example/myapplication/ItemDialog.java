package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ItemDialog extends AppCompatDialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_dialog, null);


        TextView name = view.findViewById(R.id.textView11);
        TextView cat = view.findViewById(R.id.textView18);
        TextView price = view.findViewById(R.id.textView19);
        TextView dis = view.findViewById(R.id.textView20);
        ImageView iv = view.findViewById(R.id.imageView8);
        Button addCart = view.findViewById(R.id.button6);
        Button back = view.findViewById(R.id.button7);

        Bundle bundle = getArguments();

        assert bundle != null;
        String Seller = bundle.getString("Seller");
        String key = bundle.getString("key");
        String ItemName = bundle.getString("Name");
        String ItemCat = bundle.getString("Category");
        String ItemPrice = bundle.getString("Price");
        String ItemDis = bundle.getString("Dis");
        String ItemUrl = bundle.getString("Url");
        String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        name.setText(ItemName);
        cat.setText(ItemCat);
        price.setText(ItemPrice);
        dis.setText(ItemDis);
        Picasso.get().load(ItemUrl).fit().centerCrop().into(iv);

        addCart.setOnClickListener(v -> {
            DatabaseReference rootref = FirebaseDatabase.getInstance().getReference("User").child(user).child("Cart");

            if(!user.equals(Seller)){

                rootref.child(key).setValue(key).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(),"Item is succesfully registered!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), Feed.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getContext(),"Fail to load", Toast.LENGTH_LONG).show();

                    }
                });
            }else{
                Toast.makeText(getContext(), "Cannot Buy Your Own Item", Toast.LENGTH_LONG).show();
            }


        });
        back.setOnClickListener(v -> dismiss());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();

    }

}
