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

public class MyInventoryDialog extends AppCompatDialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_dialog, null);

        TextView name = view.findViewById(R.id.textView11);
        TextView cat = view.findViewById(R.id.textView18);
        TextView price = view.findViewById(R.id.textView19);
        TextView dis = view.findViewById(R.id.textView20);
        ImageView iv = view.findViewById(R.id.imageView8);
        Button delete = view.findViewById(R.id.button6);
        Button update = view.findViewById(R.id.button7);

        Bundle bundle = getArguments();

        delete.setText("Delete");
        update.setText("Update");

        String ItemName = bundle.getString("Name");
        String ItemCat = bundle.getString("Category");
        String ItemPrice = bundle.getString("Price");
        String ItemDis = bundle.getString("Dis");
        String ItemUrl = bundle.getString("Url");
        String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        String key = user + "_" + ItemCat + "_" + ItemName;

        name.setText(ItemName);
        cat.setText(ItemCat);
        price.setText(ItemPrice);
        dis.setText(ItemDis);
        Picasso.get().load(ItemUrl).fit().centerCrop().into(iv);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(user).child("Inventory");
                reference.child(key).removeValue().addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), Feed.class);
                        startActivity(intent);
                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(), UpdateItemForm.class);
                intent.putExtra("name", ItemName);
                intent.putExtra("cat", ItemCat);
                intent.putExtra("dis", ItemDis);
                intent.putExtra("price", ItemPrice);
                intent.putExtra("url", ItemUrl);
                startActivity(intent);

            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }

}
