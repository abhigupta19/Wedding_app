package com.example.wedding;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;

import com.google.android.material.bottomappbar.BottomAppBar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomAppBar bottomAppBar=findViewById(R.id.bottombar);
        //bottomAppBar.setNavigationIcon(R.drawable.ic_home_black_24dp);
        //bottomAppBar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        bottomAppBar.setPadding(20,10,0,0);
        bottomAppBar.setTitle("helo");
        //setSupportActionBar(bottomAppBar);

    }



}
