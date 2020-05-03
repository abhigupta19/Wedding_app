package com.example.wedding;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.wedding.Full_profile.projectId;

public class deleteContact extends AppCompatActivity {
    ListView listView;

    static ArrayList<String> StoreContacts;
    // ArrayList<String> nameBy;
    static ArrayList<String> nameBy = new ArrayList<>();
    static ArrayList<String> number = new ArrayList<String>();
    static ArrayList<Integer> b = new ArrayList<Integer>();


    ArrayAdapter<String> arrayAdapter;
    Cursor cursor;
    String name, phonenumber;
    DatabaseReference d;
    public static final int RequestPermissionCode = 1;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listcontact);

        listView = (ListView) findViewById(R.id.listview1);
        d = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(projectId).child("contacts");


        Intent intent=getIntent();
        nameBy=intent.getStringArrayListExtra("namelist");
        number=intent.getStringArrayListExtra("number");
        final Button button = findViewById(R.id.btn_register);
        button.setText("Delete");
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button.setText("Done");
                        startActivity(new Intent(deleteContact.this, Profile.class));
                                finish();
                            }




                });
        deleteAdapter contactAdapter = new deleteAdapter(deleteContact.this, number, nameBy);
        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final CheckBox checkBox = view.findViewById(R.id.checkbox);
                        checkBox.setChecked(true);
                        DatabaseReference db = d.child(nameBy.get(i));
                        db.removeValue();



                Log.i("hhh", nameBy.get(i));

            }
        });

    }

}

