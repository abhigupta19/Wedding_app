package com.example.wedding;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Bottomsheet2 extends BottomSheetDialogFragment {
    public sendData2 sendData2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.bottomsheet2,container,false);
        final DatePicker datePicker=view.findViewById(R.id.date);
        final TextInputLayout type=view.findViewById(R.id.type);
        final TextInputLayout remrks=view.findViewById(R.id.remarks);
        Button next=view.findViewById(R.id.nextPfrag);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int k=1;
                if(type.getEditText().getText().toString().isEmpty())
                {
                    k=0;
                    type.setError("Input type of event");
                }
                if(k==1) {
                    sendData2.sendInfo2(String.valueOf(datePicker.getDayOfMonth())+"/"+String.valueOf(datePicker.getMonth())+"/"+String.valueOf(datePicker.getYear()), type.getEditText().getText().toString(), remrks.getEditText().getText().toString());
                    BottomSheet3 bottomSheet3=new BottomSheet3();
                    dismiss();
                    bottomSheet3.show(getFragmentManager(),"jaibaba");
                }
                }
        });
        return view;
    }
    public interface sendData2
    {
        void sendInfo2(String Date,String type,String remarks);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            sendData2= (Bottomsheet2.sendData2) getActivity();
        }
        catch (ClassCastException e)
        {
            Log.d("error",e.getMessage());
        }


    }
}
