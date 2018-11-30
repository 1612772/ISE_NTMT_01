package com.example.nguyenhuutu.convenientmenu.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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
import com.example.nguyenhuutu.convenientmenu.Restaurant_Detail;

import java.util.ArrayList;
import java.util.List;
public class ListFood extends BaseAdapter {
    Context context;
    int inflat;
    public static List<Dish> dish;
    List<Dish> search;

    public ListFood(Context context, int inflat, List<Dish> dish) {
        this.inflat = inflat;
        this.context = context;
        this.dish = dish;
        this.search = dish;
    }

    @Override
    public int getCount() {
        return search.size();
    }

    @Override
    public Object getItem(int position) {
        return search.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(inflat, null);

        ImageView imgFoodDrink = (ImageView)row.findViewById(R.id.imgFoodDrink);
        TextView tvFood = (TextView) row.findViewById(R.id.tvFood);
        CardView cvEvent = (CardView) row.findViewById(R.id.cvEvent);
        TextView tvEvent = (TextView) row.findViewById(R.id.tvEvent);
        ImageView imgPopupMenu = (ImageView) row.findViewById(R.id.imgPopupMenu);
        RatingBar rbRatingItem = (RatingBar) row.findViewById(R.id.rbRatingItem);
        TextView tvPrice = (TextView) row.findViewById(R.id.tvPrice);

        Dish item = search.get(position);

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
        imgFoodDrink.setImageBitmap(item.getDishImage(context));
        return row;
    }

    public void filter(CharSequence s) {
        String charText = String.valueOf(s).toLowerCase();

        search = new ArrayList<Dish>();
        if (charText.length() != 0) {
            for (int i=0;i<dish.size();i++)
            {
                if(Restaurant_Detail.covertToUnsigned(dish.get(i).getDishName().toLowerCase()).contains(Restaurant_Detail.covertToUnsigned(charText)))
                {
                    search.add(dish.get(i));
                }
            }
        }else
        {
            search=dish;
        }
        notifyDataSetChanged();
    }
}// CustomAdapter
