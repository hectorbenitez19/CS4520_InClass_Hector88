package com.example.cs4520_inclass;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PracticeActivity extends AppCompatActivity implements View.OnClickListener {
    final String TAG = "demo";
    Button logcatButton, toastButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        logcatButton = findViewById(R.id.logcat);
        toastButton = findViewById(R.id.toast);

        logcatButton.setOnClickListener(this);
        toastButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logcat) {
            Log.d(TAG, "Practice!Practice!Practice!");
        }

        if(v.getId() == R.id.toast ) {
            Log.d(TAG, "Now push to GitHub and Submit!");
        }
    }
}