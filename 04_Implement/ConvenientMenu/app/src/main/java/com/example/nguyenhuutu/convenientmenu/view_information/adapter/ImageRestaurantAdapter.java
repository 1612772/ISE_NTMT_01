package com.example.nguyenhuutu.convenientmenu.view_information.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.R;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class ImageRestaurantAdapter extends
        RecyclerView.Adapter<ImageRestaurantAdapter.MyViewHolder> {

    private List<String> mList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image_rest_list_item);
        }
    }

    public ImageRestaurantAdapter(List<String> countryList, Context ctx) {
        mList = countryList;
        mContext = ctx;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String c = mList.get(position);
        CMStorage.storage.child("images/restaurant/" + c)
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        try {
                            Glide
                                    .with(mContext)
                                    .load(uri.toString())
                                    .into(holder.image);
                        } catch (Exception ex) {
                            //load image failed
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_rest,parent, false);
        return new MyViewHolder(v);
    }
}