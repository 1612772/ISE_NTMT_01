package com.example.nguyenhuutu.convenientmenu.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.example.nguyenhuutu.convenientmenu.DialogDelete;
import com.example.nguyenhuutu.convenientmenu.Dish;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.Restaurant_Detail;
import com.example.nguyenhuutu.convenientmenu.Update_Dish;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ListDrink extends BaseAdapter {
    Context context;
    int inflat;
    public static List<Dish> dish;
    List<Dish> search;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public ListDrink(Context context, int inflat, List<Dish> dish) {
        this.inflat = inflat;
        this.context = context;
        this.dish = dish;
        this.search = dish;
    }

    @Override
    public int getCount() {
        return search.size();
    }

    @Override
    public Object getItem(int position) {
        return search.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(inflat, null);

        ImageView imgFoodDrink = (ImageView) row.findViewById(R.id.imgFoodDrink);
        TextView tvFood = (TextView) row.findViewById(R.id.tvFood);
        CardView cvEvent = (CardView) row.findViewById(R.id.cvEvent);
        TextView tvEvent = (TextView) row.findViewById(R.id.tvEvent);
        RatingBar rbRatingItem = (RatingBar) row.findViewById(R.id.rbRatingItem);
        TextView tvPrice = (TextView) row.findViewById(R.id.tvPrice);
        ImageView imgPopupmenu = (ImageView) row.findViewById(R.id.imgPopupMenu);

        imgPopupmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);

                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        int id = menuItem.getItemId();
                        if (id == R.id.delete) {
                            final DialogDelete dialogDelete = new DialogDelete(context);

                            dialogDelete.btnOK.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    /*StorageReference storageRef = storage.getReference();

                                    StorageReference desertRef = storageRef.child("images/dish/" + dish.get(position).getDishId()+"/*");
                                    Toast.makeText(context, desertRef.getPath(), Toast.LENGTH_SHORT).show();
                                    desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // File deleted successfully
                                            CMDB.db.collection("dish").document(dish.get(position).getDishId())
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(context,"TC",Toast.LENGTH_SHORT).show();
                                                            search.remove(position);
                                                            notifyDataSetChanged();
                                                        }
                                                    });
                                        }
                                    });*/
                                    dialogDelete.dialog.dismiss();
                                }
                            });
                        } else if (id == R.id.edit) {
                           // context.startActivity(new Intent(context, Update_Dish.class));
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
        Dish item = search.get(position);

        tvFood.setText(item.getDishName());
        if (item.getEventType() < 0) //new
        {
            tvEvent.setText(Dish.NEW);
            // cvEvent.setCardBackgroundColor(Color.rgb(0,0,0));
        } else if (item.getEventType() > 0) //hot
        {
            tvEvent.setText(Dish.HOT);
            // cvEvent.setCardBackgroundColor(Dish.colorHot);
        } else {
            cvEvent.setVisibility(View.INVISIBLE);
        }
        rbRatingItem.setRating(item.getMaxStar());
        tvPrice.setText("$ " + item.getDishPrice() + " đ");
        imgFoodDrink.setImageBitmap(item.getDishImage(context));
        return row;
    }

    public void filter(CharSequence s) {
        String charText = String.valueOf(s).toLowerCase();

        search = new ArrayList<Dish>();
        if (charText.length() != 0) {
            for (int i = 0; i < dish.size(); i++) {
                if (Restaurant_Detail.covertToUnsigned(dish.get(i).getDishName().toLowerCase()).contains(Restaurant_Detail.covertToUnsigned(charText))) {
                    search.add(dish.get(i));
                }
            }
        } else {
            search = dish;
        }
        notifyDataSetChanged();
    }
}// CustomAdapter
