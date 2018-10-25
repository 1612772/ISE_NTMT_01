package com.example.nguyenhuutu.convenientmenu.homepage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class HightRatingRestaurantFragment extends Fragment {
    private HomePage homePage;
    private LinearLayout listContent;
    private List<Restaurant> dataList;
    private static Integer hightRatingRestaurantNumber = 10;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        homePage = (HomePage)getActivity();
        dataList = new ArrayList<Restaurant>();

        Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
    }
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listContent = (LinearLayout)inflater.inflate(R.layout.hight_rating_restaurant_fragment, null);

        CMDB.db.collection("restaurant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Restaurant rest;
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                try{
//                                    rest = Restaurant.loadRestaurant(document.getData());
//                                }
//                                catch(Exception ex){
//                                    Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
//                                }
                                try {
                                    dataList.add(Restaurant.loadRestaurant(document.getData()));
                                }
                                catch (Exception ex){
                                    Toast.makeText(getActivity(), "01: " + ex.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                            Toast.makeText(getActivity(), "yyy", Toast.LENGTH_SHORT).show();
                            //sortCommentRestaurant(dataList);

                            try {
                                final LinearLayout item = (LinearLayout) inflater.inflate(R.layout.homepage_restaurant_item, null);

                                Integer index;
                                for (index = 0; index < hightRatingRestaurantNumber; index++) {
                                    rest = dataList.get(index);
                                    ((TextView) item.findViewById(R.id.restaurantName)).setText(rest.getRestName());
                                    ((RatingBar) item.findViewById(R.id.ratingRestaurant)).setRating(((Number) rest.getMaxStar()).floatValue());

                                    CMDB.db.collection("comment_restaurant")
                                            .whereEqualTo("cmt_rest_star", rest.getMaxStar())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        ((TextView) item.findViewById(R.id.ratingNumber)).setText("(" + task.getResult().getDocuments().size() + " phiếu)");
                                                    } else {

                                                    }
                                                }
                                            });
                                    ((TextView) item.findViewById(R.id.addressRestaurant)).setText(rest.getRestAddresses().get(0));

                                    ((LinearLayout) listContent.findViewById(R.id.hightRatingRestaurantList)).addView(item);
                                }
                            }
                            catch(Exception ex){
                                Toast.makeText(getActivity(), "02" + ex.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                        else {

                        }
                    }
                });

        return listContent;
    }

    private void sortCommentRestaurant(List<Restaurant> restList){

    }
}
