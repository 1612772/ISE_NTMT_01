package com.example.nguyenhuutu.convenientmenu.view_information.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.main.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

public class UpdateInformationActivity extends AppCompatActivity {
    Toolbar toolbar;
    String firstname;
    String lastname;
    String username;
    String email;

    TextView user_firstName;
    TextView user_lastName;
    TextView user_username;
    TextView user_email;

    Button button_update;
    boolean isDataChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);
        Intent intent = getIntent();
        if (intent != null) {
            firstname = intent.getExtras().getString("firstname");
            lastname = intent.getExtras().getString("lastname");
            username = intent.getExtras().getString("username");
            email = intent.getExtras().getString("email");
        }

        isDataChanged = false;
        user_firstName = findViewById(R.id.user_firstName);
        user_lastName = findViewById(R.id.user_lastName);
        user_username = findViewById(R.id.user_username);
        user_email = findViewById(R.id.user_email);
        button_update = (Button) findViewById(R.id.button_update_user_information);

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFirstName = user_firstName.getText().toString();
                String newLastName = user_lastName.getText().toString();
                String newEmail = user_email.getText().toString();
                String newUserName = user_username.getText().toString();

                if (!newFirstName.equals(firstname) ||
                        !newLastName.equals(lastname) ||
                        !newEmail.equals(email) ||
                        !newUserName.equals(username)) {
                    isDataChanged = true;
                }


                Map<String, Object> dataUpdate = new HashMap<>();
                dataUpdate.put("cus_email", newEmail);
                dataUpdate.put("cus_firstname", newFirstName);
                dataUpdate.put("cus_lastname", newLastName);
                dataUpdate.put("cus_account", newUserName);

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

        user_firstName.setText(firstname);
        user_lastName.setText(lastname);
        user_username.setText(username);
        user_email.setText(email);

        toolbar = (Toolbar) findViewById(R.id.toolbarUpdate);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
