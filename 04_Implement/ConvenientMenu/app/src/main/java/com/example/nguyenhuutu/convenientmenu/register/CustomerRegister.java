package com.example.nguyenhuutu.convenientmenu.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.R;

public class CustomerRegister extends AppCompatActivity {
    private EditText lastName;
    private EditText firstName;
    private EditText account;
    private EditText email;
    private EditText password;
    private EditText againPassword;
    private CheckBox checkRule;
    private Button register;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        lastName = findViewById(R.id.lastName);
        firstName = findViewById(R.id.firstName);
        account = findViewById(R.id.account);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        againPassword = findViewById(R.id.againPassword);
        checkRule = findViewById(R.id.checkRule);
        register = findViewById(R.id.register);

        mToolbar= findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
