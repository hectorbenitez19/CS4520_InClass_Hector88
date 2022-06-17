package com.example.cs4520_inclass.InClass08_and_InClass09;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs4520_inclass.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditProfile extends Fragment {

    private String image;

    public FragmentEditProfile() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentEditProfile newInstance(String image) {
        FragmentEditProfile fragment = new FragmentEditProfile();
        Bundle args = new Bundle();
        args.putString(InClass08.IMAGE_KEY, image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            image = getArguments().getString(InClass08.IMAGE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }
}