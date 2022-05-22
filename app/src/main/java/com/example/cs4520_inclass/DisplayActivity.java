package com.example.cs4520_inclass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
//HECTOR BENITEZ ASSIGNMENT 2

public class DisplayActivity extends AppCompatActivity {

    ImageView avatar,emotion;
    TextView name,email,phone,mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        setTitle("Display Activity");
        avatar = findViewById(R.id.displayAvatar);
        emotion = findViewById(R.id.displayMood);
        name = findViewById(R.id.displayName);
        email = findViewById(R.id.displayEmail);
        phone = findViewById(R.id.displayPhone);
        mood = findViewById(R.id.displayEmotion);

        if(getIntent() != null && getIntent().getExtras() != null) {
            ProfileInfo info = (ProfileInfo) getIntent().getParcelableExtra("profile");

            name.append("Name: " + info.name);
            email.append("Email: " + info.email);
            avatar.setImageResource(info.avatar);

            if(info.phoneType == R.id.Android) {
                phone.append("I use Android!");
            }
             if(info.phoneType == R.id.IOS) {
                phone.append("I use IOS!");
            }
             if(info.mood == 0) {
                 mood.append("I am Angry!");
                 emotion.setImageResource(R.drawable.angry);
             }
            if(info.mood == 1) {
                mood.append("I am Sad!");
                emotion.setImageResource(R.drawable.sad);
            }
            if(info.mood == 2) {
                mood.append("I am Happy!");
                emotion.setImageResource(R.drawable.happy);
            }
            if(info.mood == 3) {
                mood.append("I am Awesome!");
                emotion.setImageResource(R.drawable.awesome);
            }
        }



    }
}