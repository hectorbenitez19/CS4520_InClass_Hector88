package com.example.cs4520_inclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.Serializable;

//HECTOR BENITEZ ASSIGNMENT 2

public class selectAvatar extends AppCompatActivity {
    ImageView female1, female2, female3, male1, male2, male3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);
        setTitle("Select Avatar");

        ImageView female1 = findViewById(R.id.female1);
        ImageView female2 = findViewById(R.id.female2);
        ImageView female3 = findViewById(R.id.female3);
        ImageView male1 = findViewById(R.id.male1);
        ImageView male2 = findViewById(R.id.male2);
        ImageView male3 = findViewById(R.id.male3);


        female1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackToProfile = new Intent();
                Drawable image = female1.getDrawable();
                goBackToProfile.putExtra("Avatar",  R.drawable.avatar_f_1);
                setResult(RESULT_OK, goBackToProfile);
                finish();
            }
        });

        female2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackToProfile = new Intent();
                Drawable image = female2.getDrawable();
                goBackToProfile.putExtra("Avatar",  R.drawable.avatar_f_2);
                setResult(RESULT_OK, goBackToProfile);
                finish();
            }
        });

        female3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackToProfile = new Intent();
                Drawable image = female3.getDrawable();
                goBackToProfile.putExtra("Avatar",  R.drawable.avatar_f_3);
                setResult(RESULT_OK, goBackToProfile);
                finish();
            }
        });

        male1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackToProfile = new Intent();
                Drawable image = male1.getDrawable();
                goBackToProfile.putExtra("Avatar",  R.drawable.avatar_m_1);
                setResult(RESULT_OK, goBackToProfile);
                finish();
            }
        });

        male2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackToProfile = new Intent();
                Drawable image = male2.getDrawable();
                goBackToProfile.putExtra("Avatar",  R.drawable.avatar_m_2);
                setResult(RESULT_OK, goBackToProfile);
                finish();
            }
        });

        male3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackToProfile = new Intent();
                Drawable image = male3.getDrawable();
                goBackToProfile.putExtra("Avatar",  R.drawable.avatar_m_3);
                setResult(RESULT_OK, goBackToProfile);
                finish();
            }
        });
    }
}