package com.example.cs4520_inclass.InClass08_and_InClass09;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs4520_inclass.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Hector Benitez & Oliver Baer-Benson InClass Assignment 9


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChatLog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChatLog extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ChatAdapter messagesAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ImageButton sendButton;
    private EditText messageInput;
    private TextView friendDisplayName;

    // INVARIANT: After this is set, it doesn't change (cant be final cause we can't set it in the constructor)
    public String ONE_ON_ONE_CHAT_KEY;

    private FirebaseFirestore database;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private static final String ARG_PARAM1 = "param1";
    private String userEmail;
    private String friendEmail;

    private ArrayList messages;


    public FragmentChatLog() {
        // Required empty public constructor
    }


    public static FragmentChatLog newInstance(String fEmail) {
        FragmentChatLog fragment = new FragmentChatLog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, fEmail);
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
                friendEmail = getArguments().getString(ARG_PARAM1);

            userEmail = mUser.getEmail();
            assert userEmail != null;
            if(userEmail.compareTo(friendEmail) > 0) {
                ONE_ON_ONE_CHAT_KEY = userEmail + friendEmail;
            } else if(userEmail.compareTo(friendEmail) < 0) {
                ONE_ON_ONE_CHAT_KEY = friendEmail + userEmail;
            } else {
                Log.d(InClass08.TAG, "userEmail: " + userEmail + ", friendEmail: " + friendEmail);
                throw new IllegalStateException("User should not be messaging themself.");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_log, container, false);

        friendDisplayName = view.findViewById(R.id.a8_chatNameTextView);
        friendDisplayName.setText(friendEmail);

        recyclerView = view.findViewById(R.id.a8_chatLogRecyclerView);
        sendButton = view.findViewById(R.id.a8_FragmentChat_sendButton);
        sendButton.setOnClickListener(this);
        messageInput = view.findViewById(R.id.a8_FragmentChat_messageInput);
        recyclerViewLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        messagesAdapter = new ChatAdapter(new ArrayList<>(), userEmail);
        recyclerView.setAdapter(messagesAdapter);

        messages = new ArrayList();

        //Note: I made a ChatAdapter but didn't text it yet. It should mostly work though.
        //TODO: Get the chat log from the database, parse it into messages, and store it in an arraylist
        // Example of recyclerview code form main screen:

        DocumentReference docRef = database.collection(InClass08.CHAT_LOGS_COLLECTIONS_KEY)
                .document(ONE_ON_ONE_CHAT_KEY);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            Object hopefullyMessages = document.get(InClass08.MESSAGE_KEY);

                            if (hopefullyMessages == null) {
                                //there arent any messages so initialize the field in the database
                                HashMap<String,String> emptyEntry = new HashMap<String, String>();
                                emptyEntry.put(InClass08.SENDER_KEY, "");
                                emptyEntry.put(InClass08.MESSAGE_TEXT_KEY, "");
                                Map<String, Object> emptyArray = new HashMap<String, Object>();
                                List<HashMap<String, String>> array = new ArrayList();
                                emptyArray.put(InClass08.MESSAGE_KEY, array);

                                database.collection(InClass08.CHAT_LOGS_COLLECTIONS_KEY)
                                        .document(ONE_ON_ONE_CHAT_KEY)
                                        .set(emptyArray).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d(InClass08.TAG, "initialized the Message field successfully");
                                            }
                                        });
                            } else {
                                    //the database already has messages
                                    List<HashMap<String, String>> temp = (List<HashMap<String, String>>) hopefullyMessages;
                                    messages = new ArrayList(temp);

                                    messagesAdapter = new ChatAdapter(messages, userEmail);
                                    recyclerView.setAdapter(messagesAdapter);
                            }

                            updateRecyclerView();
                        } else {
                            Log.d(InClass08.TAG, "Error getting documents: ", task.getException());
                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.a8_FragmentChat_sendButton) {
            if(messageInput.getText().toString().equals("")) {
                Toast.makeText(getContext(), "need to input a message before sending", Toast.LENGTH_SHORT).show();
            }
             else {
                 HashMap<String,String> newMsg = new HashMap();
                 newMsg.put(InClass08.SENDER_KEY,mUser.getEmail());
                 newMsg.put(InClass08.MESSAGE_TEXT_KEY, messageInput.getText().toString());
                 //add this message to the database
                 DocumentReference documentRef = database.collection(InClass08.CHAT_LOGS_COLLECTIONS_KEY).document(ONE_ON_ONE_CHAT_KEY);
                 documentRef.update(InClass08.MESSAGE_KEY, FieldValue.arrayUnion(newMsg));
                 messageInput.setText("");
            }
        }
    }


    public void updateRecyclerView() {

        database.collection(InClass08.CHAT_LOGS_COLLECTIONS_KEY)
                .document(ONE_ON_ONE_CHAT_KEY)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null) {
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.d(InClass08.TAG, "this is the snapshot we get" + value.getData().toString());
                            Object hopefullyMessages = value.get(InClass08.MESSAGE_KEY);
                            try {
                            List<HashMap<String, String>> temp = (List<HashMap<String, String>>) hopefullyMessages;
                            messages = new ArrayList(temp);
                            } catch (Exception e) {
                                throw new IllegalStateException("Recieved not a list.");
                            }
                            messagesAdapter.setMessages(messages);
                            messagesAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }

    public void sendImageMessage() {

    }
}