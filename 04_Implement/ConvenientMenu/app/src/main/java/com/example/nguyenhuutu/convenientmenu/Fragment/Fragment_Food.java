package com.example.nguyenhuutu.convenientmenu.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
<<<<<<< HEAD
=======
import android.support.design.widget.TabLayout;
>>>>>>> 7b142cfae920723b89c51a33d0b9094227de17eb
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.CMDB;
<<<<<<< HEAD
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.Const;
import com.example.nguyenhuutu.convenientmenu.Dish;
import com.example.nguyenhuutu.convenientmenu.Event;
import com.example.nguyenhuutu.convenientmenu.LoadImage;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.Restaurant_Detail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
=======
import com.example.nguyenhuutu.convenientmenu.Dish;
import com.example.nguyenhuutu.convenientmenu.Event;
import com.example.nguyenhuutu.convenientmenu.R;
import com.google.android.gms.tasks.OnCompleteListener;
>>>>>>> 7b142cfae920723b89c51a33d0b9094227de17eb
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Food extends Fragment {
    ListView listDish;
<<<<<<< HEAD
    public static ListDish adapter;

    public Fragment_Food() {
        // Required empty public constructor
        final List<Dish> dataList = new ArrayList<Dish>();
        CMDB.db.collection("dish")
                .whereEqualTo("rest_account",Restaurant_Detail.idRestaurant)
                .whereEqualTo("dish_type_id","DTYPE1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
=======

    public Fragment_Food() {
        CMDB.db.collection("dish").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
>>>>>>> 7b142cfae920723b89c51a33d0b9094227de17eb
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
<<<<<<< HEAD
                    int mount = dataList.size();
                    for (int i = 0; i < mount; i++) {
                        final int finalI = i;
                        CMStorage.storage.child("images/dish/" + dataList.get(i).getDishHomeImage())
                                .getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        try {
                                            LoadImage loadImage = new LoadImage(getContext());
                                            loadImage.execute(uri.toString(), finalI, Const.FOOD);
                                        } catch (Exception ex) {
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });
                    }
                    adapter = new ListDish(getActivity(), R.layout.item_menu, dataList);
                    listDish.setAdapter(adapter);
=======
>>>>>>> 7b142cfae920723b89c51a33d0b9094227de17eb
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
        listDish.setAdapter(adapter);
        listDish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Chuyển qua activity Dish
            }
        });
        return view;
    }
}
