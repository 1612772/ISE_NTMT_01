package com.example.nguyenhuutu.convenientmenu.viewdish;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nguyenhuutu.convenientmenu.CommentDish;
import com.example.nguyenhuutu.convenientmenu.Dish;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.google.android.gms.tasks.OnCompleteListener;
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
    private Dish dish;
    private  ArrayList<CommentDish> listComment = new ArrayList<>();
    private TextView mdish_name;
    private RatingBar mdish_rating;
    private TextView mdish_price;
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

    private Toolbar viewDishToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dish);
        // Get dish id from intent
        String dishId = getDishIdFromIntent();
        // init for dish detail
        initInfoDish();
        // get dish data from database
        getDataFromFireBase(dishId);
        //get comment and store in firebase
        initCommentDish(dishId);

        viewDishToolbar = findViewById(R.id.viewDishToolbar);
        setSupportActionBar(viewDishToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        viewDishToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getDishIdFromIntent() {
        return getIntent().getExtras().get("dish_id").toString();
    }

    private void getDataFromFireBase(String dishId) {
        CMDB.db.collection("dish").document(dishId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Có dữ liệu của dish
                                dish = Dish.loadDish(document.getData());

                                listImage.add(dish.getDishHomeImage());
                                for (String s: dish.getDishMoreImages()) {
                                    listImage.add(s);
                                }

                                initDishImage();
                                mdish_name.setText(dish.getDishName());
                                mdish_rating.setRating(dish.getMaxStar());
                                mdish_descriptionDish.setText(dish.getDishDescription());
                                mdish_price.setText("$" + dish.getDishPrice().toString());
                            } else {
                                // code here
                            }
                        } else {
                            // code here
                        }
                    }
                });
    }

    private void initCommentDish(String dishId) {
        mdish_evaluteDish = findViewById(R.id.evaluate_dish_rating);
        mdish_txtComment = findViewById(R.id.comment_text);
        mdish_btnComment = findViewById(R.id.btn_comment_dish);

        lView = findViewById(R.id.listComment);
        getDataComment(dishId);
    }

    private void getDataComment(String dishId) {
        CMDB.db.collection("comment_dish")
                .whereEqualTo("dish_id", dishId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                        listComment.add(CommentDish.loadCommentDish(document.getData()));
                                }

                                adapterC = new DishCommentAdapter(getApplicationContext(),R.layout.dish_comment_item ,listComment);
                                lView.setAdapter(adapterC);
                        }
                    }
                });
    }
    private void initInfoDish() {

        dishImagesViewer = (ViewPager) findViewById(R.id.dish_images_viewer);
        mdish_name = findViewById(R.id.dish_name);
        mdish_rating = findViewById(R.id.dish_rating);
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