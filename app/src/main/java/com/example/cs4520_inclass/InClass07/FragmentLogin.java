package com.example.cs4520_inclass.InClass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs4520_inclass.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {

    Button loginButton, registerButton;
    EditText emailInput, passwordInput;
    public static OkHttpClient client;
    public static final String TAG = "demo";

    private ILoginToNotes mListener;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login2, container, false);

        loginButton = view.findViewById(R.id.a7_InClass07_loginButton);
        registerButton = view.findViewById(R.id.a7_InClass07_registerButton);
        emailInput = view.findViewById(R.id.a7_InClass07_emailInput);
        passwordInput = view.findViewById(R.id.a7_InClass07_passwordInput);


        HttpUrl url;
        client = new OkHttpClient();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailInput.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Need to input a valid email", Toast.LENGTH_SHORT).show();
                }

                if(passwordInput.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Need to input a valid password", Toast.LENGTH_SHORT).show();
                }

                if(!emailInput.getText().toString().isEmpty() && !passwordInput.getText().toString().isEmpty()) {
                    mListener.login(emailInput.getText().toString(), passwordInput.getText().toString());

                }

            }
        });



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.toRegister();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ILoginToNotes){
            this.mListener = (ILoginToNotes) context;
        }else{
            throw new RuntimeException(context.toString()+ "must implement IRegisterToNotes");
        }
    }

    public interface ILoginToNotes {
        void toRegister();
        void login(String email, String password);
    }

}