package com.lol.campusapp.loginAndRegister;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lol.campusapp.HomeActivity;
import com.lol.campusapp.R;
import com.lol.campusapp.SQLite.UserDataConnection;
import com.lol.campusapp.mensa.MensaDataConnection;
import com.lol.campusapp.utils.ActivityUtils;
import com.lol.campusapp.utils.DataUtils;

public class LoginActivity extends AppCompatActivity {

    private TextView loginTextView;
    private EditText loginName, loginPassword;
    private Button loginButton, noAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginTextView = findViewById(R.id.loginTextView);
        loginName = findViewById(R.id.loginName);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        noAccountButton = findViewById(R.id.noAccountButton);

        noAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noAccountOnClick();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { checkTheInput();}
        });

    }

    private void noAccountOnClick(){
        ActivityUtils.instance.switchActivity(this, RegisterActivity.class);
    }

    public void checkTheInput(){
        UserDataConnection userData = new UserDataConnection();

        String username = loginName.getText().toString();
        String password = loginPassword.getText().toString();


        if(userData.checkLogin(username, password)){
            DataUtils.instance.setCurrentUser(userData.getUser(username));
            ActivityUtils.instance.switchActivity(this, HomeActivity.class);
        }else {
            ActivityUtils.instance.wrongInput(getString(R.string.wrong_login_input), loginTextView);
        }
    }

}