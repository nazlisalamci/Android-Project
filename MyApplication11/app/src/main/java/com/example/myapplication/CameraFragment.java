package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class CameraFragment extends Fragment {

    private View view;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private metincozumleme metincozumleme;
    private EditText editTextTextMultiLine;
    private MainActivity2 mainActivity2;
    private ViewAdapter viewAdapter;
    private String in;


    void init(){
        tabLayout= view.findViewById(R.id.tablayout);
        viewPager2= view.findViewById(R.id.viewpager);
        mainActivity2=new MainActivity2();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_camera, container, false);
          init();


        viewAdapter=new ViewAdapter(getActivity().getSupportFragmentManager(),getLifecycle());
        viewAdapter.addFragment(new metincozumleme());
        viewAdapter.addFragment(new fotocozumle());
        viewPager2.setAdapter(viewAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position){
                    case 0:
                        tab.setText("text çözümle");
                        break;
                    case 1:
                        tab.setText("camera");
                        break;

                }
            }
        }).attach();




        return view;
    }

}