package com.example.cs4520_inclass.InClass08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cs4520_inclass.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InClass08 extends AppCompatActivity implements FragmentLogin.ILoginFragmentEvent {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    public final static String FRAGMAINSCREEN = "Fragment main screen";
    public final static String FRAGLOGIN = "Fragment login screen";
    public final static String FRAGCREATEACCOUNT = "Fragment create account screen";
    public static String TAG = "demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class08);
        setTitle("InClass Assignment08");


        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    protected void onStart() {
        super.onStart();
        //get the current user and populate the screen
        currentUser = mAuth.getCurrentUser();
        display();
    }




    public void display() {
        if(currentUser != null){
            //the user logged in so load the FragmentMainScreen
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.a8_fragmentMainScreen_screen, new FragmentMainScreen(), FRAGMAINSCREEN)
                    .commit();

        }

        else {
            //the user hasn't logged in yet load the FragmentLogin fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.a8_fragmentMainScreen_screen, new FragmentLogin(), FRAGLOGIN)
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
                .replace(R.id.a8_fragmentMainScreen_screen, new FragmentCreateAccount(), FRAGCREATEACCOUNT)
                .commit();
    }
}