package com.lol.brah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerName;
    private EditText registerPassword;
    private TextView registerText;
    private TextView haveAccountText;
    private Button signupButton;
    static Map<String,String> logInDaten = new LinkedHashMap<>();
    private View registerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerName = findViewById(R.id.registerName);
        registerPassword = findViewById(R.id.registerPassword);
        registerText = findViewById(R.id.registerTextView);
        signupButton = findViewById(R.id.registerButton);
        haveAccountText = findViewById(R.id.DontHaveAccount_Text);


        Intent intent = getIntent();
        String data = intent.getStringExtra("loginNameToRegister");



        haveAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                haveAccount();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpButton();
            }
        });




    }

    public void haveAccount(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("registerNameToLogin", registerName.getText().toString());
        startActivity(intent);

    }

    public void signUpButton(){
        String name = registerName.getText().toString();
        if( !(TextUtils.isEmpty(name) &&
                TextUtils.isEmpty(registerPassword.getText())) ) {
            if (charachterOnly(name)) {
                Log.d("signup: ","before the contain");
                if(logInDaten.containsKey(name)){
                    registerText.setTextSize(15);
                    registerText.setTextColor(Color.parseColor("#B51010"));
                    registerText.setText("This name is taken! \n Try with another name");
                    Log.d("signup: ","before the map");

                } else {
                    Log.d("signup: ","not in the map");
                    String password = registerPassword.getText().toString();
                    logInDaten.put(name, password);
                    Log.d("signup: ","in the map");

                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("registerNameToLogin", registerName.getText().toString());
                    startActivity(intent);
                }

            } else {
                registerText.setTextSize(15);
                registerText.setTextColor(Color.parseColor("#B51010"));
                registerText.setText("Please enter your name with characters only!");
            }
        } else {
            registerText.setTextSize(15);
            registerText.setTextColor(Color.parseColor("#B51010"));
            registerText.setText("Please enter your name and password!");

        }
        Log.d("signup: ","at the end");
    }
    public static void changePassword(String user, String password){
        logInDaten.remove(user);
        logInDaten.put(user,password);
    }


    public static boolean charachterOnly(String input){
        for(int i=0; i<input.length(); i++){
            if(!Character.isLetter(input.charAt(i))){
                return false;
            }
        }
        return true;

    }




}