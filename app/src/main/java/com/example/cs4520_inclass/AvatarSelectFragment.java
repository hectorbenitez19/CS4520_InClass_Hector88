package com.example.cs4520_inclass;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

//HECTOR BENITEZ ASSIGNMENT 3

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvatarSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvatarSelectFragment extends Fragment {

    IAvatarToMain sendId;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AvatarSelectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvatarSelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvatarSelectFragment newInstance(String param1, String param2) {
        AvatarSelectFragment fragment = new AvatarSelectFragment();
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
        View frag = inflater.inflate(R.layout.fragment_avatar_select, container, false);

        ImageView female1 = frag.findViewById(R.id.female1Frag);
        ImageView female2 = frag.findViewById(R.id.female2Frag);
        ImageView female3 = frag.findViewById(R.id.female3Frag);
        ImageView male1 = frag.findViewById(R.id.male1Frag);
        ImageView male2 = frag.findViewById(R.id.male2Frag);
        ImageView male3 = frag.findViewById(R.id.male3Frag);


        female1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendId.backToMain(R.drawable.avatar_f_1);
            }
        });

        female2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendId.backToMain(R.drawable.avatar_f_2);
            }
        });

        female3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendId.backToMain(R.drawable.avatar_f_3);
            }
        });

        male1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendId.backToMain(R.drawable.avatar_m_1);
            }
        });

        male2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendId.backToMain(R.drawable.avatar_m_2);
            }
        });

        male3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendId.backToMain(R.drawable.avatar_m_3);
            }
        });

        return frag;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IAvatarToMain) {
            sendId = (IAvatarToMain) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IAvatarToMain");
        }
    }

    public interface IAvatarToMain {
        void backToMain(int avatarId);
    }
}