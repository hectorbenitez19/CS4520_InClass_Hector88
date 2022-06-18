package com.example.cs4520_inclass.InClass08_and_InClass09;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cs4520_inclass.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Base64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditProfile extends Fragment {

    private String image;
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
    public static FragmentEditProfile newInstance(String image, String oldFirst, String oldLast, String oldDisplay) {
        FragmentEditProfile fragment = new FragmentEditProfile();
        Bundle args = new Bundle();
        args.putString(InClass08.IMAGE_KEY, image);
        args.putString(InClass08.FIRSTNAME_KEY, oldFirst);
        args.putString(InClass08.LASTNAME_KEY, oldLast);
        args.putString(InClass08.DISPLAY_NAME_KEY, oldDisplay);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        if (getArguments() != null) {
            image = getArguments().getString(InClass08.IMAGE_KEY);
            oldFirst = getArguments().getString(InClass08.FIRSTNAME_KEY);
            oldLast = getArguments().getString(InClass08.LASTNAME_KEY);
            oldDisplay = getArguments().getString(InClass08.DISPLAY_NAME_KEY);
        }

        database = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        profileImage = view.findViewById(R.id.a9_fragmentEditAccount_profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        profileImage.setImageResource(R.drawable.avatar_f_1);
        firstname = view.findViewById(R.id.a8_editAccountFrag_firstName);
        firstname.setText(oldFirst);
        lastname = view.findViewById(R.id.a8_editAccountFrag_lastName);
        lastname.setText(oldLast);
        display = view.findViewById(R.id.a8_editAccountFrag_displayName);
        display.setText(oldDisplay);

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

    public interface IEditUserFragEvent {
        public void sendEdits(String newFirst, String newLast, String newDisplay, String newImage);
    }
}