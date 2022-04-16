package com.example.notes.ui;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.notes.R;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements ToolBarHolder {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.draw_layout);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_setting:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new SettingAppFragment())
                            .addToBackStack("setting")
                            .commit();
                    drawerLayout.close();
                    return true;
                case R.id.action_profile:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new AnyAPPFragment())
                            .addToBackStack("profile")
                            .commit();
                    drawerLayout.close();
                    return true;
            }

            return false;
        });

    }

    @Override
    public void setToolBar(Toolbar toolBar) {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolBar, R.string.drawer_open, R.string.drawer_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}