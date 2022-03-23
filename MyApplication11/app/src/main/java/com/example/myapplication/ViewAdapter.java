package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments=new ArrayList<>();
    private List<String> fragmentsTitles=new ArrayList<>();

    public ViewAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {


        Fragment fragment =new Fragment();

       switch (position)
        {
            case 0:
                fragment= new metincozumleme();
                break;
            case 1:
               fragment =new fotocozumle();
                break;
            default:
                fragment= new metincozumleme();
                break;

        }
      //  return fragments.get(position);
        return fragment;

    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);

    }

}
