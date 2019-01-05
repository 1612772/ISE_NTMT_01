package com.example.nguyenhuutu.convenientmenu.view_information;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.helper.Helper;
import com.example.nguyenhuutu.convenientmenu.helper.RequestServer;
import com.example.nguyenhuutu.convenientmenu.helper.UserSession;
import com.example.nguyenhuutu.convenientmenu.manage_menu.add_dish.adapter.DishImageAdapter;
import com.example.nguyenhuutu.convenientmenu.view_information.activity.UpdateInformationActivity;
import com.example.nguyenhuutu.convenientmenu.view_information.adapter.ImageRestaurantAdapter;
import com.example.nguyenhuutu.convenientmenu.view_information.adapter.ListAddressAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ViewInformationFragment extends Fragment {
    private final int PICK_IMAGE_REQUEST = 71;

    TextView user_name;
    TextView user_email;

    ImageView user_avatar;
    RecyclerView image_rest_list;
    Button button_update_info;

    ListView list_address;
    TextView user_address;
    TextView user_description;
    TextView user_fb;
    TextView user_phone;

    UserSession loginedUserJson;
    Intent toUpdateInformationActivityIntent;
    GetUserInformation userInformation;

    boolean isRest;

    public ViewInformationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the view
        loginedUserJson = Helper.getLoginedUser(getActivity());
        isRest = loginedUserJson.isRest();
        userInformation = new GetUserInformation();
        userInformation.execute(loginedUserJson.getUsername(), loginedUserJson.isRest());
        View view;
        if (!isRest) {
            view = inflater.inflate(R.layout.fragment_user_view_information, container, false);

            //hold view
            user_name = (TextView) view.findViewById(R.id.user_name);
            user_avatar = (ImageView) view.findViewById(R.id.user_avatar);
            button_update_info = (Button) view.findViewById(R.id.button_update_info);
            user_email = (TextView) view.findViewById(R.id.user_email);
            user_avatar = (ImageView) view.findViewById(R.id.user_avatar);

            user_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent chooseImageFile = new Intent();
                    chooseImageFile.addCategory(Intent.CATEGORY_OPENABLE);

                    chooseImageFile.setType("image/*");
                    chooseImageFile.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(chooseImageFile, "Chọn ảnh"), PICK_IMAGE_REQUEST);
                }
            });

            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_rest_view_information, container, false);

            //hold view
            user_name = (TextView) view.findViewById(R.id.user_name);
            user_avatar = (ImageView) view.findViewById(R.id.user_avatar);
            user_email = (TextView) view.findViewById(R.id.user_email);
            user_address = (TextView) view.findViewById(R.id.user_address);
            list_address = (ListView) view.findViewById(R.id.list_address);
            user_description = (TextView) view.findViewById(R.id.user_description);
            user_fb = (TextView) view.findViewById(R.id.user_fb);
            user_phone = (TextView) view.findViewById(R.id.user_phone);
            button_update_info = (Button) view.findViewById(R.id.button_update_info);
            user_avatar = (ImageView) view.findViewById(R.id.user_avatar);
            image_rest_list = (RecyclerView) view.findViewById(R.id.image_rest_list);


            user_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent chooseImageFile = new Intent();
                    chooseImageFile.addCategory(Intent.CATEGORY_OPENABLE);

                    chooseImageFile.setType("image/*");
                    chooseImageFile.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(chooseImageFile, "Chọn ảnh"), PICK_IMAGE_REQUEST);
                }
            });

            return view;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    class GetUserInformation extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... data) {
            String result = "";
            String url = "https://convenientmenu.herokuapp.com/user/";
            if ((boolean) data[1]) {
                url += "restaurant/" + data[0];
            } else {
                url += "customer/" + data[0];
            }
            RequestServer request = new RequestServer(url, "GET");
            result = request.executeRequest();

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            updateUserInformation(result);
        }
    }

    private void putInformationToIntent(JSONObject result) {
        try {
            button_update_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toUpdateInformationActivityIntent = new Intent(getActivity(), UpdateInformationActivity.class);
                    toUpdateInformationActivityIntent.putExtra("username", loginedUserJson.getUsername());
                    toUpdateInformationActivityIntent.putExtra("isRest", isRest);
                    startActivity(toUpdateInformationActivityIntent);
                }
            });
        } catch (Exception e) {
        }
    }

    private void updateUserInformation(String data) {
        try {
            JSONObject result = new JSONObject(data);
            putInformationToIntent(result);

            if (result.getBoolean("isSuccess")) {
                if (result.getJSONObject("data").getBoolean("isRest")) {
                    user_name.setText(result.getJSONObject("data").getString("name"));
                    user_email.setText(result.getJSONObject("data").getString("email"));

                    //set adapter for list address
                    ArrayList<String> list = new ArrayList<String>();
                    JSONArray jsonArray = new JSONArray(result.getJSONObject("data").getString("addresses"));
                    if (jsonArray != null) {
                        int len = jsonArray.length();
                        for (int i = 0; i < len; i++) {
                            list.add(jsonArray.get(i).toString());
                        }
                    }
                    list_address.setAdapter(new ListAddressAdapter(getContext(), list));

                    CMDB.db
                            .collection("restaurant")
                            .document(loginedUserJson.getUsername())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            List<String> listLink = (List<String>) document.get("rest_more_images");
                                            try {
                                                ArrayList<String> listImageLink = new ArrayList<String>();
                                                JSONArray jsonArray = new JSONArray(listLink);
                                                if (jsonArray != null) {
                                                    int len = jsonArray.length();
                                                    for (int i = 0; i < len; i++) {
                                                        listImageLink.add(jsonArray.get(i).toString());
                                                    }
                                                }

                                                image_rest_list.setAdapter(new ImageRestaurantAdapter(listImageLink, getActivity()));

                                                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                                                llm.setOrientation(LinearLayoutManager.VERTICAL);
                                                image_rest_list.setLayoutManager(llm);
                                            }catch (Exception e) {}
                                        }
                                    }
                                }
                            });

                    user_description.setText(result.getJSONObject("data").getString("description"));
                    user_fb.setText(result.getJSONObject("data").getString("facebook"));
                    user_phone.setText(result.getJSONObject("data").getString("phone"));


                    CMDB.db
                            .collection("restaurant")
                            .document(loginedUserJson.getUsername())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            CMStorage.storage.child("images/restaurant/" + document.getString("rest_home_image"))
                                                    .getDownloadUrl()
                                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            try {
                                                                Glide
                                                                        .with(getActivity())
                                                                        .load(uri.toString())
                                                                        .into(user_avatar);
                                                            } catch (Exception ex) {
                                                                //load image failed
                                                            }
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                            //load database failed
                                                        }
                                                    });
                                        }
                                    }
                                }
                            });

                }
                //is normal user
                else {
                    String name = result.getJSONObject("data").getString("firstname");
                    name += " ";
                    name += result.getJSONObject("data").getString("lastname");
                    user_name.setText(name);
                    user_email.setText(result.getJSONObject("data").getString("email"));

                    CMStorage.storage.child("images/customer/" + result.getJSONObject("data").getString("avatarImageFile"))
                            .getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    try {
                                        Glide
                                                .with(getActivity())
                                                .load(uri.toString())
                                                .into(user_avatar);
                                    } catch (Exception ex) {
                                        //load image failed
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    //load database failed
                                }
                            });
                }
            } else {
                //request server failed
            }
        } catch (Exception ex) {
            //catch request server
        }
    }
}
