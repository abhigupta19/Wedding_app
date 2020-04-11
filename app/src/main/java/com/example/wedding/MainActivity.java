package com.example.wedding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText nameText;
    EditText mailText;
    Button id_up;
    Button pnr_submit;
    Button fli_up;
    EditText pnrText;
    TextView idText;
    TextView flightText;
    Button register;
    boolean id_uploaded;
    boolean flight_uploaded;
    boolean pnr_upload;
    private final int PICK_IMAGE_REQUEST_ID = 71;
    private final int PICK_IMAGE_REQUEST_FLIGHT = 72;
    Uri filepath_id;
    Uri file_path_flight;
    Uri file_id;
    Uri file_tkt;
    FirebaseStorage storage;
    StorageReference storageReference;
    String phone;
    FirebaseDatabase database;
    Button id_click;
    Button flight_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id_uploaded=false;
        flight_uploaded=false;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        pnr_upload=false;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        nameText=findViewById(R.id.et_name);
        mailText=findViewById(R.id.et_email);
        id_up=findViewById(R.id.et_id_up);
        fli_up=findViewById(R.id.et_flight_up);
        pnrText=findViewById(R.id.et_pnr_edit);
        idText=findViewById(R.id.id_txt);
        flightText=findViewById(R.id.flight_txt);
        register=findViewById(R.id.btn_register);
        Intent intent=getIntent();
        phone= intent.getStringExtra("phone");
        database = FirebaseDatabase.getInstance();
        id_click=findViewById(R.id.et_id_click);
        flight_click=findViewById(R.id.et_flight_click);
        id_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture("idClick");
            }
        });
        flight_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture("tktClick");

            }
        });

        id_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage("id");
            }
        });
        fli_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage("flight");

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");

                myRef.setValue("Hello, World!");
                boolean is_ok=true;

                if(nameText.getText().toString().isEmpty())
                {
                    nameText.setError("Please Enter a name");
                    is_ok=false;
                }
                if(mailText.getText().toString().isEmpty())
                {
                    mailText.setError("Please Enter your mail id");
                    is_ok=false;
                }
                if(idText.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please Upload your ID Proof", Toast.LENGTH_SHORT).show();
                    is_ok=false;
                }
                if((flightText.getText().toString().isEmpty()) && (pnrText.getText().toString().isEmpty()))
                {
                    is_ok=false;
                    Toast.makeText(MainActivity.this, "Please enter either pnr or flight ticket", Toast.LENGTH_SHORT).show();

                }
                if(is_ok)
                {
                    System.out.println("wow");
                    String name=nameText.getText().toString();
                    String mail=mailText.getText().toString();
                    String s_flight=uploadImage(file_path_flight);
                    String s_id=uploadImage(filepath_id);
                    String pnr=pnrText.getText().toString();
                    User user=new User(phone , name , s_id , s_flight , pnr);

                    myRef.setValue(user);

                }


            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST_ID && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filepath_id = data.getData();
            idText.setText(getFileName(filepath_id));
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imageView.setImageBitmap(bitmap);


        }
        if(requestCode == PICK_IMAGE_REQUEST_FLIGHT && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            file_path_flight = data.getData();
            flightText.setText(getFileName(file_path_flight));
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imageView.setImageBitmap(bitmap);


        }
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                idText.setText(getFileName(file_id));

//                imageView.setImageURI(file);
            }
        }
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
//                imageView.setImageURI(file);
                idText.setText(getFileName(file_tkt));

            }
        }
    }
    private void chooseImage(String s) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if(s.compareTo("id")==0)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_ID);
        if(s.compareTo("flight")==0)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_FLIGHT);

    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
    private String uploadImage(Uri filePath) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            String str="images/"+ UUID.randomUUID().toString();
            StorageReference ref = storageReference.child(str);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
            return str;
        }
        else
            return "";
    }
    public void takePicture( String str) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        if(str=="idClick")
        startActivityForResult(intent, 100);
        if(str=="tktClick")
            startActivityForResult(intent, 101);

    }
    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }
}
