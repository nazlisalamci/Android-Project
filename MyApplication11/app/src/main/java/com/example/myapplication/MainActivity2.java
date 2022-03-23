package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity2 extends AppCompatActivity {

    private FirebaseAuth mAuth;

    BottomNavigationView bottomNavigationView;
    MaterialToolbar toolbar;

    HomeFragment homeFragment=new HomeFragment();
    SettingsFragment settingsFragment=new SettingsFragment();
    CameraFragment cameraFragment=new CameraFragment();
    ProfileFragment profileFragment=new ProfileFragment();




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        getMenuInflater().inflate(R.menu.topbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==(R.id.close)){
            mAuth.signOut();
            Toast.makeText(getApplicationContext(),"oturum kapatıldı",Toast.LENGTH_SHORT).show();
            Intent loginIntent=new Intent(MainActivity2.this,LoginActivity.class);
            startActivity(loginIntent);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        mAuth=FirebaseAuth.getInstance();

        toolbar=findViewById(R.id.tolbar);

        bottomNavigationView=findViewById(R.id.navigationbar);
        bottomNavigationView.setItemIconTintList(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.camera:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,cameraFragment).commit();
                        return true;
                    case R.id.close:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment).commit();
                        return true;


                }
                return false;
            }
        });

    }
}

