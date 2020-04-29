package com.example.wedding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Profile extends AppCompatActivity implements project_touch{
     ArrayList<String> name=new ArrayList<>();
    ArrayList<String> email=new ArrayList<>();
    ArrayList<String> phone=new ArrayList<>();
    ArrayList<String> projectId=new ArrayList<>();
    ArrayList<String> typeOf=new ArrayList<>();
    ArrayList<String> date=new ArrayList<>();
     RecyclerView recyclerView;ProfileAdapter profileAdapter;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        recyclerView=findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textView=findViewById(R.id.title);
        textView.setText("wait for some time.....");


        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                        projectId.add(dataSnapshot1.getKey());
                        name.add(dataSnapshot1.child("profile").child("name").getValue().toString());
                        email.add(dataSnapshot1.child("profile").child("email").getValue().toString());
                        phone.add(dataSnapshot1.child("profile").child("number").getValue().toString());
                        date.add(dataSnapshot1.child("profile").child("Date").getValue().toString());
                        typeOf.add(dataSnapshot1.child("profile").child("type").getValue().toString());
                    }

                    if(projectId.size()==0)
                    {
                        textView.setText("no event found...");
                    }
                    else {
                        textView.setText("Your event list");
                    }
                profileAdapter =new ProfileAdapter( Profile.this,name,email,phone,projectId,typeOf,date);
                    recyclerView.setAdapter(profileAdapter);
                profileAdapter.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       // ProfileAdapter profileAdapter=new ProfileAdapter(this,)
    }

    @Override
    public void touch(View View, int postion) {
        Intent intent=new Intent(Profile.this,Full_profile.class);
        intent.putExtra("name",name.get(postion));
        intent.putExtra("email",email.get(postion));
        intent.putExtra("projet_id",projectId.get(postion));
        intent.putExtra("type_of",typeOf.get(postion));
        intent.putExtra("date",date.get(postion));
        intent.putExtra("phone",phone.get(postion));
        startActivity(intent);
    }
}
