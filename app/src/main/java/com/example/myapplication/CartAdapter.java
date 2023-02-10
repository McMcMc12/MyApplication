package com.example.myapplication;

import android.content.Context;
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
    ArrayList<Cart> list;


    public CartAdapter(Context context, ArrayList<Cart> list, ItemViewInterface itemViewInterface) {
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
        Cart cart = list.get(position);
        holder.name.setText(cart.getItem_name());
        Picasso.get().load(cart.getImageUrl()).fit().centerCrop().into(holder.image);

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
