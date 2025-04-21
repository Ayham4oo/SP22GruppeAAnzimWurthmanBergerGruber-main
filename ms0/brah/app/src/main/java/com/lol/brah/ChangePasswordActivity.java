package com.lol.brah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextView loggedinText;
    private Map<String,String> logInDaten = RegisterActivity.logInDaten;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        loggedinText = findViewById(R.id.LoggedinText);

        Intent intent = getIntent();
        userName = intent.getStringExtra("loginName");
        loggedinText.setText("Welcome " + userName + " you are logged in");
        Button changePassword = findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executePWChange();
            }
        });
            
        }
    public void executePWChange(){
        EditText newPassword = findViewById(R.id.editTextTextPassword);
        RegisterActivity.changePassword(userName, newPassword.getText().toString());
        System.out.println(logInDaten.get(userName));
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("registerNameToLogin", userName);
        startActivity(i);
    }

}