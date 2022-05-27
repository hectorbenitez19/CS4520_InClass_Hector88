package com.example.cs4520_inclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

//HECTOR BENITEZ ASSIGNMENT 3

public class InClass03 extends AppCompatActivity implements ProfileEditFragment.IEditFragToMain, AvatarSelectFragment.IAvatarToMain {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class03);
        setTitle("In-Class Assignment 3");


        getSupportFragmentManager().beginTransaction()
                .add(R.id.ContainerFragment, new ProfileEditFragment(), "editProfile")
                .addToBackStack("edit")
                .commit();
    }

    @Override
    public void goToAvatar(Boolean goBack) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ContainerFragment, new AvatarSelectFragment(),"avatarSelect")
                .addToBackStack("avatar")
                .commit();
    }

    @Override
    public void goToDisplay(ProfileInfo data, Boolean display) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ContainerFragment, ProfileDisplayFragment.newInstance(data.name,data.email,data.avatar,data.phoneType,data.mood), "display")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void backToMain(int avatarId) {
        //send data to edit profile fragment
        Fragment frag = getSupportFragmentManager().findFragmentByTag("editProfile");
        assert frag != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.ContainerFragment,frag).commit();

        ProfileEditFragment editprofile = (ProfileEditFragment) getSupportFragmentManager().findFragmentByTag("editProfile");
        assert editprofile != null;
        editprofile.setAvatar(avatarId, true);

    }


}