package com.example.cs4520_inclass.InClass08_and_InClass09;

import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public interface DatabaseImageUtil {

    public Bitmap pullProfilePicture(String userEmail);




}
