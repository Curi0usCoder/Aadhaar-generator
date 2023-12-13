package com.antstudios.as.manual.create.copy.of.aadhaar.aadhaargenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.antstudios.as.manual.create.copy.of.aadhaar.aadhaargenerator.CommonMethods.CommonValues;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    RelativeLayout AadhaarCard;
    Button SaveBtn;

    Bitmap myAadhaar;
    private ImageView FinalImage;
    private int WRITE_EXTERNAL_STORAGE_REQUEST_CODE=10;

    private TextView TeluguNametxt,EnglishNametxt,Aadhaarnumtxt,DOBtxt,IssuedDatetxt,Gendertxt;
    private ImageView Profileimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        TeluguNametxt.setText(CommonValues.getMethods().getATeluguName());
        EnglishNametxt.setText(CommonValues.getMethods().getAEnglishName());
        Aadhaarnumtxt.setText(AadhaarFormat());
        DOBtxt.setText(CommonValues.getMethods().getADateofBirth());
        IssuedDatetxt.setText(CommonValues.getMethods().getAIssuedDate());
        Gendertxt.setText(CommonValues.getMethods().getAGender());
        Profileimg.setImageBitmap(CommonValues.getMethods().getProfilePhoto());

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myAadhaar=createBitmapFromRelativeLayout(AadhaarCard);

                FinalImage.setImageBitmap(myAadhaar);

                String fileName = "ManualAadhaar_" + new Date().getTime() + ".jpg";

                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_TITLE, fileName);
               // startActivityForResult(intent, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);

            }
        });

    }

    private void initViews(){

        AadhaarCard=findViewById(R.id.DAadhaar_card_layout);
        SaveBtn=findViewById(R.id.save_aadhaar);
        TeluguNametxt=findViewById(R.id.Dtelugu_name);
        EnglishNametxt=findViewById(R.id.Denglish_name);
        Aadhaarnumtxt=findViewById(R.id.Daadhaar_number);
        DOBtxt=findViewById(R.id.Ddate_of_birth);
        Gendertxt=findViewById(R.id.Dgender_text);
        IssuedDatetxt=findViewById(R.id.Dissued_date_text);
        Profileimg=findViewById(R.id.Dpassport_image);

        FinalImage=findViewById(R.id.final_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Get the URI of the selected file or folder
                Uri uri = data.getData();

                // Save the bitmap to the specified URI
                try {
                    OutputStream out = getContentResolver().openOutputStream(uri);
                    myAadhaar.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    // Handle error
                }
            }
        }
    }

    private String AadhaarFormat(){

        String newNum="";
        for (int i=0;i<12;i++){
            if (i==4||i==8){
                newNum=newNum+" ";
            }
            newNum=newNum+CommonValues.getMethods().getAAadhaarNumber().charAt(i);
        }

        return newNum;
    }

    public Bitmap createBitmapFromRelativeLayout(RelativeLayout relativeLayout) {
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getDrawingCache());
        relativeLayout.setDrawingCacheEnabled(false);

        return bitmap;
    }



}