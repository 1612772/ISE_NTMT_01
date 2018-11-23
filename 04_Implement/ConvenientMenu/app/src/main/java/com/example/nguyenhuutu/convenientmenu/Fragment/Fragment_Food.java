package com.example.nguyenhuutu.convenientmenu.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.example.nguyenhuutu.convenientmenu.Dish;
import com.example.nguyenhuutu.convenientmenu.Event;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.Restaurant_Detail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Food extends Fragment {
    List<Dish> dataList = new ArrayList<Dish>();
    ListView listDish;
    ListDish adapter;
    public Fragment_Food() {
        // Required empty public constructor
        CMDB.db.collection("dish")
                .whereEqualTo("rest_account",Restaurant_Detail.idRestaurant)
                .whereEqualTo("dish_type_id","DTYPE1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        try {
                            dataList.add(Dish.loadDish(document.getData()));
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                    /*adapter = new ListDish(getActivity(), R.layout.item_menu, dataList);
                    listDish.setAdapter(adapter);*/

                } else {
                    Toast.makeText(getContext(), "Kết nối server thất bại", Toast.LENGTH_LONG).show();
                }
            }
        });
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
