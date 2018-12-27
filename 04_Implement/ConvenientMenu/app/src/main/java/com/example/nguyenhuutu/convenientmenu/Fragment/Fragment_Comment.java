package com.example.nguyenhuutu.convenientmenu.Fragment;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.CommentRestaurant;
import com.example.nguyenhuutu.convenientmenu.Const;
import com.example.nguyenhuutu.convenientmenu.LoadImage;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.Restaurant;
import com.example.nguyenhuutu.convenientmenu.Restaurant_Detail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Fragment_Comment extends Fragment {

    ListView listComment;
    public static ListComment adapter;
    RatingBar rbRating;
    EditText txtComment;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    ProgressBar progressBarComment;
    public Fragment_Comment() {
        // Required empty public constructor
        final List<CommentRestaurant> dataList = new ArrayList<CommentRestaurant>();
        CMDB.db.collection("comment_restaurant")
                .whereEqualTo("rest_account", Restaurant_Detail.idRestaurant)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    dataList.add(CommentRestaurant.loadCommentRestaurant(document.getData()));

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

                                                    LoadImage loadImage = new LoadImage();
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

        txtComment = view.findViewById(R.id.txtComment);
        rbRating = view.findViewById(R.id.ratComment);
        progressBarComment = view.findViewById(R.id.progressBarComment);

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
            }
        });
        return view;
    }

    void SendComment() {
        progressBarComment.setVisibility(View.VISIBLE);
        if (rbRating.getRating() != 0 && txtComment.getText().toString()!="") {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date(); // lấy thời gian hệ thống
            final String stringDate = dateFormat.format(date);
            Map<String, Object> commentRestaurant = CommentRestaurant.createCommentRestaurantData(
                    CommentRestaurant.createCommentRestId(10),
                    txtComment.getText().toString(),
                    stringDate,
                    Restaurant_Detail.idRestaurant,
                    Restaurant_Detail.idUser,
                    rbRating.getRating(),
                    Restaurant_Detail.idUser + Restaurant_Detail.avatarUser
            );

            CMDB.db.collection("comment_restaurant").document().set(commentRestaurant)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    StorageReference mountainImagesRef = storage.getReference().child("images/comment/"+Restaurant_Detail.idUser+Restaurant_Detail.avatarUser);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Restaurant_Detail.imageAvatarUser.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainImagesRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(getContext(),"Xảy ra lỗi khi gửi bình luận",Toast.LENGTH_LONG).show();
                            progressBarComment.setVisibility(View.INVISIBLE);
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(),"Bình luận của bạn đã được gửi",Toast.LENGTH_LONG).show();

                            CommentRestaurant addComment = new CommentRestaurant(
                                    CommentRestaurant.createCommentRestId(10),
                                    txtComment.getText().toString(),
                                    stringDate,
                                    Restaurant_Detail.idRestaurant,
                                    Restaurant_Detail.idUser,
                                    rbRating.getRating(),
                                    Restaurant_Detail.idUser+Restaurant_Detail.avatarUser
                            );
                            addComment.setImageAvatar(Restaurant_Detail.imageAvatarUser);
                            adapter.commentRestaurants.add(addComment);
                            adapter.notifyDataSetChanged();

                            txtComment.setText("");
                            rbRating.setRating(0);
                            progressBarComment.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            });

        }else {
            progressBarComment.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(),"Bạn chưa đánh giá sao cho chúng tôi hoặc chưa nhập lời bình luận",Toast.LENGTH_LONG).show();
        }
    }

}