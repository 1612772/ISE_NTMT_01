package com.example.nguyenhuutu.convenientmenu.register.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.register.RestaurantRegister;

public class ConfirmCodeDialogFragment extends DialogFragment implements View.OnClickListener {
    private EditText code;
    private Button confirmButton;
    private RestaurantRegister activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirm_restaurant, container);
        code = view.findViewById(R.id.code);
        confirmButton = view.findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle("Mã xác nhận");
        // Show soft keyboard automatically and request focus to field
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirmButton) {
            if (!code.getText().equals("")) {
                getDialog().hide();
                ((RestaurantRegister)getActivity()).notifyConfirmedRestaurant();
            }
        }
    }
}
