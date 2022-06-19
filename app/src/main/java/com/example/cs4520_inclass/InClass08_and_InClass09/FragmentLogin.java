package com.example.cs4520_inclass.InClass08_and_InClass09;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs4520_inclass.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

//Hector Benitez & Oliver Baer-Benson InClass Assignment 9

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment implements View.OnClickListener{

    private EditText username, password;
    private Button loginButton, createAccountButton;
    private ILoginFragmentEvent mListener;
    public static String TAG = "demo";

    private FirebaseAuth mAuth;
    private FirebaseFirestore database;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogin newInstance(String param1, String param2) {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        username = view.findViewById(R.id.a8_fragLogin_username);
        password = view.findViewById(R.id.a8_fragLogin_password);
        loginButton = view.findViewById(R.id.a8_fragLogin_loginButton);
        createAccountButton = view.findViewById(R.id.a8_fragLogin_CreateAccountButton);
        loginButton.setOnClickListener(this);
        createAccountButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.a8_fragLogin_loginButton) {
            String usernameEntry = username.getText().toString();
            String passwordEntry = password.getText().toString();

            if(usernameEntry.equals("")) {
                username.setError("username cannot be empty!");
            }
            if(passwordEntry.equals("")) {
                password.setError("password cannot be empty!");
            }

            if(!usernameEntry.equals("") && !passwordEntry.equals("")) {
                mAuth.signInWithEmailAndPassword(usernameEntry, passwordEntry)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                  mListener.displayMainScreen(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }

        else if(v.getId() == R.id.a8_fragLogin_CreateAccountButton) {
            mListener.displayCreateAccountScreen();
        }

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ILoginFragmentEvent) {
            mListener = (ILoginFragmentEvent) context;
        }
        else {
            throw new RuntimeException(context.toString() + " need to implement ILoginFragmentEvent");
        }
    }

    public interface ILoginFragmentEvent {
         void displayMainScreen(FirebaseUser user);
         void displayCreateAccountScreen();
    }
}