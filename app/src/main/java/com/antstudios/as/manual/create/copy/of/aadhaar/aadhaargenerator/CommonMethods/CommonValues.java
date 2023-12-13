package com.antstudios.as.manual.create.copy.of.aadhaar.aadhaargenerator.CommonMethods;

import android.graphics.Bitmap;

public class CommonValues {

    private static CommonValues commonValues=new CommonValues();
    private String ATeluguName,AEnglishName,AAadhaarNumber,ADateofBirth,AIssuedDate;
    private String AGender="పురుషుడు / Male";
    private Bitmap ProfilePhoto;

    public static CommonValues getMethods(){
        return commonValues;
    }

    public String getATeluguName() {
        return ATeluguName;
    }

    public void setATeluguName(String ATeluguName) {
        this.ATeluguName = ATeluguName;
    }

    public String getAEnglishName() {
        return AEnglishName;
    }

    public void setAEnglishName(String AEnglishName) {
        this.AEnglishName = AEnglishName;
    }

    public String getAAadhaarNumber() {
        return AAadhaarNumber;
    }

    public void setAAadhaarNumber(String AAadhaarNumber) {
        this.AAadhaarNumber = AAadhaarNumber;
    }

    public String getADateofBirth() {
        return ADateofBirth;
    }

    public void setADateofBirth(String ADateofBirth) {
        this.ADateofBirth = ADateofBirth;
    }

    public String getAGender() {
        return AGender;
    }

    public void setAGender(String AGender) {
        this.AGender = AGender;
    }

    public String getAIssuedDate() {
        return AIssuedDate;
    }

    public void setAIssuedDate(String AIssuedDate) {
        this.AIssuedDate = AIssuedDate;
    }

    public Bitmap getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(Bitmap profilePhoto) {
        ProfilePhoto = profilePhoto;
    }
}
