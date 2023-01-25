package com.example.rsabeautysalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
   //declaring variables
    EditText mfullname,memail,mphonenumber,mpassword;
    Button mregistration;
    TextView mlogin;
    private FirebaseAuth mAuth;
    FirebaseFirestore  fStore;
    String  userID;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //initializing variables
        mfullname = findViewById(R.id.fullname);
        memail = findViewById(R.id.email1);
        mphonenumber = findViewById(R.id.Password);
        mpassword = findViewById(R.id.password);
        mregistration = findViewById(R.id.login);
        mlogin = findViewById(R.id.create2 );
        mAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        //checking user  already registered
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), DashBoard.class));
            finish();
        }
//adding constraints on fields
        mregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                String fullName=mfullname.getText().toString();
                String phone=mphonenumber.getText().toString();
                String passwordVal="^"+
                        "(?=.*[@#$%^&+=])" +// at least 1 special character
                        //"(?=.*[0-9])"//atleast 1 digit
                        //"(?=.*[a-z])"//atleast 1 uppercase letter
                        //"(?=.*[A-Z])"////any letter
                        "(?=\\S+$)" +            // no white spaces
                        ".{4,}" +                // at least 4 characters
                        "$";


                if (password.isEmpty()) {
                    mpassword.setError("Field can not be empty");
                    return;
                }

                // if password does not matches to the pattern
                // it will display an error message "Password is too weak"
                // if (!PASSWORD_PATTERN.matcher(password).matches() ) {
                //  mpassword.setError("Password is too weak");
                //  return;
                // }

                else if (TextUtils.isEmpty(email)) {
                    memail.setError("email is required");
                    return;
                }
               // else if(!password.matches(passwordVal)){

                  //  mpassword.setError("Password is too weak");
                //    return;
             //   }

                else if (TextUtils.isEmpty(password)) {
                    mpassword.setError("password required");
                    return;
                }
                else if (password.length() < 6){
                    mpassword.setError("passord is less then 6 characters");
                    return;
                }
                //register user in fire base
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( @NonNull Task<AuthResult> task ) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Account has been created", Toast.LENGTH_SHORT).show();
                            userID=mAuth.getCurrentUser().getUid();
                            //user is the reference created in
                            DocumentReference documentReference=fStore.collection("user").document(userID);
                          //database to retrive from database
                            Map<String,Object>user=new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("phone",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess( Void unused ) {
                                    Log.d("TAG","onsucess:user Profile is created  for"+userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure( @NonNull Exception e ) {
                                    Log.d("tag","onFailure"+e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), DashBoard.class));


                        } else {

                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //login button connection to login screen
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                startActivity(new Intent(getApplicationContext(),login.class));

            }
        });


    }

}