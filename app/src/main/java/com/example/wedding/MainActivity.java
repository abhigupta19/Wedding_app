package com.example.wedding;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements Bottomsheet.sendData , Bottomsheet2.sendData2 {
     FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference;
    public static final String MY_PREFERENCE_KEY = "my_preference_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
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


    @Override
    public void sendInfo(String name, String email, String number) {
        Log.d("jjj",name+email+number);
        SharedPreferences.Editor editor= (SharedPreferences.Editor) getSharedPreferences(MY_PREFERENCE_KEY,MODE_PRIVATE).edit();
        editor.putString("name",name);
        editor.putString("email",email);
        editor.putString("number",number);
        editor.commit();
        databaseReference.child("profile").child("name").setValue(name);
        databaseReference.child("profile").child("email").setValue(email);
        databaseReference.child("profile").child("number").setValue(number);
    }

    @Override
    public void sendInfo2(String Date, String type, String remarks) {
        if(!Date.isEmpty())
        databaseReference.child("profile").child("Date").setValue(Date);
        if(!type.isEmpty())
            databaseReference.child("profile").child("email").setValue(type);
        if(!remarks.isEmpty())
            databaseReference.child("profile").child("remarks").setValue(remarks);

    }
}
