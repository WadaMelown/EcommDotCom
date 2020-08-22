package com.example.EcommerceShop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private Button loginButton, signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.main_login);
        signupButton = (Button) findViewById(R.id.main_login);
    }
}
