package com.example.nguyenhuutu.convenientmenu.homepage;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;

import com.example.nguyenhuutu.convenientmenu.R;

public class HomePage extends AppCompatActivity {

    private HorizontalScrollView hightRatingRestaurantList;
    private HorizontalScrollView muchViewedrestaurantList;
    private HorizontalScrollView hightRatingDishList;
    private HorizontalScrollView newEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ActionBar actionBar = getSupportActionBar();

    }
}
