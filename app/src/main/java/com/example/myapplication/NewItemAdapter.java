package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewItemAdapter extends RecyclerView.Adapter<NewItemAdapter.ItemViewHolder> {

    Context context;
    ArrayList<NewDisplayItem> displayItemArrayList;

    public NewItemAdapter(Context context, ArrayList<NewDisplayItem> displayItemArrayList) {
        this.context = context;
        this.displayItemArrayList = displayItemArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewItemAdapter.ItemViewHolder holder, int position) {

        NewDisplayItem newDisplayItem = displayItemArrayList.get(position);
        holder.itemname.setText(newDisplayItem.ItemName);
        holder.itemImage.setImageResource(newDisplayItem.itemImage);

    }

    @Override
    public int getItemCount() {
        return displayItemArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemname;
        ImageView itemImage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.textView4);
            itemImage = itemView.findViewById(R.id.imageView4);
        }
    }
}
