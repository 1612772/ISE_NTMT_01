package com.example.nguyenhuutu.convenientmenu.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.Dish;
import com.example.nguyenhuutu.convenientmenu.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

class ListDish extends BaseAdapter {
    Context context;
    int inflat;
    List<Dish> dish;
    View row;

    public ListDish(Context context, int inflat, List<Dish> dish) {
        this.inflat = inflat;
        this.context = context;
        this.dish = dish;
    }

    @Override
    public int getCount() {
        return dish.size();
    }

    @Override
    public Object getItem(int position) {
        return dish.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(inflat, null);

        // ImageView imgEvent = (ImageView)row.findViewById(R.id.imgEvent);

        TextView tvFood = (TextView) row.findViewById(R.id.tvFood);
        CardView cvEvent = (CardView) row.findViewById(R.id.cvEvent);
        TextView tvEvent = (TextView) row.findViewById(R.id.tvEvent);
        ImageView imgPopupMenu = (ImageView) row.findViewById(R.id.imgPopupMenu);
        RatingBar rbRatingItem = (RatingBar) row.findViewById(R.id.rbRatingItem);
        TextView tvPrice = (TextView) row.findViewById(R.id.tvPrice);

        Dish item = dish.get(position);

        tvFood.setText(item.getDishName());
        if (item.getEventTypeId() < 0) //new
        {
            tvEvent.setText(Dish.NEW);
            cvEvent.setCardBackgroundColor(Dish.colorNew);
        } else if (item.getEventTypeId() > 0) //hot
        {
            tvEvent.setText(Dish.HOT);
            cvEvent.setCardBackgroundColor(Dish.colorHot);
        }else
        {
            cvEvent.setVisibility(View.INVISIBLE);
        }
        rbRatingItem.setRating(item.getMaxStar());
        tvPrice.setText("$ "+item.getDishPrice()+" đ");
        CMStorage.storage.child("images/dish/" + item.getDishHomeImage())
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        try {
                            Glide
                                    .with(context)
                                    .load(uri.toString())
                                    .into((ImageView) row.findViewById(R.id.imgFood));
                        } catch (Exception ex) {
                            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        return row;
    }
}// CustomAdapter
