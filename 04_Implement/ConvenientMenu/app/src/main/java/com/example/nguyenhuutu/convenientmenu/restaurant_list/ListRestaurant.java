package com.example.nguyenhuutu.convenientmenu.restaurant_list;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class ListRestaurant extends BaseAdapter {

    List<Restaurant> restaurant;
    Context context;
    int inflat;
    List<Bitmap> bitmapList = new ArrayList<Bitmap>();

    public ListRestaurant(List<Restaurant> restaurant,Context context,int inflat){
        this.restaurant=restaurant;
        this.context=context;
        this.inflat=inflat;
    }

    @Override
    public int getCount() {
        return restaurant.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurant.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View row;

        row=inflater.inflate(inflat,null);

        ImageView imageRestaurant = (ImageView)row.findViewById(R.id.imageRestaurantH);

        TextView restaurantName = (TextView)row.findViewById(R.id.restaurantNameH);
        RatingBar ratingRestaurant = (RatingBar)row.findViewById(R.id.ratingRestaurantH);
        TextView ratingNumber = (TextView)row.findViewById(R.id.ratingNumberH);
        TextView addressRestaurantList=(TextView)row.findViewById(R.id.addressRestaurantListH);
        TextView phoneRestaurantList=(TextView)row.findViewById(R.id.phoneRestaurantListH);

        //Restaurant item =restaurant.get(position);
        Restaurant item=new Restaurant();

        imageRestaurant.setImageResource(R.drawable.ic_launcher_background);
        restaurantName.setText(item.getRestName());


        ratingRestaurant.setRating(item.getMaxStar().floatValue());


        ratingNumber.setText(item.getMaxStar()+" ("+item.getTotalRating()+" phiếu)");

        addressRestaurantList.setText("Địa chỉ: "+item.getRestAddresses().get(0));

        phoneRestaurantList.setText("SĐT: "+item.getRestPhone());

        return row;
    }
}
