package com.example.weatherappjava.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.weatherappjava.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction().replace(R.id.conteiner, new WeatherFragment())
                .commit();
    }
}