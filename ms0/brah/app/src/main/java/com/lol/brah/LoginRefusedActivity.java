package com.lol.brah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;
import java.util.Random;

public class LoginRefusedActivity extends AppCompatActivity {
    private EditText codeEingabe;
    private TextView codeAusgabe;
    private Button tryAgineButton;
    private static int code;
    private String loginName;
    private Map<String,String> logInDaten = RegisterActivity.logInDaten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_refused);


        Intent intent = getIntent();
        loginName = intent.getStringExtra("loginNameToIncorrectPassword");
        codeEingabe = findViewById(R.id.CodeEingabe);
        tryAgineButton = findViewById(R.id.TryAgineButton);
        codeAusgabe = findViewById(R.id.CodeAusgabe);

        setCode();



        tryAgineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCode();
            }
        });
        /*






         */

    }


    public void checkCode() {
        if(codeEingabe.getText().toString().equals(String.valueOf(code))){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("registerNameToLogin", loginName);
            startActivity(intent);
        } else {
            setCode();
        }
    }

    public void setCode() {
        Random ran = new Random();
        code = ((ran.nextInt(9)+1) * 1000) +( (ran.nextInt(9)+1) * 100) +
                ((ran.nextInt(9)+1) * 10) + ((ran.nextInt(9)+1));
        codeAusgabe.setText(String.valueOf(code));
    }
}