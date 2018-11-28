package com.example.nguyenhuutu.convenientmenu.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.CommentRestaurant;
import com.example.nguyenhuutu.convenientmenu.Event;
import com.example.nguyenhuutu.convenientmenu.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.w3c.dom.Comment;

import java.util.List;

class ListComment extends BaseAdapter {
    Context context;
    int inflat;
    View row;
    List<CommentRestaurant> commentRestaurants;

    public ListComment(Context context,int inflat, List<CommentRestaurant> commentRestaurants) {
        this.inflat = inflat;
        this.context = context;
        this.commentRestaurants = commentRestaurants;
    }

    @Override
    public int getCount() {
        return commentRestaurants.size();
    }

    @Override
    public Object getItem(int position) {
        return commentRestaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(inflat, null);

        CircularImageView imgComment = (CircularImageView)row.findViewById(R.id.imgAvatar);

        TextView tvName = (TextView)row.findViewById(R.id.tvName);
        TextView tvTimeRating = (TextView)row.findViewById(R.id.tvTimeRating);
        TextView tvComment = (TextView)row.findViewById(R.id.tvComment);
        RatingBar rbRating = (RatingBar)row.findViewById(R.id.rbRating);

        CommentRestaurant item = commentRestaurants.get(position);

        tvName.setText(item.getUserAccount());
        tvTimeRating.setText(item.getCmtRestDate());
        tvComment.setText(item.getCmtRestContent());
        rbRating.setRating(item.getCmtRestStar());
        CMStorage.storage.child("images/comment/" + item.getAvatar())
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        try{
                            Glide
                                    .with(context)
                                    .load(uri.toString())
                                    .into((ImageView) row.findViewById(R.id.imageDish));
                        }
                        catch(Exception ex) {
                            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        //imgComment.setImageResource(R.drawable.more);
        return row;
    }
}// CustomAdapter
