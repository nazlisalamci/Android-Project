package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;




public class RegisterActivity extends AppCompatActivity {

    private EditText regName,regEmail,regpassword,regusername;
    private TextView regText;
    private Button regButton;
    private ProgressDialog regProgres;
    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;
    private   HashMap<String,Object> hashMap;




    void Init(){
        regName=findViewById(R.id.register_name);
        regEmail=findViewById(R.id.register_email);
        regpassword=findViewById(R.id.register_password);
        regusername=findViewById(R.id.register_username);
        regText=findViewById(R.id.textView);
        regButton=findViewById(R.id.button2);
        regProgres=new ProgressDialog(this);



       mRootRef=FirebaseDatabase.getInstance().getReference();
       mAuth=FirebaseAuth.getInstance();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

             Init();
             regText.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent loginIntent=new Intent(RegisterActivity.this,LoginActivity.class);
                     startActivity(loginIntent);

                 }
             });

             regButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     String name=regName.getText().toString();
                     String email=regEmail.getText().toString();
                     String password=regpassword.getText().toString();
                     String username=regusername.getText().toString();



                     if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password)
                             &&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(username)){

                         regProgres.setTitle("Recording");
                         regProgres.setMessage("Please wait");
                         regProgres.setCanceledOnTouchOutside(false);
                         regProgres.show();

                         register_user(name,password,email,username);

                     }
                 }
             }); }




    private void register_user(String name, String password, String email,String username) {
        regProgres.setMessage("Please Wait!");
        regProgres.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    hashMap = new HashMap<>();
                    hashMap.put("name", name);
                    hashMap.put("password", password);
                    hashMap.put("email", email);
                    hashMap.put("username", username);
                    hashMap.put("id", mAuth.getCurrentUser().getUid());

                    mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(hashMap).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            regProgres.dismiss();
                            if (task.isSuccessful( )) {
                                Toast.makeText(RegisterActivity.this, "Succesfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity2.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                }
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                regProgres.dismiss();
                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

}
}

