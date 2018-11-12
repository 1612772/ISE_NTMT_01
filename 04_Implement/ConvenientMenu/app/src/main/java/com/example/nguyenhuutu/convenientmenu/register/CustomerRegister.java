package com.example.nguyenhuutu.convenientmenu.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
    }
}
