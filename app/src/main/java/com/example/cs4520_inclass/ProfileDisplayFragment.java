package com.example.cs4520_inclass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//HECTOR BENITEZ ASSIGNMENT 3

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileDisplayFragment extends Fragment {

    ImageView avatar,emotion;
    TextView name,email,phone,mood;
    ProfileInfo info;
    Boolean Display = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";


    // TODO: Rename and change types of parameters
    private String displayName;
    private String displayEmail;
    private int displayAvatar;
    private int displayPhone;
    private int displayMood;

    public ProfileDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileDisplayFragment newInstance(String param1, String param2, int avatar, int phone, int mood) {
        ProfileDisplayFragment fragment = new ProfileDisplayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, avatar);
        args.putInt(ARG_PARAM4, phone);
        args.putInt(ARG_PARAM5, mood);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            displayName = getArguments().getString(ARG_PARAM1);
            displayEmail = getArguments().getString(ARG_PARAM2);
            displayAvatar = getArguments().getInt(ARG_PARAM3);
            displayPhone = getArguments().getInt(ARG_PARAM4);
            displayMood = getArguments().getInt(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View frag =  inflater.inflate(R.layout.fragment_profile_display, container, false);
        // Inflate the layout for this fragment

        avatar = frag.findViewById(R.id.displayAvatar2);
        emotion = frag.findViewById(R.id.displayMood2);
        name = frag.findViewById(R.id.displayName2);
        email = frag.findViewById(R.id.displayEmail2);
        phone = frag.findViewById(R.id.displayPhone2);
        mood = frag.findViewById(R.id.displayEmotion2);


        name.append("Name: " + displayName);
        email.append("Email: " + displayEmail);
        avatar.setImageResource(displayAvatar);

        if (displayPhone == R.id.Android) {
            phone.append("I use Android!");
        }
        if (displayPhone== R.id.IOS) {
            phone.append("I use IOS!");
        }
        if (displayMood == 0) {
            mood.append("I am Angry!");
            emotion.setImageResource(R.drawable.angry);
        }
        if (displayMood == 1) {
            mood.append("I am Sad!");
            emotion.setImageResource(R.drawable.sad);
        }
        if (displayMood == 2) {
            mood.append("I am Happy!");
            emotion.setImageResource(R.drawable.happy);
        }
        if (displayMood == 3) {
            mood.append("I am Awesome!");
            emotion.setImageResource(R.drawable.awesome);
        }


        return frag;
    }

}