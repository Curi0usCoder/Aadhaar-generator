package com.antstudios.as.manual.create.copy.of.aadhaar.aadhaargenerator;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.antstudios.as.manual.create.copy.of.aadhaar.aadhaargenerator.CommonMethods.CommonValues;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PutDetailsActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private EditText TeluguName,EnglishName,AadhaarNum;
    private TextView DateOfBirth,IssuedDate;
    private ImageView UploadFace;
    private Button SubmitBtn;

    private int CAMERA_PERMISSION_REQUEST_CODE=5;
    private int READ_EXTERNAL_STORAGE_REQUEST_CODE=6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_details);

        initViews();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonOption1) {
                    CommonValues.getMethods().setAGender("పురుషుడు / Male");
                } else if (checkedId == R.id.radioButtonOption2) {
                    CommonValues.getMethods().setAGender("స్త్రీ / Female");
                }
            }
        });

        TeluguName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CommonValues.getMethods().setATeluguName(TeluguName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EnglishName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CommonValues.getMethods().setAEnglishName(EnglishName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        AadhaarNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CommonValues.getMethods().setAAadhaarNumber(AadhaarNum.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        UploadFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBox();
            }
        });

        DateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(DateOfBirth,1);
            }
        });
        IssuedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(IssuedDate,2);
            }
        });

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckisEmpty()){
                    if (AadhaarNum.getText().toString().length()==12){

                startActivity(new Intent(PutDetailsActivity.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(PutDetailsActivity.this, "Invalid Aadhaar number", Toast.LENGTH_SHORT).show();
                    }
                }
                else {Toast.makeText(PutDetailsActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();}
            }
        });

    }

    private void initViews(){
        radioGroup = findViewById(R.id.radioGroup);
        TeluguName = findViewById(R.id.telugu_txt);
        EnglishName = findViewById(R.id.english_txt);
        DateOfBirth = findViewById(R.id.dob_txt);
        AadhaarNum = findViewById(R.id.aadhaar_txt);
        IssuedDate = findViewById(R.id.issued_date);
        UploadFace = findViewById(R.id.upload_face);
        SubmitBtn = findViewById(R.id.submit_button);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 100);
            } else {
                // Permission denied, handle the denial
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean CheckisEmpty(){

        if (TeluguName.getText().toString()!=null&&!TeluguName.getText().toString().equals("")
                &&EnglishName.getText().toString()!=null&&!EnglishName.getText().toString().equals("")
                &&AadhaarNum.getText().toString()!=null&&!AadhaarNum.getText().toString().equals("")
                &&DateOfBirth.getText().toString()!=null&&!DateOfBirth.getText().toString().equals("")
                &&IssuedDate.getText().toString()!=null&&!IssuedDate.getText().toString().equals(""))

        return true;
        else return false;
    }

    public void openDialogBox() {
        Dialog dialog = new Dialog(PutDetailsActivity.this);

        dialog.setContentView(R.layout.pick_image_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        LinearLayout Camerabtn=dialog.findViewById(R.id.camera_btn);
        LinearLayout Gallerybtn=dialog.findViewById(R.id.gallery_btn);

        Camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(PutDetailsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request it
                    ActivityCompat.requestPermissions(PutDetailsActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 100);
                }
                dialog.dismiss();
            }
        });

        Gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, READ_EXTERNAL_STORAGE_REQUEST_CODE);

                dialog.dismiss();
            }
        });

        dialog.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap;
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            bitmap = (Bitmap) data.getExtras().get("data");
            UploadFace.setImageBitmap(bitmap);
            CommonValues.getMethods().setProfilePhoto(bitmap);
        }
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                // Process the retrieved Bitmap object
                UploadFace.setImageBitmap(bitmap);
                CommonValues.getMethods().setProfilePhoto(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void showDatePickerDialog(TextView editText, int i) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // Display the selected date in the EditText
                        String ExactMonth=month>8?""+(month+1):"0"+(month+1);
                        String selectedDate = dayOfMonth>9?""+dayOfMonth:"0"+dayOfMonth + "/" + ExactMonth + "/" + year;
                        editText.setText(selectedDate);
                        if (i==2){
                        CommonValues.getMethods().setAIssuedDate(selectedDate);
                        }
                        else if (i==1){
                            CommonValues.getMethods().setADateofBirth(selectedDate);
                        }
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }

}