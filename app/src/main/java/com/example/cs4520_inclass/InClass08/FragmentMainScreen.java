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
import android.widget.EditText;
import android.widget.TextView;

import com.example.cs4520_inclass.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMainScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMainScreen extends Fragment implements View.OnClickListener {
        TextView welcomeText;
        Button logoutButton, testButton;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private IFragmentMainFragEvent mListener;

    private FirebaseFirestore database;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentMainScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMainScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMainScreen newInstance(String param1, String param2) {
        FragmentMainScreen fragment = new FragmentMainScreen();
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
        mUser = mAuth.getCurrentUser();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        welcomeText = view.findViewById(R.id.a8_fragmentMainScreen_welcomeText);
        logoutButton = view.findViewById(R.id.a8_fragmentMainScreen_logutButton);
        testButton = view.findViewById(R.id.a8_testAuthButton);
        testButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        welcomeText.append("Welcome: " + mUser.getDisplayName());

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.a8_fragmentMainScreen_logutButton) {
          //  mAuth.signOut();
            mListener.backToLogin();
        }

        else if(v.getId() == R.id.a8_testAuthButton) {
            String test = "testing adding stuff with authentication";
            Map<String, Object> testEntry = new HashMap<>();
            testEntry.put("test", test);
            database.collection("test1").document("test2Document").set(testEntry)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(InClass08.TAG, "the data was successfully entered into the database");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
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


    public interface IFragmentMainFragEvent {
        void backToLogin();
    }
}