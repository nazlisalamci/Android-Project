package com.example.myapplication;

import static android.Manifest.permission.CAMERA;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;


import java.io.IOException;


public class fotocozumle extends Fragment {

    private Button camerabtn;
    private EditText edttxt;
    private ImageView imagetext;
    private Activity activity;
    private static final int CAMERA_REQUEST=10;
    private static final int PERMISSION_CODE=100;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fotocozumle, container, false);
        edttxt = v.findViewById(R.id.edttxt);
        imagetext = v.findViewById(R.id.imagetext);
        camerabtn=v.findViewById(R.id.camerabtn);
        camerabtn.setOnClickListener(view -> {

           if (getActivity().checkSelfPermission(CAMERA)!=PackageManager.PERMISSION_GRANTED){
               requestPermissions(new String[]{CAMERA},PERMISSION_CODE);
           }
           else{
               Intent camareIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(camareIntent,CAMERA_REQUEST);
           }

        });

        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(),"Granted",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CAMERA_REQUEST&&requestCode== RESULT_OK){
            Bitmap image=(Bitmap) data.getExtras().get("data");
            imagetext.setImageBitmap(image);
            textDetect();
        }
    }
    public void textDetect(){
        TextRecognizer recognizer=new TextRecognizer.Builder(getActivity()).build();
        Bitmap bitmap=((BitmapDrawable)imagetext.getDrawable()).getBitmap();

        Frame frame=new Frame.Builder().setBitmap(bitmap).build();

        SparseArray<TextBlock> sparseArray=recognizer.detect(frame);
        StringBuilder stringBuilder=new StringBuilder();

        for (int i = 0; i <sparseArray.size() ; i++) {
            TextBlock textBlock=sparseArray.get(i);
            String str=textBlock.getValue();
            stringBuilder.append(str);
        }
        edttxt.setText(stringBuilder);
    }
}





