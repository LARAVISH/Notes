package com.example.notes.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_menu, new NotesListFragment())
                    .commit();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_nav);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.action_notes:

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_menu, new NotesListFragment())
                            .commit();

                    return true;

                case R.id.action_info:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_menu, new AnyAPPFragment())
                            .commit();

                    return true;
            }
            return false;
        });
    }
}