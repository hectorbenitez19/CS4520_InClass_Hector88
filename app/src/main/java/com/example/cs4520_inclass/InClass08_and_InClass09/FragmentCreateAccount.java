package com.example.cs4520_inclass.InClass08_and_InClass09;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cs4520_inclass.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Base64;
import java.util.HashMap;

//Hector Benitez & Oliver Baer-Benson InClass Assignment 9

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCreateAccount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateAccount extends Fragment implements View.OnClickListener {

    private EditText firstname,lastname,display,email,password,confirmPassword;
    private Button createAccount;
    private ImageView profileImage;
    private ICreateUserFragEvent mListener;
    public static String TAG = "demo";
    private String image;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseFirestore database;



    public FragmentCreateAccount() {
        // Required empty public constructor
    }

    public static FragmentCreateAccount newInstance(String image) {
        FragmentCreateAccount fragment = new FragmentCreateAccount();
        Bundle args = new Bundle();
        args.putString(InClass08.IMAGE_KEY, image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        if (getArguments() != null) {
            image = getArguments().getString(InClass08.IMAGE_KEY);
        }

        database = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        profileImage = view.findViewById(R.id.a9_fragmentCreateAccount_profileImage);
        profileImage.setOnClickListener(this);
        profileImage.setImageResource(R.drawable.avatar_f_1);
        firstname = view.findViewById(R.id.a8_createAccountFrag_firstName);
        lastname = view.findViewById(R.id.a8_createAccountFrag_lastName);
        display = view.findViewById(R.id.a8_createAccountFrag_displayName);
        email = view.findViewById(R.id.a8_createAccountFrag_email);
        password = view.findViewById(R.id.a8_createAccountFrag_password);
        confirmPassword = view.findViewById(R.id.a8_createAccountFrag_confirmPassword);
        createAccount = view.findViewById(R.id.a8_createAccountFrag_createAccountButton);
        createAccount.setOnClickListener(this);

        try {
            Log.d(InClass08.TAG, "this is the image string " + image);
            byte[] b = Base64.getDecoder().decode(image);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            profileImage.setImageBitmap(bitmap);
        }catch(Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        String firstNameInput = firstname.getText().toString();
        String lastNameInput = lastname.getText().toString();
        String displayNameInput = display.getText().toString();
        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();
        String confirmPasswordInput = confirmPassword.getText().toString();

        if(v.getId() == R.id.a9_fragmentCreateAccount_profileImage) {
                mListener.takePicAndLogIn();
        }


        if(v.getId() == R.id.a8_createAccountFrag_createAccountButton) {

            if(firstNameInput.equals("")) {
                firstname.setError("First name cannot be empty!");
            }

            if(lastNameInput.equals("")) {
                lastname.setError("Last name cannot be empty!");
            }

            if(displayNameInput.equals("")) {
                display.setError("Display name cannot be empty!");
            }

            if(emailInput.equals("")) {
                email.setError("Email cannot be empty!");
            }

            if(passwordInput.equals("")) {
                password.setError("Password cannot be empty!");
            }

            if(!passwordInput.equals(confirmPasswordInput)) {
                firstname.setError("Password must be the same in both fields!");
            }

            if(!firstNameInput.equals("") && !lastNameInput.equals("") && !displayNameInput.equals("") &&
                    !emailInput.equals("") && !passwordInput.equals("") && passwordInput.equals(confirmPasswordInput) &&
                    !image.equals("")) {

                //creates the account
                mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mUser = task.getResult().getUser();

                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest
                                              .Builder()
                                              .setDisplayName(displayNameInput)
                                              .build();

                                    mUser.updateProfile(profileChangeRequest)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                      Log.d(TAG, "users display name successfully set");
                                                    }
                                                }
                                            });


                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");

                                    HashMap<String, Object> user = new HashMap<>();
                                    user.put(InClass08.FIRSTNAME_KEY, firstNameInput);
                                    user.put(InClass08.LASTNAME_KEY, lastNameInput);
                                    user.put(InClass08.DISPLAY_NAME_KEY, displayNameInput);
                                    user.put(InClass08.EMAIL_KEY, emailInput);
                                    user.put(InClass08.IMAGE_KEY, image);

                                    database.collection(InClass08.USERS_COLLECTION_KEY)
                                            .document(emailInput)
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    mListener.createdAccount(mUser);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    e.printStackTrace();
                                                }
                                            });

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

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentLogin.ILoginFragmentEvent) {
            mListener = (ICreateUserFragEvent) context;
        }
        else {
            throw new RuntimeException(context.toString() + " need to implement ICreateUserFragEvent");
        }
    }


public interface ICreateUserFragEvent {
        void createdAccount(FirebaseUser user);
        void takePicAndLogIn();
}
}