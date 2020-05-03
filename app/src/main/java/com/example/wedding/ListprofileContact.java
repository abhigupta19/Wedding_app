package com.example.wedding;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class ListprofileContact extends AppCompatActivity {
    ListView listView ;

    static ArrayList<String> StoreContacts ;
   // ArrayList<String> nameBy;
   static  ArrayList<String> nameBy = new ArrayList<>();
   static ArrayList<Integer> b=new ArrayList<>();


    ArrayAdapter<String> arrayAdapter ;
    Cursor cursor ;
    String name, phonenumber ;
    DatabaseReference d;
    public  static final int RequestPermissionCode  = 1 ;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listcontact);

        listView = (ListView)findViewById(R.id.listview1);
        d= FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Full_profile.projectId);


        StoreContacts= GetContactsIntoArrayList();
        Button button=findViewById(R.id.btn_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        startActivity(new Intent(ListprofileContact.this,Profile.class));
                        finish();
                    }
                });



        Add_adapter contactAdapter=new Add_adapter(ListprofileContact.this,StoreContacts,nameBy);
        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final CheckBox checkBox=view.findViewById(R.id.checkbox);
                if(checkBox.isChecked()) {
                    checkBox.setChecked(false);
                    if(b.contains(i))
                    {
                        DatabaseReference db=d.child(nameBy.get(i));
                        db.removeValue();
                        b.remove(new Integer(i));
                    }
                }
                else
                {
                    checkBox.setChecked(true);
                    b.add(i);
                    d.child("contacts").child(nameBy.get(i)).setValue(StoreContacts.get(i));
                }
                Log.i("hhh",nameBy.get(i));

            }
        });

    }

    public ArrayList<String> GetContactsIntoArrayList(){

        ArrayList<String> nameList = new ArrayList<>();

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur!=null ? cur.getCount() : 0) > 0) {
            while (cur!= null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
               // String phoneNumber = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
               // nameList.add(phoneNumber);
               // map.put(phoneNumber,name);
                if (cur.getInt(cur.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if(name.length()!=0 && phoneNo.length()!=0) {
                            nameList.add(phoneNo);
                            nameBy.add(name);
                        }
                        //Log.i("jjjjj",name);
                     //   if(name.isEmpty())
         //               map.put(phoneNo,"ggg");
            //            else
//                        map.put(phoneNo,name);
                    }
                    pCur.close();
                }
            }
        }
        if (cur!= null) {
            cur.close();
        }
        return nameList;
    }

    }

