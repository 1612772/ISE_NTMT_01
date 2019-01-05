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
import com.example.nguyenhuutu.convenientmenu.Restaurant;
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

//        CMDB.db
//                .collection("customer")
//                .document(loginedUserJson.getUsername())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                oldEncryptedPassword =document.getString("cus_password");
//                            } else {
//                                CMDB.db
//                                        .collection("restaurant")
//                                        .document(loginedUserJson.getUsername())
//                                        .get()
//                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                if (task.isSuccessful()) {
//                                                    DocumentSnapshot document = task.getResult();
//                                                    if (document.exists()) {
//                                                        oldEncryptedPassword = document.getString("cus_password");
//                                                    }
//                                                }
//                                            }
//                                        });
//                            }
//                        }
//                    }
//                });



        button_update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification =  "";
                oldPassword = user_old_password.getText().toString();
                oldPassword = Helper.getCompressPassword(oldPassword);
                resetNotification();

                if (loginedUserJson.isRest() == true) {
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
                                            if (oldPassword.equals(document.getData().get("rest_password").toString())) {
                                                newPassword = user_new_password.getText().toString();
                                                confirmNewPassword = user_confirm_new_password.getText().toString();

                                                if (checkNewPassword(newPassword)) {
                                                    if (checkConfirmNewPassword(newPassword, confirmNewPassword)) {
                                                        hideNotification();
                                                        Map<String, Object> dataUpdate = new HashMap<>();

                                                        dataUpdate.put("rest_password", Helper.getCompressPassword(newPassword));
                                                        CMDB.db
                                                                .collection("restaurant")
                                                                .document(loginedUserJson.getUsername())
                                                                .update(dataUpdate)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        resetForm();
                                                                        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }
                                                }
//                                                if (!newPassword.equals("") && newPassword.equals(confirmNewPassword)) {
//
//                                                    //////
//                                                    if(loginedUserJson.isRest()) {
//
//                                                    } else {
//                                                        dataUpdate.put("cus_password", Helper.getCompressPassword(newPassword));
//                                                        CMDB.db.collection("customer").document(loginedUserJson.getUsername()).update(dataUpdate)
//                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                    @Override
//                                                                    public void onSuccess(Void aVoid) {
//                                                                        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                                                                        Intent returnIntent = new Intent(getContext(), MainActivity.class);
//                                                                        startActivity(returnIntent);
//                                                                    }
//                                                                });
//                                                    }
////                                                    if(newPassword.length() >= 8) {
////
////
////                                                    }
////                                                    else {
////                                                        notification += notification.equals("") ? "Mật khẩu mới phải đủ 8 ký tự" : System.getProperty("line.separator") + "Mật khẩu mới phải đủ 8 ký tự" ;
////                                                    }
//                                                }
//                                                else {
//                                                    notification += "Mật khẩu mới không khớp nhau" ;
//                                                }
                                            }
                                            else {
                                                showNotification("Mật khẩu cũ không đúng");
                                            }
                                        } else {

                                        }
                                    } else {

                                    }
                                }
                            });
                }
                else {
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
                                            if (oldPassword.equals(document.getData().get("cus_password").toString())) {
                                                newPassword = user_new_password.getText().toString();
                                                confirmNewPassword = user_confirm_new_password.getText().toString();

                                                if (checkNewPassword(newPassword)) {
                                                    if (checkConfirmNewPassword(newPassword, confirmNewPassword)) {
                                                        hideNotification();
                                                        Map<String, Object> dataUpdate = new HashMap<>();

                                                        dataUpdate.put("cus_password", Helper.getCompressPassword(newPassword));
                                                        CMDB.db
                                                                .collection("customer")
                                                                .document(loginedUserJson.getUsername())
                                                                .update(dataUpdate)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        resetForm();
                                                                        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }
                                                }
//                                                if (!newPassword.equals("") && newPassword.equals(confirmNewPassword)) {

                                                    //////
//                                                    if(loginedUserJson.isRest()) {
//
//                                                    } else {
//                                                        dataUpdate.put("cus_password", Helper.getCompressPassword(newPassword));
//                                                        CMDB.db.collection("customer").document(loginedUserJson.getUsername()).update(dataUpdate)
//                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                    @Override
//                                                                    public void onSuccess(Void aVoid) {
//                                                                        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                                                                        Intent returnIntent = new Intent(getContext(), MainActivity.class);
//                                                                        startActivity(returnIntent);
//                                                                    }
//                                                                });
//                                                    }
//                                                    if(newPassword.length() >= 8) {
//
//
//                                                    }
//                                                    else {
//                                                        notification += notification.equals("") ? "Mật khẩu mới phải đủ 8 ký tự" : System.getProperty("line.separator") + "Mật khẩu mới phải đủ 8 ký tự" ;
//                                                    }
//                                                }
//                                                else {
//                                                    showNotification("Mật khẩu mới không khớp nhau");
//                                                }
                                            }
                                            else {
                                                showNotification("Mật khẩu cũ không đúng");
                                            }
                                        } else {

                                        }
                                    } else {

                                    }
                                }
                            });
                }


            }
        });

        return view;
    }

    private void resetForm() {
        user_old_password.setText("");
        user_new_password.setText("");
        user_confirm_new_password.setText("");
    }

    private boolean checkNewPassword(String password) {
        boolean notEmpty = true;
        boolean enoughLength = true;
        boolean trueFormat = true;

        if (Helper.checkEmptyPassword(password)) {
            showNotification("Mật khẩu mới còn trống");
            notEmpty = false;
        }
        if (!Helper.checkLengthPassword(password)) {
            showNotification("Mật khẩu có độ dài ít nhất là 8");
            enoughLength = false;
        }
        if (!Helper.checkFormatPassword(password)) {
            showNotification("Mật khẩu phải có ít nhất một ký tự in hoa và ít nhất một chữ số");
            trueFormat = false;
        }

        return (notEmpty && enoughLength && trueFormat);
    }

    private boolean checkConfirmNewPassword(String password, String confirmPassword) {
        boolean notEmpty = true;
        boolean enoughLength = true;
        boolean trueFormat = true;
        boolean matchPassword = true;

        if (Helper.checkEmptyPassword(confirmPassword)) {
            showNotification("Mật khẩu xác nhận còn trống");
            notEmpty = false;
        }
        if (!Helper.checkLengthPassword(confirmPassword)) {
            showNotification("Mật khẩu xác nhận có độ dài ít nhất là 8");
            enoughLength = false;
        }
        if (!Helper.checkFormatPassword(confirmPassword)) {
            showNotification("Mật khẩu xác nhận phải có ít nhất một ký tự in hoa và ít nhất một chữ số");
            trueFormat = false;
        }
        if (!confirmPassword.equals(password)) {
            matchPassword = false;
        }

        return (notEmpty && enoughLength && trueFormat && matchPassword);
    }

    private void resetNotification() {
        noti.setText("");
    }

    private void hideNotification() {
        resetNotification();

        noti.setVisibility(View.INVISIBLE);
    }

    private void showNotification(String message) {
        noti.setText(noti.getText() + "\n" + message);
        noti.setVisibility(View.VISIBLE);
    }
}
