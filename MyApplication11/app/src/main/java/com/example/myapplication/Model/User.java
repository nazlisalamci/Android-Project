package com.example.myapplication.Model;

public class User {

    private  String id;
    private  String UserName;
    private  String imageurl;

    public User(String id, String userName, String imageurl) {
        this.id = id;
        UserName = userName;
        this.imageurl = imageurl;
    }
    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
