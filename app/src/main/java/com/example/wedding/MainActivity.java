package com.example.wedding;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Bottomsheet.sendData , Bottomsheet2.sendData2 {
     FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference;
     ArrayList<String> photos=new ArrayList<>();
    static String projectId;
    RecyclerView recyclerView;
   static int random;


    public static final String MY_PREFERENCE_KEY = "my_preference_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase=FirebaseDatabase.getInstance();
        TextView home=findViewById(R.id.home);
        TextView profile=findViewById(R.id.profile);
        home.setClickable(true);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,Profile.class));
            }
        });

        final BottomAppBar bottomAppBar=findViewById(R.id.bottombar);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("photos");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    photos.add(dataSnapshot1.getValue().toString());
                    Log.i("jjjjjj",dataSnapshot1.getValue().toString());
                }
                Log.d("lll", String.valueOf(photos.size()));
                ListAdapter listAdapter = new ListAdapter(MainActivity.this, photos);
                recyclerView.setAdapter(listAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("jjjjjj","kkkk");
            }
        });



        //bottomAppBar.setNavigationIcon(R.drawable.ic_home_black_24dp);
        //bottomAppBar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        bottomAppBar.setPadding(20,10,0,0);
        bottomAppBar.setTitle("helo");
                //setSupportActionBar(bottomAppBar
        FloatingActionButton floatingActionButton=findViewById(R.id.floatting);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                random = new Random().nextInt((900000 - 1000) + 1) + 1000;
                databaseReference=firebaseDatabase.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(random));

                SharedPreferences.Editor editor= (SharedPreferences.Editor) getSharedPreferences(MY_PREFERENCE_KEY,MODE_PRIVATE).edit();
                editor.putString("random", String.valueOf(random));
                editor.commit();

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
     }

    @Override
    public void sendInfo2(String Date, String type, String remarks) {
        if(!Date.isEmpty())
        databaseReference.child("profile").child("Date").setValue(Date);
        if(!type.isEmpty())
            databaseReference.child("profile").child("type").setValue(type);
        if(!remarks.isEmpty())
            databaseReference.child("profile").child("remarks").setValue(remarks);
        SharedPreferences sharedPreferences=getSharedPreferences(MY_PREFERENCE_KEY,MODE_PRIVATE);
        String name=sharedPreferences.getString("name","");
        String email=sharedPreferences.getString("email","");
        String number=sharedPreferences.getString("number","");
        databaseReference.child("profile").child("name").setValue(name);
        databaseReference.child("profile").child("email").setValue(email);
        databaseReference.child("profile").child("number").setValue(number);

    }
}
