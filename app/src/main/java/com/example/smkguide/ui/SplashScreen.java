package com.example.smkguide.ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smkguide.MainActivity;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


        finish();

    }

}
