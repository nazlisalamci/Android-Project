package com.example;


public class Upload {

    private String mDescription;
    private String mImageUri;
    private String mFromWho;

    public Upload(){

    }

    public Upload(String mDescription, String mImageUri, String mFromWho) {

        this.mDescription = mDescription;
        this.mImageUri = mImageUri;
        this.mFromWho = mFromWho;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmImageUri() {
        return mImageUri;
    }

    public String getmFromWho() {
        return mFromWho;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }

    public void setmFromWho(String mFromWho) {
        this.mFromWho = mFromWho;
    }
}
