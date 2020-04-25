package com.example.wedding;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codinguser.android.contactpicker.ContactsPickerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.LimitColumn;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class BottomSheet3 extends BottomSheetDialogFragment  {
     Uri file_id;
    static Uri filepath_id;
    Uri file_path_flight;
    static String a="";
    Uri file_tkt;
    private final int PICK_IMAGE_REQUEST_ID = 71;
    private final int PICK_IMAGE_REQUEST_FLIGHT = 72;
    private final  int CAMERA_REQUEST_CODE=73;
    public static final int REQUEST_CODE_PICK_CONTACT = 991;
    public static final int  MAX_PICK_CONTACT= 10;
    Bundle bundle;

    StorageReference storageReference;
    TextView idText;
    ProgressBar progressBar;
    TextView textView;
    String currentPhotoPath;
    private String mContactName = "";
    private String mAllCols = "";
    private ArrayList<ContactResult> results = new ArrayList<>();




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bottomsheet3,container,false);
        Button card_click=view.findViewById(R.id.et_id_click);
        Button card_upload=view.findViewById(R.id.et_id_up);
//        Button doument_click=view.findViewById(R.id.et_flight);
        idText=view.findViewById(R.id.flight_txt);
        progressBar=view.findViewById(R.id.progress_doucment);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getProgressDrawable().setColorFilter(
                Color.parseColor("#ff4081"), android.graphics.PorterDuff.Mode.SRC_IN);


        //  progressBar.setProgressDrawable(R.color.primary_dark,);
        final Button doument_upload=view.findViewById(R.id.et_flight_click);
        doument_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("jjjj","pp");
                doument_upload.setText("Wait..");
                startActivity(new Intent(getActivity(),Listcontact.class));
               // Log.d("kk",results.get(0).toString());
            }
        });
        textView=view.findViewById(R.id.id_txt);
        card_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,PICK_IMAGE_REQUEST_ID);
            }
        });
        card_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                takePicture("idClick");


               // Uri file = Uri.fromFile(new File(String.valueOf(file_id)));\\



            }
        });
        Button register =view.findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // upload();
            }
        });

        return view;

    }
    public void takePicture( String str) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivityForResult(intent, 100);
//        }
//
//        //a= String.valueOf(getOutputMediaFile());
//       // Uri file= FileProvider.getUriForFile(getActivity(), getContext().getPackageName() + ".provider", getOutputMediaFile());
//        //intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
//        if(str=="idClick") {
//            Log.i("jjjjj","ok");
//            startActivityForResult(intent, 100);
//        }

//        if(str=="tktClick")
//            startActivityForResult(intent, 101);
//        //return file;
//        Intent photo = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//
//        startActivityForResult(photo,CAMERA_REQUEST_CODE);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "net.smallacademy.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
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
            a="IMG_"+ timeStamp + ".jpg";
        return new File(mediaStorageDir + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK
        ) {
            Log.d("jjjj", "okkk");

//            filepath_id = data.getData();
//            Log.d("jjjj",filepath_id.toString());
//            idText.setText(getFileName(filepath_id));
////                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
////                imageView.setImageBitmap(bitmap);
            File f = new File(currentPhotoPath);
            Uri uri = Uri.fromFile(f);
            upload_click(uri);
            //  selectedImage.setImageURI(Uri.fromFile(f));
            Log.d("jjjjjj", "ABsolute Url of Image is " + Uri.fromFile(f));

            // Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            // Uri contentUri = Uri.fromFile(f);
            // mediaScanIntent.setData(contentUri);
            //this.sendBroadcast(mediaScanIntent);

        }
        if (requestCode == PICK_IMAGE_REQUEST_ID && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            //  filepath_id = data.getData();
            //  idText.setText(getFileName(file_path_flight));
            file_path_flight = data.getData();
            upload();
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imageView.setImageBitmap(bitmap);
        }
        if (resultCode == 100) {
            if (resultCode == RESULT_OK) {

//                imageView.setImageURI(file);

                Log.d("jjjj", data.getData().toString());
                filepath_id = data.getData();
                // idText.setText(getFileName(file_tkt));


            }

        }
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                //idText.setText(getFileName(file_id));
                // Log.d("jjjj",data.getData().toString());
                // filepath_id = data.getData();

//                imageView.setImageURI(file);
            }
        }
        if (requestCode == REQUEST_CODE_PICK_CONTACT) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Cursor cursor = getContext().getContentResolver().query( ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
                while (cursor.moveToNext()) {
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    Log.d("jjj",hasPhone.toString());
                    if("1".equals(hasPhone) || Boolean.parseBoolean(hasPhone)) {
                        // You know it has a number so now query it like this
                        Cursor phones = getActivity().getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId, null, null);
                        while (phones.moveToNext()) {
                            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            int itype = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

                            final boolean isMobile =
                                    itype == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE ||
                                            itype == ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE;

                            // Do something here with 'phoneNumber' such as saving into
                            // the List or Array that will be used in your 'ListView'.

                        }
                        phones.close();
                    }
                }

            }
        }
    }









    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
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
    public  void upload()
    {

        progressBar.setVisibility(View.VISIBLE);
        textView.setText("uploading....");
        storageReference = FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        storageReference.child("card").child(Calendar.getInstance().getTime().toString()).putFile(file_path_flight).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                textView.setText("Uploaded");
            }
        });

    }
    public  void upload_click(Uri uri)
    {

        progressBar.setVisibility(View.VISIBLE);
        textView.setText("uploading....");
        storageReference = FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        storageReference.child("card").child(Calendar.getInstance().getTime().toString()).putFile(uri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                textView.setText("Uploaded");
            }
        });

    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void getContactName(String id) {
        Cursor cursor = null;
        String result = "";

        try {
            cursor = getContext().getContentResolver().query(ContactsContract
                            .Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + "=?",
                    new String[] { id },
                    null);

            if (cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex
                        (ContactsContract.Data.DISPLAY_NAME));

                // Get all columns
                mAllCols = "";
                String columns[] = cursor.getColumnNames();
                for (String column : columns) {
                    int index = cursor.getColumnIndex(column);
                    mAllCols += ("(" + index + ") [" + column + "] = ["
                            + cursor.getString(index) + "]\n");
                }

                cursor.close();
            }
        } catch (Exception e) {
            Log.e("jjjj", e.getMessage());
        }

        mContactName = result;
    }
}
