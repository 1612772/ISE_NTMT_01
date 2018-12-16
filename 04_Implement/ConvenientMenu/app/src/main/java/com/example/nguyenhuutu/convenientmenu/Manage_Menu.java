package com.example.nguyenhuutu.convenientmenu;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nguyenhuutu.convenientmenu.Fragment.Fragment_Drink;
import com.example.nguyenhuutu.convenientmenu.Fragment.Fragment_Food;
import com.example.nguyenhuutu.convenientmenu.Fragment.PagerAdapterRestaurant;

public class Manage_Menu extends AppCompatActivity {

    ViewPager viewpager;
    PagerAdapterRestaurant pagerAdapterRestaurant;
    Fragment_Food food = new Fragment_Food("restphuongdong");
    Fragment_Drink drink = new Fragment_Drink("restphuongdong");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarManage);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        viewpager = (ViewPager) findViewById(R.id.view_pager_menu);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabMenu);

        pagerAdapterRestaurant = new PagerAdapterRestaurant(getSupportFragmentManager());

        pagerAdapterRestaurant.AddFragment(food, "Món ăn");
        pagerAdapterRestaurant.AddFragment(drink, "Thức uống");
        viewpager.setOffscreenPageLimit(2);
        viewpager.setAdapter(pagerAdapterRestaurant);
        tabLayout.setupWithViewPager(viewpager);
        EditText search_Dish = (EditText) findViewById(R.id.search_Dish);
        search_Dish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tabLayout.getTabAt(0).isSelected()) {
                    Fragment_Food.adapter.filter(s);
                } else {
                    Fragment_Drink.adapter.filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ImageView imagePopupMenu = (ImageView) findViewById(R.id.imgAdd);
        imagePopupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Manage_Menu.this,Update_Dish.class));
            }
        });
    }
}
