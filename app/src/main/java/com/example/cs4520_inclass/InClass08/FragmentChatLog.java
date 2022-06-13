package com.example.cs4520_inclass.InClass08;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs4520_inclass.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

//Hector Benitez InClass Assignment 8


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChatLog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChatLog extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ChatAdapter messagesAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private Button sendButton;
    private ImageButton backButton;
    private EditText messageInput;
    private TextView friendDisplayName;
    public static String ONE_ON_ONE_CHAT_KEY;

    private FirebaseFirestore database;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private static final String ARG_PARAM1 = "param1";
    private String friendEmail;

    private ArrayList<Message> messages;


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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_log, container, false);


        String userEmail = mUser.getEmail();
        assert userEmail != null;
        if(userEmail.compareTo(friendEmail) >= 1) {
            ONE_ON_ONE_CHAT_KEY = userEmail + friendEmail;
        }
        if(userEmail.compareTo(friendEmail) <= -1) {
            ONE_ON_ONE_CHAT_KEY = friendEmail + userEmail;
        }
         else {
            ONE_ON_ONE_CHAT_KEY = userEmail + friendEmail;
        }

        backButton = view.findViewById(R.id.a8_chatLogBackButton);
        friendDisplayName = view.findViewById(R.id.a8_chatNameTextView);
        recyclerView = view.findViewById(R.id.a8_chatLogRecyclerView);
        sendButton = view.findViewById(R.id.a8_FragmentChat_sendButton);
        sendButton.setOnClickListener(this);
        messageInput = view.findViewById(R.id.a8_FragmentChat_messageInput);
        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerViewLayoutManager = layout;
        layout.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

         messages = new ArrayList<>();

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
                    if (document.exists()) {
                        database.collection(InClass08.CHAT_LOGS_COLLECTIONS_KEY)
                                .document(ONE_ON_ONE_CHAT_KEY)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                Chat messageField = document.toObject(Chat.class);
                                                assert messageField != null;
                                                messages = messageField.getMessage();
                                                messagesAdapter = new ChatAdapter(messages, userEmail);
                                                recyclerView.setAdapter(messagesAdapter);
                                                Log.d(InClass08.TAG, messageField.toString());

                                        } else {
                                            Log.d(InClass08.TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });


                    } else {
                        Log.d(InClass08.TAG, "No such document");
                        Chat Message = new Chat(new ArrayList<>());
                        database.collection(InClass08.CHAT_LOGS_COLLECTIONS_KEY).document(ONE_ON_ONE_CHAT_KEY)
                                .set(Message)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(InClass08.TAG, "DocumentSnapshot successfully written!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(InClass08.TAG, "Error writing document", e);
                                    }
                                });
                    }
                } else {
                    Log.d(InClass08.TAG, "get failed with ", task.getException());
                }
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
                 Chat Message = new Chat(new ArrayList<>());
                 Message newMsg = new Message(mUser.getEmail(), messageInput.getText().toString());
                 Message.getMessage().add(newMsg);
                 //add this message to the database
                DocumentReference documentRef = database.collection(InClass08.CHAT_LOGS_COLLECTIONS_KEY).document(ONE_ON_ONE_CHAT_KEY);
                documentRef.update(InClass08.MESSAGE_KEY, FieldValue.arrayUnion(newMsg));
                messageInput.setText("");
                updateRecyclerView();
            }
        }
        if(v.getId() == R.id.a8_chatLogBackButton) {

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
                            Chat newChat = value.toObject(Chat.class);
                            assert newChat != null;

                            messagesAdapter.setMessages(newChat.getMessage());
                            messagesAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}