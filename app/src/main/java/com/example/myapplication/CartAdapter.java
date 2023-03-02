package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final ItemViewInterface itemViewInterface;
    Context context;
    ArrayList<CartInventory> list;


    public CartAdapter(Context context, ArrayList<CartInventory> list, ItemViewInterface itemViewInterface) {
        this.context = context;
        this.list = list;
        this.itemViewInterface = itemViewInterface;
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card,parent,false);
        return new ViewHolder(view, itemViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartInventory cartInventory = list.get(position);
        if (cartInventory != null) {
            Log.d("CartAdapter", "Item name: " + cartInventory.getItem_name() + ", Image URL: " + cartInventory.getImageUrl());
            holder.name.setText(cartInventory.getItem_name());
            Picasso.get().load(cartInventory.getImageUrl()).fit().centerCrop().into(holder.image);
        } else {
            Log.e("CartAdapter", "Null inventory object at position " + position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, price,cat, user;
        ImageView image;
        public ViewHolder(@NonNull View itemView, ItemViewInterface itemViewInterface) {
            super(itemView);
            name = itemView.findViewById(R.id.textView4);
            image = itemView.findViewById(R.id.imageView4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            itemViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }


    }
}
