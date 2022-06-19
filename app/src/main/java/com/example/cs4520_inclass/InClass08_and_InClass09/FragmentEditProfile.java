package com.example.cs4520_inclass.InClass08_and_InClass09;

//Hector Benitez & Oliver Baer-Benson InClass Assignment 9

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

import com.example.cs4520_inclass.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Base64;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditProfile extends Fragment {

    private String image;
    private String email;
    private EditText firstname,lastname,display;
    private Button editAccount;
    private ImageView profileImage;
    private IEditUserFragEvent mListener;

    String oldFirst, oldLast, oldDisplay;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseFirestore database;

    public FragmentEditProfile() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentEditProfile newInstance(String email) {
        FragmentEditProfile fragment = new FragmentEditProfile();
        Bundle args = new Bundle();
        args.putString(InClass08.EMAIL_KEY, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        if (getArguments() != null) {
            email = getArguments().getString(InClass08.EMAIL_KEY);
        }

        database = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        database.collection(InClass08.USERS_COLLECTION_KEY).document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> user = task.getResult().getData();
                    oldFirst = (String) user.get(InClass08.FIRSTNAME_KEY);
                    oldLast = (String) user.get(InClass08.LASTNAME_KEY);
                    oldDisplay = (String) user.get(InClass08.DISPLAY_NAME_KEY);
                    image = (String) user.get(InClass08.IMAGE_KEY);
                    firstname.setText(oldFirst);
                    lastname.setText(oldLast);
                    display.setText(oldDisplay);

                    try {
                        Log.d(InClass08.TAG, "this is the image string " + image);
                        byte[] b = Base64.getDecoder().decode(image);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                        profileImage.setImageBitmap(bitmap);
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(InClass08.TAG, "Failed to set data correctly.");
                }
            }
        });

        profileImage = view.findViewById(R.id.a9_fragmentEditAccount_profileImage);
        //profileImage.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        mListener.takePicAndEdit();
        //    }
        //});
        profileImage.setImageResource(R.drawable.avatar_f_1);
        firstname = view.findViewById(R.id.a8_editAccountFrag_firstName);
        lastname = view.findViewById(R.id.a8_editAccountFrag_lastName);
        display = view.findViewById(R.id.a8_editAccountFrag_displayName);

        editAccount = view.findViewById(R.id.a8_editAccountFrag_editAccountButton);
        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUser("", firstname.getText().toString(), lastname.getText().toString(), display.getText().toString(), email);
                mListener.editProfileDone();
            }
        });

        return view;
    }

    public void editUser(String image, String firstname, String lastname, String displayname, String email) {

        try {
            Log.d(InClass08.TAG, "this is the image string " + image);
            byte[] b = Base64.getDecoder().decode(image);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            profileImage.setImageBitmap(bitmap);
        }catch(Exception e) {
            e.printStackTrace();
        }

        database.collection(InClass08.USERS_COLLECTION_KEY).document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> user = task.getResult().getData();
                    if (!firstname.equals("")) {
                        user.put(InClass08.FIRSTNAME_KEY, firstname);
                    }
                    if (!lastname.equals("")) {
                        user.put(InClass08.LASTNAME_KEY, lastname);
                    }
                    if (!displayname.equals("")) {
                        user.put(InClass08.DISPLAY_NAME_KEY, displayname);
                    }
                    //if (!image.equals("")) {
                    //    user.put(InClass08.IMAGE_KEY, image);
                    //}


                    database.collection(InClass08.USERS_COLLECTION_KEY)
                            .document(email)
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(InClass08.TAG, "Successfully edited profile");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                }
                            });
                } else {
                    Log.d(InClass08.TAG, "Failed to edit profile.");
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentEditProfile.IEditUserFragEvent) {
            mListener = (FragmentEditProfile.IEditUserFragEvent) context;
        }
        else {
            throw new RuntimeException(context.toString() + " need to implement ICreateUserFragEvent");
        }
    }

    public interface IEditUserFragEvent {
        public void takePicAndEdit();
        public void editProfileDone();
    }
}