package com.example.cs4520_inclass.InClass08_and_InClass09;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cs4520_inclass.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Base64;

//Hector Benitez & Oliver Baer-Benson InClass Assignment 9

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMainScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMainScreen extends Fragment implements View.OnClickListener,
        UserAdapter.IchatUserClicked {
        TextView welcomeText;
        Button logoutButton,editButton;
    ImageView testView;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private IFragmentMainFragEvent mListener;

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ArrayList<String> users;

    private static  String testImageString;
    private FirebaseFirestore database;
    private static final String ARG_PARAM1 = "param1";

    public FragmentMainScreen() {
        // Required empty public constructor
    }

    public static FragmentMainScreen newInstance(String param1) {
        FragmentMainScreen fragment = new FragmentMainScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

        if (getArguments() != null) {
            testImageString = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        welcomeText = view.findViewById(R.id.a8_fragmentMainScreen_welcomeText);
        logoutButton = view.findViewById(R.id.a8_fragmentMainScreen_logutButton);
        logoutButton.setOnClickListener(this);
        welcomeText.append("Welcome: " + mUser.getDisplayName());

        editButton = view.findViewById(R.id.a8_fragmentMainScreen_editButton);
        editButton.setOnClickListener(this);

        try {
            Log.d(InClass08.TAG, "this is the image string " + testImageString);
            byte[] b = Base64.getDecoder().decode(testImageString);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            testView.setImageBitmap(bitmap);
        }catch(Exception e) {
            e.printStackTrace();
        }


        recyclerView = view.findViewById(R.id.a8_mainRecyclerView);
        recyclerViewLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setOnClickListener(this);

        users = new ArrayList<>();
        database.collection(InClass08.USERS_COLLECTION_KEY)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                users.add(document.getString(InClass08.EMAIL_KEY).toLowerCase());
                            }
                            users.remove(mUser.getEmail());

                            userAdapter = new UserAdapter(users, getContext());
                            recyclerView.setAdapter(userAdapter);
                        } else {
                            Log.d(InClass08.TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.a8_fragmentMainScreen_logutButton) {
            mListener.backToLogin();
        }


        if(v.getId() == R.id.a8_fragmentMainScreen_editButton) {
            mListener.goToEditProfile();
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IFragmentMainFragEvent) {
            mListener = (IFragmentMainFragEvent) context;
        }
        else {
            throw new RuntimeException(context.toString() + " need to implement ICreateUserFragEvent");
        }
    }

    @Override
    public void openChat(String email) {
        Log.d(InClass08.TAG, "Button clicked, user given is " + email);
    }


    public interface IFragmentMainFragEvent {
        void backToLogin();
        void goToEditProfile();
        void testCamera();
    }
}