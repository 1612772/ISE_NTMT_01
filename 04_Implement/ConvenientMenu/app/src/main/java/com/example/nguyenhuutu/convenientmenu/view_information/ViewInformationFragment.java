package com.example.nguyenhuutu.convenientmenu.view_information;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.helper.Helper;
import com.example.nguyenhuutu.convenientmenu.helper.RequestServer;
import com.example.nguyenhuutu.convenientmenu.helper.UserSession;
import com.example.nguyenhuutu.convenientmenu.view_information.activity.UpdateInformationActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;


public class ViewInformationFragment extends Fragment {
    TextView user_name;
    TextView user_email;

    ImageView user_avatar;
    Button button_update_info;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @  Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the view
        loginedUserJson = Helper.getLoginedUser(getActivity());
        isRest = loginedUserJson.isRest();
        userInformation = new GetUserInformation();
        userInformation.execute(loginedUserJson.getUsername(), loginedUserJson.isRest());
        View view;
        if(!isRest) {
            view = inflater.inflate(R.layout.fragment_user_view_information, container, false);

            //hold view
            user_name = (TextView)view.findViewById(R.id.user_name);
            user_avatar = (ImageView)view.findViewById(R.id.user_avatar);
            button_update_info = (Button)view.findViewById(R.id.button_update_info);
            user_email = (TextView)view.findViewById(R.id.user_email);


            return view;
        }
        else
        {
            view = inflater.inflate(R.layout.fragment_rest_view_information, container, false);

            //hold view
            user_name = (TextView)view.findViewById(R.id.user_name);
            user_avatar = (ImageView)view.findViewById(R.id.user_avatar);
            user_email = (TextView)view.findViewById(R.id.user_email);
            user_address = (TextView)view.findViewById(R.id.user_address);
            user_description = (TextView)view.findViewById(R.id.user_description);
            user_fb = (TextView)view.findViewById(R.id.user_fb);
            user_phone = (TextView)view.findViewById(R.id.user_phone);
            button_update_info = (Button)view.findViewById(R.id.button_update_info);

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
            if ((boolean)data[1]) {
                url += "restaurant/" + data[0];
            }
            else {
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
        }catch (Exception e)       {        }
    }

    private void updateUserInformation(String data) {
        try {
            JSONObject result = new JSONObject(data);
            putInformationToIntent(result);

            if (result.getBoolean("isSuccess")) {
                if (result.getJSONObject("data").getBoolean("isRest")) {
                    user_name.setText(result.getJSONObject("data").getString("name"));
                    user_email.setText(result.getJSONObject("data").getString("email"));
                    user_address.setText(result.getJSONObject("data").getString("addresses"));
                    user_description.setText(result.getJSONObject("data").getString("description"));
                    user_fb.setText(result.getJSONObject("data").getString("facebook"));
                    user_phone.setText(result.getJSONObject("data").getString("phone"));

                    CMStorage.storage.child("images/restaurant/" + result.getJSONObject("data").getString("homeImageFile"))
                            .getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    try{
                                        Glide
                                                .with(getActivity())
                                                .load(uri.toString())
                                                .into(user_avatar);
                                    }
                                    catch(Exception ex) {
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
                                    try{
                                        Glide
                                                .with(getActivity())
                                                .load(uri.toString())
                                                .into(user_avatar);
                                    }
                                    catch(Exception ex) {
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
            else {
                //request server failed
            }
        }
        catch(Exception ex){
            //catch request server
        }
    }
}
