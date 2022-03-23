package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Upload;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    private View v;
    private RecyclerView mrecyclerView;
    private MainAdapter mAdapter;

    private DatabaseReference mDatabaseRef;

    private List<Upload> mUploads;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_home, container, false);

        mrecyclerView=(RecyclerView) v.findViewById(R.id.main_recycler_view);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mUploads=new ArrayList<>();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postSnapshot:snapshot.getChildren()){
                    Upload upload=postSnapshot.getValue(Upload.class);
                    mUploads.add(upload); }
                mAdapter=new MainAdapter(getActivity(),mUploads);
                mrecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }


}