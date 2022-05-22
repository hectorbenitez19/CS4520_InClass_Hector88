package com.example.cs4520_inclass;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

//HECTOR BENITEZ ASSIGNMENT 2

public class InClass02 extends AppCompatActivity {
final String TAG = "demo";
    Button submit;
    EditText nameText, emailText;
    ImageView profilePicture, emotionPicture;
    TextView error;
    SeekBar emotionBar;
    RadioGroup group;
    int avatar;


ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
        if(result.getResultCode() == RESULT_OK) {
            ImageView profilePicture = findViewById(R.id.imageButton);
            assert result.getData() != null;
            avatar =  result.getData().getIntExtra("Avatar", 0);
            profilePicture.setImageResource(avatar);
        }

    }
});


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class02);
        setTitle("Edit Profile Activity");


         submit = findViewById(R.id.submitButton);
         nameText = findViewById(R.id.ProfileNameText);
         emailText = findViewById(R.id.ProfileEmailText);
         profilePicture = findViewById(R.id.imageButton);
         error = findViewById(R.id.Error);
         emotionBar = findViewById(R.id.seekBar2);
         emotionPicture = findViewById(R.id.Emotion);
         group = findViewById(R.id.radioGroup);
         error.setText("");

         emotionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 if(progress == 0) {
                     emotionPicture.setImageResource(R.drawable.angry);
                 }
                 else if(progress == 1) {
                     emotionPicture.setImageResource(R.drawable.sad);
                 }
                 else if(progress == 2) {
                     emotionPicture.setImageResource(R.drawable.happy);
                 }
                 else if(progress == 3) {
                     emotionPicture.setImageResource(R.drawable.awesome);
                 }

             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {

             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {

             }
         });




        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileToAvatarSelect = new Intent(InClass02.this, selectAvatar.class);
                startActivityForResult.launch(profileToAvatarSelect);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error.setText("");
                int phoneType = group.getCheckedRadioButtonId();

                //toast error message need to input something
                if(nameText.getText().toString().isEmpty() || emailText.getText().toString().isEmpty() ) {
                    error.append("Error must input something for all fields");
                }

                //toast message error invalid email
                else if(!isValidEmailAddress(emailText.getText().toString())) {
                    error.append("Error must input a valid email address");

                }

                //toast error message must choose a phone type
                else if(phoneType == -1) {
                    error.append("Error must choose the type of phone you use");

                }

                else {
                    //once all fields have been filled in and validated package it up into an extra and send
                    //it to display activity
                    int mood = emotionBar.getProgress();
                    int radioid = group.getCheckedRadioButtonId();
                    ProfileInfo user = new ProfileInfo(nameText.getText().toString(), emailText.getText().toString(), mood, avatar, radioid);
                    Intent proflieToDisplay = new Intent(InClass02.this, DisplayActivity.class);
                    proflieToDisplay.putExtra("profile", user);
                    startActivity(proflieToDisplay);
                }

            }
        });


    }

    public boolean isValidEmailAddress(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}