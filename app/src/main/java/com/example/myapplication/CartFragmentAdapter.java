package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CartFragmentAdapter extends BaseAdapter {

    Context context;
    String[] itemName;
    int[] image;

    LayoutInflater inflater;

    public CartFragmentAdapter(Context context, String[] itemName, int[] image) {
        this.context = context;
        this.itemName = itemName;
        this.image = image;
    }

    @Override
    public int getCount() {
        return itemName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)    {
            convertView = inflater.inflate(R.layout.listed_item_card, null);
        }
        ImageView imageView = convertView.findViewById(R.id.imageView3);
        TextView textView = convertView.findViewById(R.id.textView3);

        imageView.setImageResource(image[position]);
        textView.setText(itemName[position]);

        return convertView;
    }
}
