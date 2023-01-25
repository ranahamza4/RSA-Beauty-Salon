package com.example.rsabeautysalon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class DashBoard extends AppCompatActivity {
ImageButton button4;
    TextView fulllname, email, phone;
    FirebaseAuth mfAuth;
    FirebaseFirestore fStore;
    String userId;
    LayoutInflater inflater;
    AlertDialog.Builder reset_alert;
    Dialog mydialog;
    TextView seeall;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dash_board );
        setUserTypeOnButtonClick();
        button4 = (ImageButton) findViewById(R.id.imageButton3);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(DashBoard.this, Makeup.class);
                startActivity(intent);}});
        button4 = (ImageButton) findViewById(R.id.imageButtonnail);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(DashBoard.this, Nail.class);
                startActivity(intent);}});
        button4 = (ImageButton) findViewById(R.id.imageButtonhair);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(DashBoard.this, Hair.class);
                startActivity(intent);}});
        button4 = (ImageButton) findViewById(R.id.imageButtonfacial);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(DashBoard.this, Facial.class);
                startActivity(intent);}});
        seeall = (TextView) findViewById(R.id.seeall_btn);
        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(DashBoard.this, Services.class);
                startActivity(intent);}});
        reset_alert = new AlertDialog.Builder( this );
        inflater = this.getLayoutInflater();
        mydialog = new Dialog( this );
        fulllname = findViewById( R.id.profileName );
        email = findViewById( R.id.profileEmail );
        phone = findViewById( R.id.profilePhone );
        mfAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        BottomNavigationView bottomNavigationView = findViewById( R.id.bottom_nav );
        bottomNavigationView.setSelectedItemId( R.id.mHome );
        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( @NonNull MenuItem item ) {
                switch (item.getItemId()) {
                    case R.id.mHome:
                        return true;
                    case R.id.mcontact:
                        startActivity( new Intent( getApplicationContext(), Contact.class ) );
                        overridePendingTransition( 0, 0 );
                        return true;
                    case R.id.mappoint:
                        startActivity( new Intent( getApplicationContext(), Appointment.class ) );
                        overridePendingTransition( 0, 0 );
                        return true;
                    case R.id.mabout:
                        startActivity( new Intent( getApplicationContext(), About.class ) );
                        overridePendingTransition( 0, 0 );
                        return true;
                    case R.id.mprofile:
                        startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                        overridePendingTransition( 0, 0 );
                        return true;
                }
                return false;
            }

        } );
        //database reference
        userId = mfAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection( "user" ).document( userId );
        documentReference.addSnapshotListener( this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent( @Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error ) {
                //getting data from database
//                phone.setText( documentSnapshot.getString( "phone" ) );
                fulllname.setText( documentSnapshot.getString( "fName" ) );
               // email.setText( documentSnapshot.getString( "email" ) );
            }
        } );
    }

    private void setUserTypeOnButtonClick() {

    }

    public void logout( View view ) {//logout
        FirebaseAuth.getInstance().signOut();
        startActivity( new Intent( getApplicationContext(), login.class ) );

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.option_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem i ) {

        if (i.getItemId() == R.id.resetUserPassword) {
            startActivity( new Intent( getApplicationContext(), ResetPassword.class ) );
            finish();
        }
        if (i.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity( new Intent( getApplicationContext(), login.class ) );
            finish();
        }
        if (i.getItemId() == R.id.updateEmailMenu) {
            View view1 = inflater.inflate( R.layout.reset_pop, null );
            reset_alert.setTitle( "Want to update your email address?" ).setMessage( "Enter new email address" )
                    .setPositiveButton( "Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick( DialogInterface dialogInterface, int i ) {
                            EditText email1 = view1.findViewById( R.id.rest_email );
                            if (email1.getText().toString().isEmpty()) {
                                email1.setError( "Required Field" );
                                return;
                            }
                            FirebaseUser user = mfAuth.getCurrentUser();
                            user.updateEmail( email1.getText().toString() ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess( Void unused ) {
                                    Toast.makeText( DashBoard.this, "Email Updated", Toast.LENGTH_SHORT ).show();
                                }
                            } ).addOnFailureListener( new OnFailureListener() {
                                @Override
                                public void onFailure( @NonNull Exception e ) {
                                    Toast.makeText( DashBoard.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                                }
                            } );
                        }
                    } ).setNegativeButton( "Cancel", null ).setView( view1 ).create().show();
        }
        return super.onOptionsItemSelected( i );
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void showPopup( View v ) {
        TextView txtclose;
        mydialog.setContentView( R.layout.popup );
        txtclose = (TextView) mydialog.findViewById( R.id.txtclose );
        txtclose.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                mydialog.dismiss();
            }
        } );
        mydialog.show();
    }

    public void showPopup1( View v ) {
        TextView txtclose1;
        mydialog.setContentView( R.layout.popup2 );
        txtclose1 = (TextView) mydialog.findViewById( R.id.txtclose1 );
        txtclose1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                mydialog.dismiss();
            }
        } );
        mydialog.show();
    }

    public void showPopup2( View v ) {
        TextView txtclose2;
        mydialog.setContentView( R.layout.popup3 );
        txtclose2 = (TextView) mydialog.findViewById( R.id.txtclose2 );
        txtclose2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                mydialog.dismiss();
            }
        } );
        mydialog.show();
    }
}
