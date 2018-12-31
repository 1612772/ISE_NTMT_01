package com.example.nguyenhuutu.convenientmenu.search;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.nguyenhuutu.convenientmenu.Dish;
import com.example.nguyenhuutu.convenientmenu.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class ListSearchResViewAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<Restaurant> resList;

    ArrayList<Restaurant> arrRes;

    public int getCount() {
        return resList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
