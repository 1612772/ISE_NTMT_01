package com.example.nguyenhuutu.convenientmenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nguyenhuutu.convenientmenu.Fragment.Fragment_Drink;
import com.example.nguyenhuutu.convenientmenu.Fragment.Fragment_Food;
import com.example.nguyenhuutu.convenientmenu.Fragment.PagerAdapterRestaurant;

public class Manage_Menu extends Fragment {

    ViewPager viewpager;
    PagerAdapterRestaurant pagerAdapterRestaurant;
    Fragment_Food food;
    Fragment_Drink drink;
    @SuppressLint("ValidFragment")
    public Manage_Menu(String id)
    {
        food = new Fragment_Food(id);
        drink = new Fragment_Drink(id);
    }
    public  Manage_Menu()
    {
        String id = "restphuongdong";
        food = new Fragment_Food(id);
        drink = new Fragment_Drink(id);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_manage__menu, container, false);

       // Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarManage);
        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
            }
        });*/
        viewpager = (ViewPager) view.findViewById(R.id.view_pager_menu);
        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabMenu);

        pagerAdapterRestaurant = new PagerAdapterRestaurant(getChildFragmentManager());

        pagerAdapterRestaurant.AddFragment(food, "Món ăn");
        pagerAdapterRestaurant.AddFragment(drink, "Thức uống");
        viewpager.setOffscreenPageLimit(2);
        viewpager.setAdapter(pagerAdapterRestaurant);
        tabLayout.setupWithViewPager(viewpager);
        EditText search_Dish = (EditText) view.findViewById(R.id.search_Dish);
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
        ImageView imagePopupMenu = (ImageView) view.findViewById(R.id.imgAdd);
        imagePopupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Thêm món mới
            }
        });
        return view;
    }
}
