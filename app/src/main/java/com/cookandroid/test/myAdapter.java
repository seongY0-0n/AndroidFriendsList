package com.cookandroid.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class myAdapter extends BaseAdapter {

    Context context = null;
    LayoutInflater layoutInflater = null;
    ArrayList<String> name, phone;

    public myAdapter(Context context, ArrayList<String> name, ArrayList<String> phone) {
        this.context = context;
        this.name = name;
        this.phone = phone;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public String getItem(int i) {
        return name.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.list_item, null);
        TextView name_tv = (TextView) view.findViewById(R.id.item_name);
        TextView phone_tv = (TextView) view.findViewById(R.id.item_phone);

        name_tv.setText(name.get(i));
        phone_tv.setText(phone.get(i));


        return view;
    }
}
