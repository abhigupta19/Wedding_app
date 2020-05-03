package com.example.wedding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class deleteAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> contacts;
    ArrayList<String> nameBy;
    deleteContact listcontact=new deleteContact();// Map<String,String> map;
    deleteAdapter(Context context, ArrayList<String>  contacts, ArrayList<String> nameBy)
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
        View view1=LayoutInflater.from(context).inflate(R.layout.deleteitem,viewGroup,false);
        TextView textView=view1.findViewById(R.id.text);
        CheckBox checkBox=view1.findViewById(R.id.checkbox);

        if(listcontact.b.contains(i))
            checkBox.setChecked(true);
        checkBox.setChecked(checkBox.isChecked());
        TextView number=view1.findViewById(R.id.number);
        number.setText(contacts.get(i));
        textView.setText(nameBy.get(i));
        checkBox.findFocus();
        return view1;
    }
}
