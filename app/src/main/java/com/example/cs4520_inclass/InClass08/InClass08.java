package com.example.cs4520_inclass.InClass08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.cs4520_inclass.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InClass08 extends AppCompatActivity implements FragmentLogin.ILoginFragmentEvent, FragmentCreateAccount.ICreateUserFragEvent, FragmentMainScreen.IFragmentMainFragEvent, UserAdapter.IchatUserClicked {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    public final static String FRAGMAINSCREEN = "Fragment main screen";
    public final static String FRAGLOGIN = "Fragment login screen";
    public final static String FRAGCREATEACCOUNT = "Fragment create account screen";
    public final static String FRAGMENTCHATLOG = "Fragment chat log screen";

    // Users Collection key
    public static final String USERS_COLLECTION_KEY = "User";
    public static final String CHAT_LOGS_COLLECTIONS_KEY = "Chat Logs";


    //User field variables
    public static final String FIRSTNAME_KEY = "First Name";
    public static final String LASTNAME_KEY = "Last Name";
    public static final String DISPLAY_NAME_KEY = "Display";
    public static final String EMAIL_KEY = "Email";
    public static final String MESSAGE_KEY = "Message";

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
      //  currentUser = mAuth.getCurrentUser();
        display();
    }




    public void display() {
        //display error is that i haven't handled signing out yet so it is signed in until i
        //manually sign out and will automatically display the main screen with out login prompt

      //  Log.d(TAG, mAuth.getCurrentUser().getDisplayName());
        currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
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
    public void openChat(String email) {
        Log.d(TAG, "display chat log now");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView4, FragmentChatLog.newInstance(email), FRAGMENTCHATLOG)
                .addToBackStack(null)
                .commit();
    }
}