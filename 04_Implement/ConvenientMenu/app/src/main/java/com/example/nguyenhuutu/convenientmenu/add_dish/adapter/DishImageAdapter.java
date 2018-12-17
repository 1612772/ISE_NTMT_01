package com.example.nguyenhuutu.convenientmenu.add_dish.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.add_dish.viewholder.DishImageViewHolder;

import java.util.ArrayList;

public class DishImageAdapter extends RecyclerView.Adapter<DishImageViewHolder> {
    private ArrayList<String> dishImageLinks;
    private Activity activity;

    public DishImageAdapter(Activity _activity, ArrayList<String> _dishImageLinks) {
        this.dishImageLinks = _dishImageLinks;
        this.activity = _activity;
    }

    public void addDishImageLink(String imageLink) {
        this.dishImageLinks.add(imageLink);
    }

    public ArrayList<String> getDishImageLinks() {
        return this.dishImageLinks;
    }

    @NonNull
    @Override
    public DishImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_item, viewGroup, false);
        DishImageViewHolder dishViewHolder = new DishImageViewHolder(view);

        return dishViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DishImageViewHolder dishImageViewHolder, int position) {
        Glide
                .with(this.activity)
                .load(dishImageLinks.get(position))
                .into(dishImageViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return this.dishImageLinks.size();
    }
}
