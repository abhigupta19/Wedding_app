package com.example.wedding;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Bottomsheet extends BottomSheetDialogFragment {
    public  sendData sendData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.bottomsheet,container,false);
           // container.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button button=v.findViewById(R.id.nextFragment);
            final TextInputLayout name=v.findViewById(R.id.name);
            final TextInputLayout email=v.findViewById(R.id.email);
            final TextInputLayout number=v.findViewById(R.id.number);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int k=1;
                    if(name.getEditText().getText().toString().isEmpty())
                    {
                        name.setError("Fill Name");
                        k=0;
                    }
                    if(email.getEditText().getText().toString().isEmpty() && email.getEditText().getText().toString().contains("@"))
                    {
                        email.setError("Fill Email");
                        k=0;
                    }
                    if(number.getEditText().getText().toString().isEmpty())
                    {
                        number.setError("Fill Mobile number");
                        k=0;
                    }
                    if(k==1)
                    {

                        sendData.sendInfo(name.getEditText().getText().toString(),email.getEditText().getText().toString(),number.getEditText().getText().toString());
                        Bottomsheet2 bottomsheet2=new Bottomsheet2();
                        bottomsheet2.show(getFragmentManager(),"jai");
                    }
                }
            });
            return v;
    }
    public interface sendData
    {
        void sendInfo(String name,String email,String number);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            sendData=(sendData)getActivity();
        }
        catch (ClassCastException e)
        {
            Log.d("error",e.getMessage());
        }


    }
}
