package com.example.nguyenhuutu.convenientmenu.register;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.helper.Helper;

public class SwitchRegister extends AppCompatActivity implements View.OnClickListener {
    private ImageButton restaurantRegister;
    private ImageButton customerRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_register);

        restaurantRegister = findViewById(R.id.restaurantRegister);
        customerRegister = findViewById(R.id.customerRegister);
        restaurantRegister.setOnClickListener(this);
        customerRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restaurantRegister:
//                Intent restaurantRegisterIntent = new Intent(this, RestaurantRegister.class);
//                startActivity(restaurantRegisterIntent);
                break;
            case R.id.customerRegister:
                Intent customerRegisterIntent = new Intent(this, CustomerRegister.class);
                startActivity(customerRegisterIntent);
                Toast.makeText(this, "Customer Register", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
