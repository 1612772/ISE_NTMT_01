package com.example.nguyenhuutu.convenientmenu.Fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
<<<<<<< HEAD
import android.widget.EditText;
=======
>>>>>>> 7b142cfae920723b89c51a33d0b9094227de17eb
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.CommentRestaurant;
import com.example.nguyenhuutu.convenientmenu.Const;
import com.example.nguyenhuutu.convenientmenu.LoadImage;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.Restaurant_Detail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Comment extends Fragment {
    ListView listComment;
<<<<<<< HEAD
    public static ListComment adapter;

    AppCompatRatingBar rbRating;
    EditText txtComment;
=======

>>>>>>> 7b142cfae920723b89c51a33d0b9094227de17eb
    public Fragment_Comment() {
        final List<CommentRestaurant> dataList = new ArrayList<CommentRestaurant>();
        CMDB.db.collection("comment_restaurant")
                .whereEqualTo("rest_account", Restaurant_Detail.idRestaurant)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

<<<<<<< HEAD
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    dataList.add(CommentRestaurant.loadCommentRestaurant(document.getData()));
=======
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        try {
                            dataList.add(CommentRestaurant.loadCommentRestaurant(document.getData()));
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Kết nối server thất bại", Toast.LENGTH_LONG).show();
                }
            }
        });
>>>>>>> 7b142cfae920723b89c51a33d0b9094227de17eb

                                } catch (Exception ex) {
                                    Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                            int mount = dataList.size();
                            for (int i = 0; i < mount; i++) {
                                final int finalI = i;

                                CMStorage.storage.child("images/comment/" + dataList.get(i).getAvatar())
                                        .getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                try {
                                                    LoadImage loadImage = new LoadImage(getContext());
                                                    loadImage.execute(uri.toString(), finalI, Const.COMMENT);
                                                } catch (Exception ex) {
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                            }
                                        });
                            }
                            adapter = new ListComment(getActivity(), R.layout.item_comment, dataList);
                            listComment.setAdapter(adapter);
                        } else {
                            Toast.makeText(getContext(), "Kết nối server thất bại", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_comments, container, false);
        listComment = view.findViewById(R.id.lvComment);
        listComment.setAdapter(adapter);
<<<<<<< HEAD
        txtComment = view.findViewById(R.id.txtComment);
        rbRating = view.findViewById(R.id.rbRating);
        txtComment.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            SendComment();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        txtComment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (txtComment.getRight() - txtComment.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        SendComment();
                        return true;
                    }
                }
                return false;
            }
        });
        listComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Nhấp vào item comment
=======
        listComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //chuyển qua activity bình luận
>>>>>>> 7b142cfae920723b89c51a33d0b9094227de17eb
            }
        });
        return view;
    }

    void SendComment() {
      // CommentRestaurant commentRestaurant = CommentRestaurant.createCommentRestaurantData(CommentRestaurant.createCommentRestId())
    }

}