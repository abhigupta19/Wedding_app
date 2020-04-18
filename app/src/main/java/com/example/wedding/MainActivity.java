package com.example.wedding;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BottomAppBar bottomAppBar=findViewById(R.id.bottombar);
        //bottomAppBar.setNavigationIcon(R.drawable.ic_home_black_24dp);
        //bottomAppBar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        bottomAppBar.setPadding(20,10,0,0);
        bottomAppBar.setTitle("helo");
        //setSupportActionBar(bottomAppBar
        FloatingActionButton floatingActionButton=findViewById(R.id.floatting);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Bottomsheet bottomsheet=new Bottomsheet();
                    bottomsheet.show(getSupportFragmentManager(),"botoomsheet");

            }
        });
    }



}
