package com.example.rsabeautysalon.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DB_Utill {
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser user;

    public String getCurrentUserID(){

        user= mAuth.getCurrentUser();
        return user.getUid();
    }
}