package com.example.nguyenhuutu.convenientmenu.change_password;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.helper.Helper;
import com.example.nguyenhuutu.convenientmenu.helper.UserSession;
import com.example.nguyenhuutu.convenientmenu.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordFragment extends Fragment {
    EditText user_old_password;
    EditText user_new_password;
    EditText user_confirm_new_password;
    TextView noti;
    Button button_update_info;

    UserSession loginedUserJson;
    String oldEncryptedPassword;

    String newPassword;
    String oldPassword;
    String confirmNewPassword;
    String notification;

    public ChangePasswordFragment() {
        notification = "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        loginedUserJson = Helper.getLoginedUser(getActivity());

        user_old_password = (EditText) view.findViewById(R.id.user_old_password);
        user_new_password = (EditText) view.findViewById(R.id.user_new_password);
        user_confirm_new_password = (EditText) view.findViewById(R.id.user_confirm_new_password);
        noti = (TextView) view.findViewById(R.id.noti);
        button_update_info = (Button) view.findViewById(R.id.button_update_info);



        CMDB.db
                .collection("customer")
                .document(loginedUserJson.getUsername())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                oldEncryptedPassword =document.getString("cus_password");
                            } else {
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
                                                        oldEncryptedPassword = document.getString("cus_password");
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });



        button_update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification =  "";
                oldPassword = user_old_password.getText().toString();
                oldPassword = Helper.getCompressPassword(oldPassword);

                if (oldPassword.equals(oldEncryptedPassword)) {
                    newPassword = user_new_password.getText().toString();
                    confirmNewPassword = user_confirm_new_password.getText().toString();

                    if (!newPassword.equals("") && newPassword.equals(confirmNewPassword)) {
                        if(newPassword.length() >= 8) {

                            Map<String, Object> dataUpdate = new HashMap<>();

                            if(loginedUserJson.isRest()) {
                                dataUpdate.put("rest_password", Helper.getCompressPassword(newPassword));
                                CMDB.db.collection("restaurant").document(loginedUserJson.getUsername()).update(dataUpdate)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                                Intent returnIntent = new Intent(getContext(), MainActivity.class);
                                                startActivity(returnIntent);
                                            }
                                        });
                            } else {
                                dataUpdate.put("cus_password", Helper.getCompressPassword(newPassword));
                                CMDB.db.collection("customer").document(loginedUserJson.getUsername()).update(dataUpdate)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                                Intent returnIntent = new Intent(getContext(), MainActivity.class);
                                                startActivity(returnIntent);
                                            }
                                        });
                            }
                        } else {
                            notification += notification.equals("") ? "Mật khẩu mới phải đủ 8 ký tự" : System.getProperty("line.separator") + "Mật khẩu mới phải đủ 8 ký tự" ;
                        }
                    }
                    else {
                        notification += "Mật khẩu mới không khớp nhau" ;
                    }
                }
                else {
                    notification += "Mật khẩu không đúng";
                }

                noti.setText(notification);
                noti.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}
