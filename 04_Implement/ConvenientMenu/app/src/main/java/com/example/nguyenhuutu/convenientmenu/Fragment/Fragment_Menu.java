package com.example.nguyenhuutu.convenientmenu.Fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.R;

public class Fragment_Menu extends Fragment {

    ViewPager viewpager;
    PagerAdapterRestaurant pagerAdapterRestaurant;
    Fragment_Food food;
    Fragment_Drink drink ;
    public Fragment_Menu() {
        // Required empty public constructor
        food = new Fragment_Food();
        drink = new Fragment_Drink();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_menu, container, false);
        viewpager = (ViewPager) view.findViewById(R.id.view_pager_menu);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabMenu);
        tabLayout.setupWithViewPager(viewpager);
        pagerAdapterRestaurant = new PagerAdapterRestaurant(getChildFragmentManager());

        pagerAdapterRestaurant.AddFragment(food,"Món ăn");
        pagerAdapterRestaurant.AddFragment(drink,"Thức uống");

       // Toast.makeText(getContext(),pagerAdapterRestaurant.+"",Toast.LENGTH_LONG).show();
        viewpager.setAdapter(pagerAdapterRestaurant);
        return view;
    }

}