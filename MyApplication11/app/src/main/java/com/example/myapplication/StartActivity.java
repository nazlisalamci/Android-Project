package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;



public class StartActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mAuth=FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser()!=null){
            Intent loginIntent=new Intent(StartActivity.this,LoginActivity.class);
            startActivity(loginIntent);
            Toast.makeText(getApplicationContext(),"Please Log in",Toast.LENGTH_SHORT).show();
        }
        else{
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(StartActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        },1500);}
    }


}