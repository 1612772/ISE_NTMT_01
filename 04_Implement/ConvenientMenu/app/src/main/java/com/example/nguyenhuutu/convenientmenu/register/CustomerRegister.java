package com.example.nguyenhuutu.convenientmenu.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.Customer;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.helper.Helper;
import com.example.nguyenhuutu.convenientmenu.main.MainActivity;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerRegister extends AppCompatActivity implements View.OnClickListener {
    private final int NumberOfEditBox = 7;
    private TextInputEditText lastName;
    private TextInputEditText firstName;
    private TextInputEditText account;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText againPassword;
    private TextInputLayout lastNameLayout;
    private TextInputLayout firstNameLayout;
    private TextInputLayout accountLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout againPasswordLayout;
    private CheckBox checkRule;
    private Button registerButton;
    private Toolbar mToolbar;
    private boolean lastNameOk = false;
    private boolean firstNameOk = false;
    private boolean accountOk = false;
    private boolean emailOk = false;
    private boolean passwordOk = false;
    private boolean againPasswordOk = false;
    private boolean checkRuleOk = false;

    private ProgressDialog process;

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
        registerButton = findViewById(R.id.register);

        lastNameLayout = findViewById(R.id.textInputLastName);
        firstNameLayout = findViewById(R.id.textInputFirstName);
        accountLayout = findViewById(R.id.textInputAccount);
        emailLayout = findViewById(R.id.textInputEmail);
        passwordLayout = findViewById(R.id.textInputPassword);
        againPasswordLayout = findViewById(R.id.textInputAgainPassword);
        setTextChangeForAccount();
        setTextChangeForLastName();
        setTextChangeForFirstName();
        setTextChangeForEmail();
        setTextChangeForPassword();
        setTextChangeForAgainPassword();

        process = new ProgressDialog(this);
        process.setMessage("Registering. Please Wait for a while.");
        process.setProgressStyle(ProgressDialog.STYLE_SPINNER);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                if (checkInfo()) {
                    String lastNameStr = lastName.getText().toString();
                    String firstNameStr = firstName.getText().toString();
                    String accountStr = account.getText().toString();
                    String emailStr = email.getText().toString();
                    String passwordStr = password.getText().toString();
                    String againPasswordStr = againPassword.getText().toString();

                    Customer customer = new Customer(accountStr, passwordStr, emailStr, lastNameStr, firstNameStr);
                    customer.register(this);

                    process.show();

                }
                break;
            case R.id.checkRule:
                if (((CheckBox) v).isChecked()) {
                    checkRuleOk = true;
                }
                else {
                    checkRuleOk = false;
                }
                break;
        }
    }

    public void callbackRegister(boolean status) {
        if (status) {
            process.hide();
            Intent homepageIntent = new Intent(this, MainActivity.class);
            startActivity(homepageIntent);
        }
        else {

        }
    }

    private void setTextChangeForLastName(){
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = lastName.getText().toString();

                if (str.isEmpty()) {
                    lastNameOk = false;
                    lastNameLayout.setError("Không được để trống");
                }
                else if (str.matches("^[a-zA-Z]+$")) {
                    lastNameOk = true;
                    lastNameLayout.setError(null);
                }
                else {
                    lastNameOk = false;
                    lastNameLayout.setError("Không được chứa số và khoảng trắng");
                }
            }
        });
    }
    private void setTextChangeForFirstName(){
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = firstName.getText().toString();

                if (str.isEmpty()) {
                    firstNameOk = false;
                    firstNameLayout.setError("Không được để trống");
                }
                else if (str.matches("^[a-zA-Z]+$")) {
                    firstNameOk = true;
                    firstNameLayout.setError(null);
                }
                else {
                    firstNameOk = false;
                    firstNameLayout.setError("Không được chứa số và khoảng trắng");
                }
            }
        });
    }
    private void setTextChangeForAccount(){
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = account.getText().toString();

                if (str.isEmpty()) {
                    accountOk = false;
                    accountLayout.setError("Không được để trống");
                }
                else if (str.matches("^[a-zA-Z0-9]+$")) {
                    if (Helper.checkAvailableCustomerAccount(CustomerRegister.this, str)) {
                        accountOk = true;
                        accountLayout.setError(null);
                    }
                    else {
                        accountOk = false;
                        accountLayout.setError("Tên tài khoản đã tồn tại");
                    }
                }
                else {
                    accountOk = false;
                    accountLayout.setError("Không được khoảng trắng và ký tự đặc biệt");
                }
            }
        });
    }
    private void setTextChangeForEmail(){
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = email.getText().toString();

                if (str.isEmpty()) {
                    emailOk = false;
                    emailLayout.setError("Không được để trống");
                }
                else if (str.matches("^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$")) {
                    emailOk = true;
                    emailLayout.setError(null);
                }
                else {
                    emailOk = false;
                    emailLayout.setError("Email không đúng");
                }
            }
        });
    }
    private void setTextChangeForPassword(){
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = password.getText().toString();
                Pattern patternUpper = Pattern.compile("[A-Z]+");
                Pattern patternNumber = Pattern.compile("[0-9]+");
                Matcher matcher;

                if (str.isEmpty()) {
                    passwordOk = false;
                    passwordLayout.setError("Không được để trống");
                }
                else if (!patternUpper.matcher(str).find() || !patternNumber.matcher(str).find()) {
                    passwordOk = false;
                    passwordLayout.setError("Mật khẩu phải chứa ký tự in hoa và số");
                }
                else {
                    passwordOk = true;
                    passwordLayout.setError(null);
                }
            }
        });
    }
    private void setTextChangeForAgainPassword() {
        againPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = againPassword.getText().toString();
                Pattern patternUpper = Pattern.compile("[A-Z]+");
                Pattern patternNumber = Pattern.compile("[0-9]+");
                Matcher matcher;

                if (str.isEmpty()) {
                    againPasswordOk = false;
                    againPasswordLayout.setError("Không được để trống");
                }
                else if (!patternUpper.matcher(str).find() || !patternNumber.matcher(str).find()) {
                    againPasswordOk = false;
                    againPasswordLayout.setError("Mật khẩu phải chứa ký tự in hoa và số");
                }
                else if (str.equals(new String(password.getText().toString()))) {
                    againPasswordOk = true;
                    againPasswordLayout.setError(null);
                }
                else {
                    Toast.makeText(CustomerRegister.this, str + "\n" + password.getText(), Toast.LENGTH_SHORT).show();
                    againPasswordOk = false;
                    againPasswordLayout.setError("Mật khẩu không trùng khớp");
                }
            }
        });
    }

    private boolean checkInfo() {
        if (lastNameOk && firstNameOk && accountOk && passwordOk && againPasswordOk && emailOk && checkRuleOk) {
            return true;
        }
        else {
            return false;
        }
    }
}
