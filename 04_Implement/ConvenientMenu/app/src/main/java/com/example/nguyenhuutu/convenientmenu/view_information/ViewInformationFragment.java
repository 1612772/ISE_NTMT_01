package com.example.nguyenhuutu.convenientmenu.view_information;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.helper.Helper;
import com.example.nguyenhuutu.convenientmenu.helper.RequestServer;
import com.example.nguyenhuutu.convenientmenu.helper.UserSession;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;


public class ViewInformationFragment extends Fragment {
    TextView user_name;
    TextView user_role;
    TextView user_username;
    TextView user_sex;
    TextView user_email;
    ImageView user_avatar;

    public ViewInformationFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @  Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the view
        View view = inflater.inflate(R.layout.fragment_view_information, container, false);

        user_name = (TextView)view.findViewById(R.id.user_name);
        user_role = (TextView)view.findViewById(R.id.user_role);
        user_username = (TextView)view.findViewById(R.id.user_username);
        user_sex = (TextView)view.findViewById(R.id.user_sex);
        user_email = (TextView)view.findViewById(R.id.user_email);
        user_avatar = (ImageView)view.findViewById(R.id.user_avatar);

        UserSession loginedUserJson = Helper.getLoginedUser(getActivity());
        GetUserInformation userInformation = new GetUserInformation();
        userInformation.execute(loginedUserJson.getUsername(), loginedUserJson.isRest());

        user_username.setText(loginedUserJson.getUsername());

        String role = loginedUserJson.isRest() ? "Chủ Nhà hàng" : "Thực khách";
        user_role.setText(role);

        return view;
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
            udpateUserInformation(result);
        }
    }

    private void udpateUserInformation(String data) {
        //is rest
        try {
            JSONObject result = new JSONObject(data);

            if (result.getBoolean("isSuccess")) {
                if (result.getJSONObject("data").getBoolean("isRest")) {
                    user_name.setText(result.getJSONObject("data").getString("name"));
                    user_email.setText(result.getJSONObject("data").getString("email"));

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
                    String name = result.getJSONObject("data").getString("lastname");
                    name += " ";
                    name += result.getJSONObject("data").getString("firstname");
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
