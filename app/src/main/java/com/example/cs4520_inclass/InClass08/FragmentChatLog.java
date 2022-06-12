package com.example.cs4520_inclass.InClass08;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs4520_inclass.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChatLog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChatLog extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter messagesAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    public FragmentChatLog() {
        // Required empty public constructor
    }


    public static FragmentChatLog newInstance() {
        FragmentChatLog fragment = new FragmentChatLog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_log, container, false);

        recyclerView = view.findViewById(R.id.a8_chatLogRecyclerView);
        recyclerViewLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        ArrayList<Message> messages = new ArrayList<>();

        //Note: I made a ChatAdapter but didn't text it yet. It should mostly work though.
        //TODO: Get the chat log from the database, parse it into messages, and store it in an arraylist
        // Example of recyclerview code form main screen:
        /*
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

                            userAdapter = new UserAdapter(users, FragmentMainScreen.this);
                            recyclerView.setAdapter(userAdapter);
                        } else {
                            Log.d(InClass08.TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        */


        return view;
    }
}