package com.example.nguyenhuutu.convenientmenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.Fragment.Fragment_Comment;
import com.example.nguyenhuutu.convenientmenu.Fragment.Fragment_Drink;
import com.example.nguyenhuutu.convenientmenu.Fragment.Fragment_Event;
import com.example.nguyenhuutu.convenientmenu.Fragment.Fragment_Food;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class LoadImage extends AsyncTask<Object, String, Bitmap> {
    private final static String TAG = "AsyncTaskLoadImage";

    @Override
    protected Bitmap doInBackground(Object... params) {
        Bitmap bitmap = null;
        int i = (int) params[1];
        int id = (int) params[2];
        try {
            URL url = new URL((String) params[0]);
            bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        if (Const.EVENT == id) {
            Fragment_Event.adapter.event.get(i).setImageEvent(bitmap);
        }else if(Const.DRINK==id)
        {
            Fragment_Drink.adapter.dish.get(i).setDishImage(bitmap);
        }else if(Const.COMMENT==id)
        {
            Fragment_Comment.adapter.commentRestaurants.get(i).setImageAvatar(bitmap);
        }else if(Const.FOOD ==id)
        {
            Fragment_Food.adapter.dish.get(i).setDishImage(bitmap);
        }
        publishProgress(String.valueOf(id));
        return bitmap;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        int id = Integer.parseInt(values[0]);
        if (Const.EVENT == id) {
            Fragment_Event.adapter.notifyDataSetChanged();
        }else if(Const.DRINK==id)
        {
            Fragment_Drink.adapter.notifyDataSetChanged();
        }else if(Const.COMMENT==id)
        {
            Fragment_Comment.adapter.notifyDataSetChanged();
        }else if(Const.FOOD ==id)
        {
            Fragment_Food.adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

    }

}