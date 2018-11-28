package com.example.nguyenhuutu.convenientmenu.restaurant_list;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.Restaurant;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Fragment_Restaurant_Recently extends Fragment {
    static public List<Restaurant> dataList=new ArrayList<Restaurant>();
    ListView listRestaurant;
    ListRestaurant adapter;

    @SuppressLint("ValidFragment")
    public Fragment_Restaurant_Recently(){

    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restaurant_list_tab_recently,container,false);
        listRestaurant=view.findViewById(R.id.lvRecentRestaurant);
        adapter=new ListRestaurant(dataList,getActivity(),R.layout.list_restaurant_item);
        listRestaurant.setAdapter(adapter);

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ListRestaurant(dataList,getActivity(),R.layout.tab_restaurant_list);
        listRestaurant.setAdapter(adapter);
    }
}
