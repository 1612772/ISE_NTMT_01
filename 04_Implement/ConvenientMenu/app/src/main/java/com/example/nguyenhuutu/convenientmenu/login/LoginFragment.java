package com.example.nguyenhuutu.convenientmenu.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.R;

/**
 * Fragment for login screen
 */
public class LoginFragment extends Fragment implements View.OnClickListener{
    private final String emptyUsernameMsg = "Your username is empty";
    private final String emptyPasswordMsg = "Your password is empty";
    private final String welcomeMsg = "Hello!";
    private final String ignoreMsg = "Wrong information!";

    private UserSession userSession;
    private String uUsername;
    private String uPassword;

    //view holder
    EditText whUsername;
    EditText whPassWord;
    Button whLoginBtn;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        //hold these view
        whUsername = (EditText) view.findViewById(R.id.username);
        whPassWord = (EditText) view.findViewById(R.id.password);
        whLoginBtn = (Button) view.findViewById(R.id.loginBtn);

        //set onclick for loginBtn
        whLoginBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        boolean isFullfill = true;

        //reset color of text
        whUsername.setHintTextColor(getResources().getColor(R.color.hintColor));
        whPassWord.setHintTextColor(getResources().getColor(R.color.hintColor));

        //get values for checking
        uUsername = whUsername.getText().toString();
        uPassword = whPassWord.getText().toString();

        //check 2 fields whether empty or not
        if (uUsername.equals("")) {
            isFullfill = false;

            whUsername.setHintTextColor(getResources().getColor(R.color.red));
            whUsername.setHint(emptyUsernameMsg);
        }

        if (uPassword.equals("")) {
            isFullfill = false;

            whPassWord.setHintTextColor(getResources().getColor(R.color.red));
            whPassWord.setHint(emptyPasswordMsg);
        }

        //2 fields are filled
        if (isInformationCorrect() && isFullfill) {
            //valid login information

            //create a session for user
            userSession = new UserSession(getActivity());
            userSession.createUserLoginSession(uUsername, uPassword);

            Toast.makeText(getActivity(), welcomeMsg, Toast.LENGTH_LONG).show();
        }
        else {
            //authorization failed
            Toast.makeText(getActivity(), ignoreMsg, Toast.LENGTH_LONG).show();
        }
    }

    //to validate information from text input of user
    //if correct, login and create session
    //otherwise, do nothing
    boolean isInformationCorrect() {
        boolean result = false;

        //checking code here
        if((uUsername.equals("123") ) && (uPassword.equals("123"))) {
            result = true;
        }

        return result;
    }
}
