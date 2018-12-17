package com.example.nguyenhuutu.convenientmenu.ViewDish;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.CommentDish;
import com.example.nguyenhuutu.convenientmenu.R;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Comment;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DishCommentAdapter extends BaseAdapter {
    Context context;
    ArrayList<CommentDish> commentDishList;
    int inflat;

    public DishCommentAdapter(Context context,int inflat,ArrayList<CommentDish> commentDishList) {
        this.context = context;
        this.commentDishList = commentDishList;
        this.inflat = inflat;
    }

    @Override
    public int getCount() {
        return commentDishList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentDishList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = inflater.inflate(inflat, null);
        View v = inflater.inflate(inflat, parent, false);
        TextView fullName = v.findViewById(R.id.user_fullname);
        RatingBar star = v.findViewById(R.id.number_of_star);
        TextView commentDate = v.findViewById(R.id.comment_date);
        TextView content = v.findViewById(R.id.comment_content);
        final ImageView avatar = v.findViewById(R.id.user_avatar);

        fullName.setText(commentDishList.get(position).getUsername());
        star.setRating(commentDishList.get(position).getScore());
        commentDate.setText(commentDishList.get(position).getCmtDishDate());
        content.setText(commentDishList.get(position).getCmtDishContent());

        CMStorage.storage.child("images/comment/avatar.png")
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context)
                                .load(uri.toString())
                                .into(avatar);
                    }
                });
        return v;
    }
}
