package com.example.nguyenhuutu.convenientmenu.ViewDish;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nguyenhuutu.convenientmenu.Dish;
import com.example.nguyenhuutu.convenientmenu.R;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class ViewDish extends AppCompatActivity {
    private ViewPager dishImagesViewer;
    private ViewPageImageAdapter adapter;
    private List<Dish> listDish;

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


    private String[] urls = new String[] {
            "https://demonuts.com/Demonuts/SampleImages/W-03.JPG",
            "https://demonuts.com/Demonuts/SampleImages/W-08.JPG",
            "https://demonuts.com/Demonuts/SampleImages/W-10.JPG",
            "https://demonuts.com/Demonuts/SampleImages/W-13.JPG",
            "https://demonuts.com/Demonuts/SampleImages/W-17.JPG",
            "https://demonuts.com/Demonuts/SampleImages/W-21.JPG"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dish);

        initDishImage();
        initInfoDish();
        initCommentDish();
    }

    private void initCommentDish() {
        mdish_evaluteDish = findViewById(R.id.evaluate_dish_rating);
        mdish_txtComment = findViewById(R.id.comment_text);
        mdish_btnComment = findViewById(R.id.btn_comment_dish);
    }

    private void initInfoDish() {
        mdish_name = findViewById(R.id.dish_name);
        mdish_rating = findViewById(R.id.dish_rating);
        mdish_ratingScore = findViewById(R.id.rating_score);
        mdish_Vote = findViewById(R.id.vote);
        mdish_price = findViewById(R.id.dish_price);
        mdish_descriptionDish = findViewById(R.id.dish_description);
        mdish_viewMore = findViewById(R.id.btn_more);

    }
    private void initDishImage() {
        //Image dish
        //phhviet: Code view pager
        dishImagesViewer = (ViewPager) findViewById(R.id.dish_images_viewer);
        adapter = new ViewPageImageAdapter(urls,this);
        dishImagesViewer.setAdapter(adapter);
        //phhviet: use lib 'me.relex:circleindicator:1.2.2' draw circle indicator
        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(dishImagesViewer);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());
    }
}
