package com.example.nguyenhuutu.convenientmenu.Fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

        final View view = inflater.inflate(R.layout.tab_menu, container, false);

        viewpager = (ViewPager) view.findViewById(R.id.view_pager_menu);
        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabMenu);

        pagerAdapterRestaurant = new PagerAdapterRestaurant(getChildFragmentManager());

        pagerAdapterRestaurant.AddFragment(food,"Món ăn");
        pagerAdapterRestaurant.AddFragment(drink,"Thức uống");
        viewpager.setOffscreenPageLimit(2);
        viewpager.setAdapter(pagerAdapterRestaurant);
        tabLayout.setupWithViewPager(viewpager);

        EditText search_Dish = (EditText)view.findViewById(R.id.search_Dish);
        search_Dish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(tabLayout.getTabAt(0).isSelected())
                {
                    Fragment_Food.adapter.filter(s);
                }else
                {
                    Fragment_Drink.adapter.filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
}