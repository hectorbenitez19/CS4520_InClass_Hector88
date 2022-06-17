package com.example.cs4520_inclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cs4520_inclass.InClass07.InClass07;
import com.example.cs4520_inclass.InClass08_and_InClass09.InClass08;

public class MainActivity extends AppCompatActivity {
    final String TAG = "demo";
    Button buttonPractice, buttonInClass01, buttonInClass02, buttonInClass03,buttonInClass04,
            buttonInClass05, buttonInClass07,buttonInClass08, buttonInClass09 ;
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

        buttonInClass05 = findViewById(R.id.InClass05);
        buttonInClass05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toInClass05 = new Intent(MainActivity.this, InClass05.class);
                startActivity(toInClass05);
            }
        });


        buttonInClass07 = findViewById(R.id.InClass07);
        buttonInClass07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toInClass07 = new Intent(MainActivity.this, InClass07.class);
                startActivity(toInClass07);
            }
        });


        buttonInClass08 = findViewById(R.id.InClass08);
        buttonInClass08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toInClass08 = new Intent(MainActivity.this, InClass08.class);
                startActivity(toInClass08);
            }
        });

        buttonInClass09 = findViewById(R.id.InClass09);
        buttonInClass09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toInClass09 = new Intent(MainActivity.this, InClass08.class);
                startActivity(toInClass09);
            }
        });
    }
}