package com.example.nguyenhuutu.convenientmenu.Fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Fragment_Food extends Fragment {

    ListView listDish;
    public static ListFood adapter;
    public Fragment_Food(String id) {
        // Required empty public constructor
        final List<Dish> dataList = new ArrayList<Dish>();
        CMDB.db.collection("dish")
                .whereEqualTo("rest_account",id)
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
                        }
                    }
                    int mount = dataList.size();
                    for (int i = 0; i < mount; i++) {
                        final int finalI = i;
                        CMStorage.storage.child("images/dish/" + dataList.get(i).getDishHomeImage())
                                .getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        try {
                                            LoadImage loadImage = new LoadImage();
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
                    adapter = new ListFood(getActivity(), R.layout.item_menu, dataList);
                    listDish.setAdapter(adapter);
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
        return view;
    }


}
