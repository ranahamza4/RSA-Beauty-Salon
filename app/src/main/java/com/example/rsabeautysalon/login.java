package com.example.rsabeautysalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    EditText memail,mpassword;
    Button mloginBtn;
    TextView mCreate,mforgetpass;
    FirebaseUser user;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    LayoutInflater inflater;
    AlertDialog.Builder reset_alert;

    @Override    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        reset_alert=new AlertDialog.Builder(this);
        inflater=this.getLayoutInflater();
        memail=findViewById(R.id.email1);
        mpassword=findViewById(R.id.Password);
        mloginBtn=findViewById(R.id.login);
        mCreate=findViewById(R.id.create2 );
        progressBar=findViewById(R.id.progressBar);
        mAuth=FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();

        mforgetpass=findViewById(R.id.forgetpassword);


        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {

                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    memail.setError("Email required!");
                    return;

                }

                if (TextUtils.isEmpty(password)) {
                    mpassword.setError("Password required!");
                    return;
                }

                progressBar.setVisibility(view.VISIBLE);
                //authenticate user
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( @NonNull Task<AuthResult> task ) {
                        if(task.isSuccessful()){
                            Toast.makeText(login.this, "You have logged in successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), DashBoard.class));
                            finish();

                        }
                        else{
                            Toast.makeText(login.this, "Error : can't process ", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        mforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                View view1 = inflater.inflate( R.layout.reset_pop,null );
                reset_alert.setTitle("Want to reset your password?").setMessage( "Enter your email address to get password rest link" )
              .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialogInterface, int i ) {
                        EditText email1=view1.findViewById( R.id.rest_email );
                        if (email1.getText().toString().isEmpty()){
                            email1.setError( "Required Field" );
                            return;
                        }
                        mAuth.sendPasswordResetEmail( email1.getText().toString() ).addOnSuccessListener( new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess( Void unused ) {
                                Toast.makeText( login.this,"Reset Email has been Sent",Toast.LENGTH_SHORT ).show();
                            }
                        } ).addOnFailureListener( new OnFailureListener() {
                            @Override
                            public void onFailure( @NonNull Exception e ) {
                                Toast.makeText( login.this,e.getMessage(),Toast.LENGTH_SHORT ).show();

                            }
                        } );
                    }}).setNegativeButton("Cancel,", null).setView( view1 ).create().show();
            }
        });
    }
}
