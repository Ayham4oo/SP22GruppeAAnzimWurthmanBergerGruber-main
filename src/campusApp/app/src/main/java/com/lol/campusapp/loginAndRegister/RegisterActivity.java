package com.lol.campusapp.loginAndRegister;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lol.campusapp.HomeActivity;
import com.lol.campusapp.R;
import com.lol.campusapp.SQLite.UserDataConnection;
import com.lol.campusapp.data.Applogin;
import com.lol.campusapp.data.User;
import com.lol.campusapp.mensa.MensaDataConnection;
import com.lol.campusapp.utils.ActivityUtils;
import com.lol.campusapp.utils.DataUtils;

public class RegisterActivity extends AppCompatActivity {

    private TextView registerTextView;
    private EditText registerName, registerPassword;
    private Button registerButton, alreadyAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerTextView = findViewById(R.id.registerTextView);
        registerName = findViewById(R.id.registerName);
        registerPassword = findViewById(R.id.registerPassword);
        registerButton = findViewById(R.id.registerButton);
        alreadyAccountButton = findViewById(R.id.alreadyAccountButton);

        alreadyAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alreadyAccountOnClick();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { checkTheInput();}
        });
    }

    private void alreadyAccountOnClick() {
        ActivityUtils.instance.switchActivity(this, LoginActivity.class);
    }

    public void checkTheInput(){
        UserDataConnection userDataConn = new UserDataConnection();
        String username = registerName.getText().toString();
        String password = registerPassword.getText().toString();

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            ActivityUtils.instance.wrongInput(getString(R.string.empty_usernameOrPassword), registerTextView);
        }else if(userDataConn.getAllLogins().containsKey(username)){
            ActivityUtils.instance.wrongInput(getString(R.string.already_used_username), registerTextView);
        }else if(username.length() < 3){
            ActivityUtils.instance.wrongInput(getString(R.string.short_username), registerTextView);
        }else if(password.length() < 8) {
            ActivityUtils.instance.wrongInput(getString(R.string.short_password), registerTextView);
        }else{
            initNewUser(username, password);
            ActivityUtils.instance.switchActivity(this, HomeActivity.class);
        }
    }

    private void initNewUser(String username, String password) {
        User user = new User();
        user.setApplogin(new Applogin(username, password));
        DataUtils.instance.setCurrentUser(user);

        UserDataConnection conn = new UserDataConnection();
        conn.insertAppLoginData(username, password);
    }
}