package com.example.nguyenhuutu.convenientmenu.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.Const;
import com.example.nguyenhuutu.convenientmenu.Dish;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.helper.Helper;
import com.example.nguyenhuutu.convenientmenu.manage_menu.add_dish.AddDish;
import com.example.nguyenhuutu.convenientmenu.main.MainActivity;
import com.example.nguyenhuutu.convenientmenu.restaurant_detail.Restaurant_Detail;
import com.example.nguyenhuutu.convenientmenu.DialogDelete;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ListFood extends BaseAdapter {
    Context context;
    int inflat;
    public static List<Dish> dish;
    List<Dish> search;
    String _id;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public ListFood(Context context, int inflat, List<Dish> _dish, String id) {
        this.inflat = inflat;
        this.context = context;
//        this.dish = _dish;
        this.search = _dish;
        this._id = id;
    }

    @Override
    public int getCount() {
        return search.size();
    }

    @Override
    public Dish getItem(int position) {
        return search.get(position);
    }

    public void replace(int position, Dish _dish) {
        search.remove(position);
//        dish.remove(position);

        search.add(position, _dish);
//        dish.add(position, _dish);
    }

    public void remove(int position) {
        search.remove(position);
//        dish.remove(position);
    }

    public void add(Dish _dish) {
        search.add(_dish);
//        dish.add(_dish);
    }

    public void add(int position, Dish _dish) {
        search.add(position, _dish);
//        dish.add(position, _dish);
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

        final Dish item = search.get(position);

        final ImageView imgFoodDrink = (ImageView) row.findViewById(R.id.imgFoodDrink);
        TextView tvFood = (TextView) row.findViewById(R.id.tvFood);
        CardView cvEvent = (CardView) row.findViewById(R.id.cvEvent);
        TextView tvEvent = (TextView) row.findViewById(R.id.tvEvent);
        RatingBar rbRatingItem = (RatingBar) row.findViewById(R.id.rbRatingItem);
        TextView tvPrice = (TextView) row.findViewById(R.id.tvPrice);
        ImageView imgPopupmenu = (ImageView) row.findViewById(R.id.imgPopupMenu);

        if (_id.equals("")) {
            imgPopupmenu.setVisibility(View.INVISIBLE);
        }
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
                                    StorageReference storageRef = storage.getReference();

                                    for (int i = 0; i < item.getDishMoreImages().size(); i++) {
                                        StorageReference desertRef = storageRef.child("images/dish/" + item.getDishId() + "/" + item.getDishMoreImages().get(i));
                                        desertRef.delete();
                                    }
                                    StorageReference desertRef = storageRef.child("images/dish/" + item.getDishId() + "/" + item.getDishHomeImage());
                                    desertRef.delete();
                                    CMDB.db.collection("dish").document(item.getDishId())
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    search.remove(position);
                                                    notifyDataSetChanged();
                                                    Toast.makeText(context, "Xóa món ăn thành công", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    dialogDelete.dialog.dismiss();
                                }
                            });
                        } else if (id == R.id.edit) {
                            Intent editDishIntent = new Intent(context, AddDish.class);
                            editDishIntent.putExtra("edit_dish", true);
                            editDishIntent.putExtra("dish_id", item.getDishId());
                            editDishIntent.putExtra("dish_name", item.getDishName());
                            editDishIntent.putExtra("dish_create_date", item.getCreateDateRaw());
                            editDishIntent.putExtra("max_star", item.getMaxStar());
                            editDishIntent.putExtra("rest_account", item.getRestAccount());
                            editDishIntent.putExtra("dish_type_id", item.getDishTypeId());
                            editDishIntent.putExtra("dish_price", item.getDishPrice());
                            editDishIntent.putExtra("dish_description", item.getDishDescription());
                            editDishIntent.putExtra("dish_home_image", item.getDishHomeImage());
                            editDishIntent.putCharSequenceArrayListExtra("dish_more_images", new ArrayList<CharSequence>(item.getDishMoreImages()));

                            ((MainActivity) context).startActivityForResult(editDishIntent, Const.EDIT_DISH);
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });


        tvFood.setText(item.getDishName());
        if (item.getEventType() < 0) //new
        {
            tvEvent.setText(Dish.NEW);
            //cvEvent.setCardBackgroundColor(Dish.colorNew);
        } else if (item.getEventType() > 0) //hot
        {
            tvEvent.setText(Dish.HOT);
            //cvEvent.setCardBackgroundColor(Dish.colorHot);
        } else {
            cvEvent.setVisibility(View.INVISIBLE);
        }
        rbRatingItem.setRating(item.getMaxStar());
        tvPrice.setText("$ " + item.getDishPrice() + " vnđ");
        if (!item.getDishHomeImage().equals("")) {
//            imgFoodDrink.setImageBitmap(item.getDishImage(context));
            CMStorage.storage.child(Helper.getDishImageFilePath(item.getDishId(), item.getDishHomeImage()))
                    .getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide
                                    .with(context)
                                    .load(uri.toString())
                                    .into(imgFoodDrink);
                        }
                    });
        }

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
