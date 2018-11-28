package com.example.nguyenhuutu.convenientmenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyenhuutu.convenientmenu.Fragment.PagerAdapterRestaurant;
import com.example.nguyenhuutu.convenientmenu.restaurant_list.Fragment_Restaurant;
import com.example.nguyenhuutu.convenientmenu.restaurant_list.Fragment_Restaurant_Recently;
import com.example.nguyenhuutu.convenientmenu.R;

public class Restaurant_List extends Fragment {
    ViewPager viewPager;
    PagerAdapterRestaurant pagerAdapterRestaurant;
    Fragment_Restaurant resAll;
    Fragment_Restaurant_Recently resRecent;
    TabLayout tabLayout;

    public Restaurant_List(){
        // Required empty public constructor
        resAll= new Fragment_Restaurant();
        resRecent= new Fragment_Restaurant_Recently();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_restaurant_list,container,false);

        viewPager=(ViewPager)view.findViewById(R.id.viewPagerRestaurantList);
        tabLayout=(TabLayout)view.findViewById(R.id.tabRestaurantList);

        pagerAdapterRestaurant = new PagerAdapterRestaurant(getChildFragmentManager());

        pagerAdapterRestaurant.AddFragment(resAll,"Mashu");
        pagerAdapterRestaurant.AddFragment(resRecent,"kyrielight");

        viewPager.setAdapter(pagerAdapterRestaurant);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerAdapterRestaurant.getItem(tab.getPosition()).onStart();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
