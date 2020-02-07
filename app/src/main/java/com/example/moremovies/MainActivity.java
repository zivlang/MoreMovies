package com.example.moremovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment mlf = new MoviesListFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragments_container, mlf);
        ft.commit();
    }
}
