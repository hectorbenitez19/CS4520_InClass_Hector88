package com.example.cs4520_inclass.InClass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs4520_inclass.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNotes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNotes extends Fragment implements NotesAdapter.IAdapterToNotes {

    Button logoutButton, finishButton;
    EditText noteInput;
    RecyclerView recyclerView;
    NotesAdapter adapter;



    IMakeNotes mListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentNotes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNotes.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNotes newInstance(String param1, String param2) {
        FragmentNotes fragment = new FragmentNotes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IMakeNotes){
            this.mListener = (IMakeNotes) context;
        }else{
            throw new RuntimeException(context.toString()+ "must implement IRegisterToNotes");
        }
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
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        logoutButton = view.findViewById(R.id.a7_FragmentNotes_logoutButton);
        finishButton = view.findViewById(R.id.a7_FragmentNotes_finishButton);
        noteInput = view.findViewById(R.id.a7_FragmentNotes_newMessageInput);
        recyclerView = view.findViewById(R.id.a7_FragmentNotes_recyclerView);

        mListener.getNotes();

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noteInput.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),"need to input a note", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d(InClass07.TAG,"going to make a note");
                    mListener.makeNote(noteInput.getText().toString());
                }

            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InClass07.ACCESS_TOKEN = "";
                mListener.logout();
            }
        });

        return view;
    }

    public interface IMakeNotes {
        void makeNote(String note);
        void deleteNote(String id);
        void logout();
        void getNotes();
    }
}