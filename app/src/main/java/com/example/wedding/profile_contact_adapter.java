package com.example.wedding;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class profile_contact_adapter extends BaseAdapter {
    Context context;
    ArrayList<String> contacts;
    ArrayList<String> nameBy;
    Listcontact listcontact=new Listcontact();// Map<String,String> map;
    profile_contact_adapter(Context context, ArrayList<String> contacts,ArrayList<String> nameBy)
    {
        this.contacts=contacts;
        this.context=context;
        this.nameBy=nameBy;
    }
    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1=LayoutInflater.from(context).inflate(R.layout.profile_list_contact,viewGroup,false);
        TextView textView=view1.findViewById(R.id.text);
        CheckBox checkBox=view1.findViewById(R.id.checkbox);

     // if(listcontact.b.contains(i))
      //      checkBox.setChecked(true);
    //    checkBox.setChecked(checkBox.isChecked());
        TextView number=view1.findViewById(R.id.number);
        number.setText(contacts.get(i));
       textView.setText(nameBy.get(i));
  //     checkBox.findFocus();
        return view1;
    }
}
