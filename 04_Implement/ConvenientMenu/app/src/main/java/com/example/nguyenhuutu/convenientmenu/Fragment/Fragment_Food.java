package com.example.nguyenhuutu.convenientmenu.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nguyenhuutu.convenientmenu.Dish;
import com.example.nguyenhuutu.convenientmenu.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Food extends Fragment {
    List<Dish> dataList = new ArrayList<Dish>();
    ListView listDish;
    public Fragment_Food() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_food, container, false);
        listDish = view.findViewById(R.id.list_food);
        ListDish adapter = new ListDish(getActivity(), R.layout.item_menu, dataList);
        listDish.setAdapter(adapter);
        return view;
    }

}
