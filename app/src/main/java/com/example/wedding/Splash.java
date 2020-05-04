package com.example.wedding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    startActivity(new Intent(Splash.this,MainActivity.class));
            }
        },3000);
    }
}