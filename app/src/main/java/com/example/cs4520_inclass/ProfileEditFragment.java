package com.example.cs4520_inclass;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

//HECTOR BENITEZ ASSIGNMENT 3

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileEditFragment extends Fragment {

    IEditFragToMain sendData;
    Button submit;
    EditText nameText, emailText;
    ImageView profilePicture, emotionPicture;
    TextView error;
    SeekBar emotionBar;
    RadioGroup group;
    int avatar;
    int image;
    boolean pasteImage = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "email";
    private static final String ARG_PARAM3 = "phonetype";
    private static final String ARG_PARAM4 = "moodlevel";

    // TODO: Rename and change types of parameters
    private String name;
    private String email;
    private int phonetype;
    private int moodlevel;

    public ProfileEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileEditFragment newInstance(String param1, String param2, int param3, int param4) {
        ProfileEditFragment fragment = new ProfileEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        args.putInt(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            email = getArguments().getString(ARG_PARAM2);
            phonetype = getArguments().getInt(ARG_PARAM3);
            moodlevel = getArguments().getInt(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragView = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        submit = fragView.findViewById(R.id.submitButton2);
        nameText = fragView.findViewById(R.id.profileNameText2);
        emailText = fragView.findViewById(R.id.profileEmailText2);
        profilePicture = fragView.findViewById(R.id.imageButtonFrag);
        error = fragView.findViewById(R.id.error2);
        emotionBar = fragView.findViewById(R.id.seekBarFrag);
        emotionPicture = fragView.findViewById(R.id.emotion2);
        group = fragView.findViewById(R.id.radioGroup);
        error.setText("");

        nameText.setText(name);
        emailText.setText(email);
        group.check(phonetype);
        emotionBar.setProgress(moodlevel);


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
                sendData.goToAvatar(true);

            }
        });

        if(pasteImage) {
            profilePicture.setImageResource(image);
        }


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
                    ProfileInfo user = new ProfileInfo(nameText.getText().toString(), emailText.getText().toString(), mood, image, radioid);
                    sendData.goToDisplay(user, true);

                }

            }
        });


        return fragView;
    }

    public void setAvatar(int avatarid, boolean selectedImage) {
        image = avatarid;
        pasteImage = selectedImage;

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IEditFragToMain) {
            sendData = (IEditFragToMain) context;
        }
        else {
            throw new RuntimeException(context.toString() + "must implement IEditFragToMain");
        }

    }


    public interface IEditFragToMain {
        void goToAvatar(Boolean goBack);
        void goToDisplay(ProfileInfo data, Boolean display);
    }

    public boolean isValidEmailAddress(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}