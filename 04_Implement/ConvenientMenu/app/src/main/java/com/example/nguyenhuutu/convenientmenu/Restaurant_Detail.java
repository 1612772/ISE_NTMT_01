package com.example.nguyenhuutu.convenientmenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.example.nguyenhuutu.convenientmenu.Fragment.Fragment_Comment;
import com.example.nguyenhuutu.convenientmenu.Fragment.Fragment_Event;
import com.example.nguyenhuutu.convenientmenu.Fragment.Fragment_Menu;
import com.example.nguyenhuutu.convenientmenu.Fragment.PagerAdapterRestaurant;


public class Restaurant_Detail extends AppCompatActivity {
    ViewPager viewpager;
    Fragment_Event event = new Fragment_Event();
    Fragment_Menu menu = new Fragment_Menu();
    Fragment_Comment comment = new Fragment_Comment();
    PagerAdapterRestaurant pagerAdapterRestaurant;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_detail);

        viewpager = (ViewPager) findViewById(R.id.view_pager_restaurant_detail);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabRestaurantDetail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarRestaurant);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tabLayout.setupWithViewPager(viewpager);
        pagerAdapterRestaurant = new PagerAdapterRestaurant(getSupportFragmentManager());

        pagerAdapterRestaurant.AddFragment(event,"Sự kiện");
        pagerAdapterRestaurant.AddFragment(menu,"Menu");
        pagerAdapterRestaurant.AddFragment(comment,"Bình luận");

        viewpager.setAdapter(pagerAdapterRestaurant);

    }

}
