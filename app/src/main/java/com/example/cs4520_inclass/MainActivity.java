package com.example.cs4520_inclass;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    final String TAG = "demo";
    Button buttonPractice, buttonInClass01, buttonInClass02, buttonInClass03,buttonInClass04;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");

        buttonPractice = findViewById(R.id.Practice);
        buttonPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPractice = new Intent(MainActivity.this, PracticeActivity.class);
                startActivity(toPractice);
            }
        });
        buttonInClass01 = findViewById(R.id.InClass01);
        buttonInClass01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toInClass01 = new Intent(MainActivity.this, InClass01.class);
                startActivity(toInClass01);
            }
        });

        buttonInClass02 = findViewById(R.id.InClass02);
        buttonInClass02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toInClass02 = new Intent(MainActivity.this, InClass02.class);
                startActivity(toInClass02);
            }
        });

        buttonInClass03 = findViewById(R.id.InClass03);
        buttonInClass03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toInClass03 = new Intent(MainActivity.this, InClass03.class);
                startActivity(toInClass03);
            }
        });

        buttonInClass04 = findViewById(R.id.InClass04);
        buttonInClass04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toInClass04 = new Intent(MainActivity.this, InClass04.class);
                startActivity(toInClass04);
            }
        });
    }
}