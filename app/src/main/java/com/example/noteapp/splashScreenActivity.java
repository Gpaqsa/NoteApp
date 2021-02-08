package com.example.noteapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;



public class splashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_TIME = 3000;
    private TextView nameTextView;
    private TextView createrTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ActionBar actionBar = getSupportActionBar();
        if ( actionBar != null ) {
            actionBar.hide();
        }


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(splashScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 3000);


    }
}