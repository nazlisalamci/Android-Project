package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadImage extends AppCompatActivity {

    private static  final  int PICK_IMAGE_REQUEST=1;
    private Button mButtonChoosseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    //

    private StorageTask mUploadTask;



    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private FirebaseAuth mAuth;


    private void init(){
        mButtonChoosseImage=findViewById(R.id.button_choose_image);
        mButtonUpload=findViewById(R.id.button_upload);
        mTextViewShowUploads=findViewById(R.id.text_view_show_uploads);
        mEditTextFileName=findViewById(R.id.edit_text_file_name);
        mImageView=findViewById(R.id.image_view);
        mProgressBar=findViewById(R.id.progerss_bar);

        mAuth=FirebaseAuth.getInstance();


        //database request

        mStorageRef= FirebaseStorage.getInstance().getReference("uploands");
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploands");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        init();


        mButtonChoosseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();

            }
        });

    }
    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    private void uploadFile() {
        if (mImageUri !=null){
            StorageReference fileReference=mStorageRef.child("uploands/"+System.currentTimeMillis()
            +"."+getFileExtension(mImageUri));


            //*/
            mUploadTask = fileReference.putFile(mImageUri);


            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    },5000);

                    Toast.makeText(UploadImage.this,"Upload Succesful",Toast.LENGTH_SHORT).show();

                /*    Upload upload=new Upload(mEditTextFileName.getText().toString().trim(),
                            taskSnapshot.getStorage().getDownloadUrl().toString(),mAuth.getUid());
                    String uploadId=mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(upload);
                    */

                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                        @Override
                        public void onSuccess(Uri uri) {
                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim()
                                    , uri.toString(),mAuth.getUid());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);

                        }

                    });

                  /*  Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();

                    Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),downloadUrl.toString(),mAuth.getUid());

                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(upload);

                   */



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadImage.this,e.getMessage(),
                            Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress=(100.0 *snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    mProgressBar.setProgress((int)progress);
                }
            });

        }else{
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }
}