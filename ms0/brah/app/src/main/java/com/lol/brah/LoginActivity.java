package com.lol.brah;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText loginName;
    private EditText loginPassword;
    private TextView loginText;
    private TextView noAccount;
    private Button loginButton;
    private Map<String,String> logInDaten = RegisterActivity.logInDaten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginName = findViewById(R.id.LoginName);
        loginPassword = findViewById(R.id.LoginPassword);
        loginText = findViewById(R.id.LoginTextView);
        loginButton = findViewById(R.id.LoginButton);

        Intent intent = getIntent();
        String data = intent.getStringExtra("registerNameToLogin");
        String data2 = intent.getStringExtra("registerNameToLogin");
        if(data.equals("")) {
            loginName.setText(data2);
        } else if (data2.equals("")) {
            loginName.setText(data);
        }

        loginName.setText(data);



        noAccount = findViewById(R.id.DontHaveAccount_Text);
        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dontHaveAccount();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton();
            }
        });



    }

    public void loginButton(){
        String name = loginName.getText().toString();
        if( !(TextUtils.isEmpty(name) &&
                TextUtils.isEmpty(loginPassword.getText())) ) {
            if (characterOnly(name)) {
                if(! logInDaten.containsKey(name)){
                    loginText.setTextSize(15);
                    loginText.setTextColor(Color.parseColor("#B51010"));
                    loginText.setText("There is no Account with this name!");

                } else if (! logInDaten.get(name).equals( loginPassword.getText().toString() )) {
                    Log.d("incorrectPassword: ","in the if");
                    Intent intent = new Intent(this, LoginRefusedActivity.class);
                    intent.putExtra("loginNameToIncorrectPassword", loginName.getText().toString());
                    startActivity(intent);
                } else {
                    loginSuccessful();
                }


            } else {
                loginText.setTextSize(15);
                loginText.setTextColor(Color.parseColor("#B51010"));
                loginText.setText("Please enter your name with digits only!");
            }
        } else {
            loginText.setTextSize(15);
            loginText.setTextColor(Color.parseColor("#B51010"));
            loginText.setText("Please enter your name and password!");
        }
    }

    private void loginSuccessful() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        intent.putExtra("loginName", loginName.getText().toString());
        startActivity(intent);
    }

    public void dontHaveAccount() {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("loginNameToRegister", loginName.getText().toString());
        startActivity(intent);
    }

    public static boolean characterOnly(String input){
        for(int i=0; i<input.length(); i++){
            if(!Character.isLetter(input.charAt(i))){
                return false;
            }
        }
        return true;

    }


}