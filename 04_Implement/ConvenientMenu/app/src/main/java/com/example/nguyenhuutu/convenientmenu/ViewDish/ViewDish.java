package com.example.nguyenhuutu.convenientmenu.ViewDish;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.CommentDish;
import com.example.nguyenhuutu.convenientmenu.CommentRestaurant;
import com.example.nguyenhuutu.convenientmenu.Dish;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
public class ViewDish extends AppCompatActivity {
    private ViewPager dishImagesViewer;
    private ViewPageImageAdapter adapter;

    private List<String> listImage = new ArrayList<>();
    Dish dish = new Dish();
    private  ArrayList<CommentDish> listComment = new ArrayList<>();
    CommentDish cmtDish = new CommentDish();
    //dish information
    //dish name
    private TextView mdish_name;
    //dish rating
    private RatingBar mdish_rating;
    private TextView mdish_ratingScore;
    private TextView mdish_Vote;
    //dish price
    private TextView mdish_price;
    //dish description
    private TextView mdish_descriptionDish;
    private Button mdish_viewMore;

    //comment on view dish
    //evaluate dish
    private RatingBar mdish_evaluteDish;
    //comment form
    private EditText mdish_txtComment;
    private ImageButton mdish_btnComment;
    //list comment
    private ListView lView;
    DishCommentAdapter adapterC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dish);
        initInfoDish();
        getDataFromFireBase();
        //get comment and store in firebase
        initCommentDish();

    }

    private void getDataFromFireBase() {
        //phhviet: Lấy tạm DISH1

        try{
            CMDB.db.collection("dish").document("DISH1")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // Có dữ liệu của DISH1
                                    dish = Dish.loadDish(document.getData());

                                    listImage.add(dish.getDishHomeImage());
                                    for (String s: dish.getDishMoreImages()) {
                                        listImage.add(s);
                                    }
                                    initDishImage();
                                    mdish_name.setText(dish.getDishName());
                                    mdish_rating.setRating(dish.getMaxStar());
                                    mdish_descriptionDish.setText(dish.getDishDescription());
                                    mdish_price.setText(dish.getDishPrice().toString());
                                } else {
                                    // code here
                                }
                            } else {
                                // code here
                            }
                        }
                    });
        }
        catch(Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private void initCommentDish() {
        mdish_evaluteDish = findViewById(R.id.evaluate_dish_rating);
        mdish_txtComment = findViewById(R.id.comment_text);
        mdish_btnComment = findViewById(R.id.btn_comment_dish);
        //Send comment to database
        lView = findViewById(R.id.listComment);
        getDataComment();


    }

    private void getDataComment() {
        //thiếu điều kiện user_account.
        CMDB.db.collection("comment_dish")
                .whereEqualTo("dish_id","DISH1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                        listComment.add(CommentDish.loadCommentDish(document.getData()));
                                }

                            try {
                                adapterC = new DishCommentAdapter(getApplicationContext(),R.layout.dish_comment_item ,listComment);
                                lView.setAdapter(adapterC);
                            }catch (Exception ex) {Log.e("test1",ex.toString());}
                        }
                    }
                });
    }
    private void initInfoDish() {

        dishImagesViewer = (ViewPager) findViewById(R.id.dish_images_viewer);
        mdish_name = findViewById(R.id.dish_name);
        mdish_rating = findViewById(R.id.dish_rating);
        /*mdish_ratingScore = findViewById(R.id.rating_score);
        mdish_Vote = findViewById(R.id.vote);*/
        mdish_price = findViewById(R.id.dish_price);
        mdish_descriptionDish = findViewById(R.id.dish_description);
        //chưa xử lí
        mdish_viewMore = findViewById(R.id.btn_more);

    }
    private void initDishImage() {
        //Image dish
        //phhviet: Code view pager

        adapter = new ViewPageImageAdapter(listImage, this,dish);
        dishImagesViewer.setAdapter(adapter);
        //phhviet: use lib 'me.relex:circleindicator:1.2.2' draw circle indicator
        try {
            CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
            indicator.setViewPager(dishImagesViewer);
            adapter.registerDataSetObserver(indicator.getDataSetObserver());

        }
        catch(Exception ex) {
            Log.e("errorInit", ex.toString());
        }

    }
}