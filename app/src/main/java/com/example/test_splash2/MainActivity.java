package com.example.test_splash2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tagtag_", "onCreate called. savedInstanceState: " + savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("tagtag_","1");

    }
}