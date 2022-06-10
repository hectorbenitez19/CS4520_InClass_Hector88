package com.example.cs4520_inclass.InClass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs4520_inclass.InClass05;
import com.example.cs4520_inclass.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCreateAccount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateAccount extends Fragment implements View.OnClickListener {

    private EditText firstname,lastname,display,email,password,confirmPassword;
    private Button createAccount;
    private ICreateUserFragEvent mListener;
    public static String TAG = "demo";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseFirestore database;

    // Users Collection key
    public static final String USERS_COLLECTION_KEY = "users_collection_key";

    //User field variables
    public static final String FIRSTNAME_KEY = "firstname_key";
    public static final String LASTNAME_KEY = "lastname_key";
    public static final String DISPLAY_NAME_KEY = "display_name_key";
    public static final String EMAIL_KEY = "email_key";

    public FragmentCreateAccount() {
        // Required empty public constructor
    }

    public static FragmentCreateAccount newInstance() {
        FragmentCreateAccount fragment = new FragmentCreateAccount();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (getArguments() != null) {
        }

        database = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        firstname = view.findViewById(R.id.a8_createAccountFrag_firstName);
        lastname = view.findViewById(R.id.a8_createAccountFrag_lastName);
        display = view.findViewById(R.id.a8_createAccountFrag_displayName);
        email = view.findViewById(R.id.a8_createAccountFrag_email);
        password = view.findViewById(R.id.a8_createAccountFrag_password);
        confirmPassword = view.findViewById(R.id.a8_createAccountFrag_confirmPassword);
        createAccount = view.findViewById(R.id.a8_createAccountFrag_createAccountButton);
        createAccount.setOnClickListener(this);

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
                    !emailInput.equals("") && !passwordInput.equals("") && passwordInput.equals(confirmPasswordInput)) {

                //creates the account
                mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mUser = task.getResult().getUser();

                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");

                                    HashMap<String, Object> user = new HashMap<>();
                                    user.put(FIRSTNAME_KEY, firstNameInput);
                                    user.put(LASTNAME_KEY, lastNameInput);
                                    user.put(DISPLAY_NAME_KEY, displayNameInput);
                                    user.put(EMAIL_KEY, emailInput);

                                    database.collection(USERS_COLLECTION_KEY).add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(InClass08.TAG, "User with ID " + documentReference.getId()
                                                    + " successfully added to database");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            e.printStackTrace();
                                            Log.w(InClass08.TAG, "Error adding document.", e);
                                        }
                                    });

                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest
                                            .Builder()
                                            .setDisplayName(displayNameInput)
                                                    .build();

                                    mUser.updateProfile(profileChangeRequest)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                mListener.createdAccount(mUser);
                                            }
                                        }
                                    });

                                    database.collection(USERS_COLLECTION_KEY).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Log.d(InClass08.TAG, document.getId() + " -> " + document.getData());
                                                }
                                            } else {
                                                Log.w(InClass08.TAG, "Error retrieving the documents.", task.getException());
                                            }
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
}

}