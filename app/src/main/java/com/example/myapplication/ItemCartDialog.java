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


public class ItemCartDialog extends AppCompatDialogFragment {


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
        Button Buy = view.findViewById(R.id.button6);
        Buy.setText("Buy");
        Button back = view.findViewById(R.id.button7);
        back.setText("Remove");

        Bundle bundle = getArguments();

        assert bundle != null;
        String ItemName = bundle.getString("Name");
        String ItemCat = bundle.getString("Category");
        String ItemPrice = bundle.getString("Price");
        String ItemDis = bundle.getString("Dis");
        String ItemUrl = bundle.getString("Url");
        String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        String key = user + "_" + ItemName;

        name.setText(ItemName);
        cat.setText(ItemCat);
        price.setText(ItemPrice);
        dis.setText(ItemDis);
        Picasso.get().load(ItemUrl).fit().centerCrop().into(iv);


        back.setOnClickListener(v -> {
            String user1 = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child("Inventory").child(user1);
            reference.child(key).removeValue().addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), Feed.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                }
            });
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();

    }

}
