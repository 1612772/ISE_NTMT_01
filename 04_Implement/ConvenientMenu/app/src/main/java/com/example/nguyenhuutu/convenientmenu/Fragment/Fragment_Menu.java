package com.example.nguyenhuutu.convenientmenu.Fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.Const;
import com.example.nguyenhuutu.convenientmenu.Dish;
import com.example.nguyenhuutu.convenientmenu.LoadImage;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.Restaurant_Detail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Fragment_Menu extends Fragment {

    ViewPager viewpager;
    PagerAdapterRestaurant pagerAdapterRestaurant;
    Fragment_Food food;
    Fragment_Drink drink;
    TabLayout tabLayout;
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

        pagerAdapterRestaurant = new PagerAdapterRestaurant(getChildFragmentManager());

        pagerAdapterRestaurant.AddFragment(food,"Món ăn");
        pagerAdapterRestaurant.AddFragment(drink,"Thức uống");
        viewpager.setOffscreenPageLimit(2);
        viewpager.setAdapter(pagerAdapterRestaurant);
        tabLayout.setupWithViewPager(viewpager);

        return view;
    }
}