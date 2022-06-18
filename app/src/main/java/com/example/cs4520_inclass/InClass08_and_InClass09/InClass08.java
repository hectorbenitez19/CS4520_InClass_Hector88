package com.example.cs4520_inclass.InClass08_and_InClass09;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.example.cs4520_inclass.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

//Hector Benitez InClass Assignment 8

public class InClass08 extends AppCompatActivity implements FragmentLogin.ILoginFragmentEvent, FragmentCreateAccount.ICreateUserFragEvent, FragmentMainScreen.IFragmentMainFragEvent, UserAdapter.IchatUserClicked {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    public final static String FRAGMAINSCREEN = "Fragment main screen";
    public final static String FRAGLOGIN = "Fragment login screen";
    public final static String FRAGCREATEACCOUNT = "Fragment create account screen";
    public final static String FRAGMENTCHATLOG = "Fragment chat log screen";
    public final static String FRAGMENTEDITPROFILE = "Fragment edit profile screen";

    // Users Collection key
    public static final String USERS_COLLECTION_KEY = "User";
    public static final String CHAT_LOGS_COLLECTIONS_KEY = "Chat Logs";


    public final static String IMAGE_KEY = "image";
    public final static String NON_IMAGE = "false";
    public final static String REAL_IMAGE = "true";

    public String FRAGMENT_WE_CAME_FROM;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    //User field variables
    public static final String FIRSTNAME_KEY = "First Name";
    public static final String LASTNAME_KEY = "Last Name";
    public static final String DISPLAY_NAME_KEY = "Display";
    public static final String EMAIL_KEY = "Email";
    public static final String MESSAGE_KEY = "Message";

    public static final String SENDER_KEY = "senderEmail";
    public static final String MESSAGE_TEXT_KEY = "text";

    public static String TAG = "demo";

    private boolean isCamera;

    private Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class08);
        setTitle("InClass Assignment08");
        isCamera = false;

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    protected void onStart() {
        super.onStart();
        //get the current user and populate the screen
      //  currentUser = mAuth.getCurrentUser();
        display();
    }




    public void display() {
        //display error is that i haven't handled signing out yet so it is signed in until i
        //manually sign out and will automatically display the main screen with out login prompt

        currentUser = mAuth.getCurrentUser();

        if(isCamera) {
            isCamera = false;

        } else if(currentUser != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView4, new FragmentMainScreen(), FRAGMAINSCREEN)
                    .addToBackStack(null)
                    .commit();
        }

        else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView4, new FragmentLogin(), FRAGLOGIN)
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public void displayMainScreen(FirebaseUser user) {
        currentUser = user;
        display();
    }

    @Override
    public void displayCreateAccountScreen() {
        //the user needs to create an account so load FragmentCreateAccount fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView4, new FragmentCreateAccount(), FRAGCREATEACCOUNT)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void createdAccount(FirebaseUser user) {
        currentUser = user;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView4, new FragmentLogin(), FRAGLOGIN)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void backToLogin() {
      //  Log.d(TAG, "Currently logging out this user: " + mAuth.getCurrentUser().getDisplayName());
        mAuth.signOut();
      //  Log.d(TAG, "User is logged out should be null: " + mAuth.getCurrentUser().toString());
        display();
    }

    @Override
    public void goToEditProfile() {

    }

    @Override
    public void openChat(String email) {
        Log.d(TAG, "display chat log now");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView4, FragmentChatLog.newInstance(email), FRAGMENTCHATLOG)
                .addToBackStack(null)
                .commit();
    }

    public void takePicture(String fragmentTag) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            isCamera = true;
            FRAGMENT_WE_CAME_FROM = fragmentTag;
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d(InClass08.TAG, "Just took the picture");


            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
            byte[] b = stream.toByteArray();
            String image = Base64.encodeToString(b, Base64.NO_WRAP);
            Log.d(InClass08.TAG, "This is the image string we encode " + image);
            Bundle bundle = new Bundle();
            bundle.putString("test", image);
            returnToFragment(FRAGMENT_WE_CAME_FROM, image);
            
        }
    }

    public void returnToFragment(String fragTag, String image) {
        switch(FRAGMENT_WE_CAME_FROM) {
            case FRAGCREATEACCOUNT:
                Log.d(InClass08.TAG, "going back to create account now");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView4, FragmentCreateAccount.newInstance(image), FRAGCREATEACCOUNT)
                        .addToBackStack(null)
                        .commit();
                break;
            case FRAGMAINSCREEN:
                Log.d(InClass08.TAG, "going back to main screen now");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView4, FragmentMainScreen.newInstance(image), FRAGMAINSCREEN)
                        .addToBackStack(null)
                        .commit();
                break;
            case FRAGMENTEDITPROFILE:
                Log.d(InClass08.TAG, "going back to edit profile now");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView4, FragmentEditProfile.newInstance(image), FRAGMENTEDITPROFILE)
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                throw new IllegalArgumentException("Must be given the tag fro a supported fragment.");
        }
    }

    @Override
    public void testCamera() {
       // Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            takePicture(InClass08.FRAGMAINSCREEN);
      //      startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
              // returnToFragment(FRAGMENT_WE_CAME_FROM);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
            e.printStackTrace();
        }

    }


    @Override
    public void takePicAndLogIn() {
        FRAGMENT_WE_CAME_FROM = FRAGCREATEACCOUNT;
        takePicture(FRAGMENT_WE_CAME_FROM);
    }



}