package com.example.wedding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Full_profile extends AppCompatActivity {
     ListView listView;
     TextView textView;
    static   String projectId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_profile);
        getSupportActionBar().hide();
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        projectId=intent.getStringExtra("project_id");
        String email=intent.getStringExtra("email");
        final String date=intent.getStringExtra("date");
        textView=findViewById(R.id.textView5);
        String type=intent.getStringExtra("type");
        final ArrayList<String> nameList=new ArrayList<>();
        listView=findViewById(R.id.list);
        EditText email1=findViewById(R.id.Mail);
        EditText number1=findViewById(R.id.Phone);
        EditText type1=findViewById(R.id.Type);
        EditText date1=findViewById(R.id.date);
        EditText projectid1=findViewById(R.id.projectId);
        projectid1.setText(projectId);
        date1.setText(date.toString());
        number1.setText(name);
        type1.setText(type.toString());
        email1.setText(email.toString());

        final ArrayList<String> phoneList=new ArrayList<>();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(projectId).child("contacts").getChildrenCount()==0)
                {
                    textView.setText("No contact found...");
                }
                for (DataSnapshot dataSnapshot1 : dataSnapshot.child(projectId).child("contacts").getChildren()) {
                    {
                        nameList.add(dataSnapshot1.getKey().toString());
                        phoneList.add(dataSnapshot1.getValue().toString());
                        Log.d("kkk", dataSnapshot1.getValue().toString());
                        Log.d("kkk", dataSnapshot1.getKey().toString());
                        textView.setText("Your contact list..");
                    }
                    profile_contact_adapter profile_contact_adapter=new profile_contact_adapter(Full_profile.this,phoneList,nameList);
                    listView.setAdapter(profile_contact_adapter);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ImageView edit=findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Full_profile.this,deleteContact.class);
                intent1.putExtra("namelist",nameList);
                intent1.putExtra("number",phoneList);
                startActivity(intent1);

            }
        });
        final ImageView add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add.setVisibility(View.INVISIBLE);
                textView.setText("Wait...");
             startActivity(new Intent(Full_profile.this,ListprofileContact.class));

            }
        });
    }
}
