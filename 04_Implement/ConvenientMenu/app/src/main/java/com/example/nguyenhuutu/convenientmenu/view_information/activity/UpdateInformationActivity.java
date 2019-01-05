package com.example.nguyenhuutu.convenientmenu.view_information.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.helper.RequestServer;
import com.example.nguyenhuutu.convenientmenu.helper.UserSession;
import com.example.nguyenhuutu.convenientmenu.main.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateInformationActivity extends AppCompatActivity {
    Toolbar toolbar;
    String username;
    String name;
    String description;
    String fb;
    String address;
    String phone;

    String firstName = "";
    String lastName = "";
    String email = "";

    EditText user_firstName;
    EditText user_lastName;
    EditText user_email;

    EditText user_name;
    EditText user_address;
    EditText user_description;
    EditText user_fb;
    EditText user_phone;

    Button button_update;
    boolean isDataChanged;
    boolean isRest;
    GetUserInformation userInformation;
    String remoteData;

    public UpdateInformationActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null) {
            username = intent.getExtras().getString("username");
            isRest = intent.getExtras().getBoolean("isRest");
            userInformation = new GetUserInformation();
            userInformation.execute(username, isRest);
        }


        if (!isRest) {
            setContentView(R.layout.activity_user_update_information);
            isDataChanged = false;

            user_firstName = (EditText) findViewById(R.id.user_firstName);
            user_lastName = (EditText) findViewById(R.id.user_lastName);
            user_email = (EditText) findViewById(R.id.user_email);
            button_update = (Button) findViewById(R.id.button_update_user_information);




        } else {
            setContentView(R.layout.activity_rest_update_information);
            isDataChanged = false;

            user_name = (EditText) findViewById(R.id.user_name);
            user_email = (EditText) findViewById(R.id.user_email);
            user_address = (EditText) findViewById(R.id.user_address);
            user_description = (EditText) findViewById(R.id.user_description);
            user_fb = (EditText) findViewById(R.id.user_fb);
            user_phone = (EditText) findViewById(R.id.user_phone);
            button_update = (Button) findViewById(R.id.button_update_user_information);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbarUpdate);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private class GetUserInformation extends AsyncTask<Object, Void, String> {

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
            remoteData = result;

            if (!isRest) {

                try {
                    JSONObject data = new JSONObject(remoteData);
                    firstName = data.getJSONObject("data").getString("firstname");
                    lastName = data.getJSONObject("data").getString("lastname");
                    email = data.getJSONObject("data").getString("email");

                    user_firstName.setText(firstName);
                    user_lastName.setText(lastName);
                    user_email.setText(email);
                } catch (Exception e) {
                }

                button_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newFirstName = user_firstName.getText().toString();
                        String newLastName = user_lastName.getText().toString();
                        String newEmail = user_email.getText().toString();

                        if (!newFirstName.equals(firstName) ||
                                !newLastName.equals(lastName) ||
                                !newEmail.equals(email)) {
                            isDataChanged = true;
                        }

                        Map<String, Object> dataUpdate = new HashMap<>();
                        dataUpdate.put("cus_email", newEmail);
                        dataUpdate.put("cus_firstname", newFirstName);
                        dataUpdate.put("cus_lastname", newLastName);
                        if (isDataChanged) {
                            CMDB.db.collection("customer").document(username).update(dataUpdate)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                                            Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(returnIntent);
                                        }
                                    });
                        }
                    }
                });
            }
            else {
                try {
                    JSONObject data = new JSONObject(remoteData);
                    name = data.getJSONObject("data").getString("name");
                    address = data.getJSONObject("data").getString("addresses");
                    email = data.getJSONObject("data").getString("email");
                    description = data.getJSONObject("data").getString("description");
                    phone = data.getJSONObject("data").getString("phone");
                    fb = data.getJSONObject("data").getString("facebook");


                    user_name.setText(name);
                    user_email.setText(email);
                    user_description.setText(description);
                    user_phone.setText(phone);
                    user_fb.setText(fb);
                } catch (Exception e) {
                }

                    button_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newName = user_name.getText().toString();
                        String newEmail = user_email.getText().toString();
                        //String newAddress = user_address.getText().toString();
                        String newDescription = user_description.getText().toString();
                        String newFb = user_fb.getText().toString();
                        String newPhone = user_phone.getText().toString();

                            if (!name.equals(newName) ||
                                    !newEmail.equals(email) ||
                                    !newDescription.equals(description) ||
                                    !newFb.equals(fb) ||
                                    !newPhone.equals(phone)) {
                                isDataChanged = true;
                            }

                            Map<String, Object> dataUpdate = new HashMap<>();
                            dataUpdate.put("rest_description", newDescription);
                            dataUpdate.put("rest_email", newEmail);
                            dataUpdate.put("rest_facebook", newFb);
                            dataUpdate.put("rest_phone", newPhone);
                            dataUpdate.put("rest_name", newName);

                            if (isDataChanged) {
                                CMDB.db.collection("restaurant").document(username).update(dataUpdate)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                                                Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(returnIntent);
                                            }
                                        });
                            }

                    }
                });
            }
        }
    }
}
