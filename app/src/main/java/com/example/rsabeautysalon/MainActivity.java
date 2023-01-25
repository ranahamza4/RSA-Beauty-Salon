package com.example.rsabeautysalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    //declaring variables
    TextView fulllname,email,phone;
    FirebaseAuth mfAuth;
    FirebaseFirestore fStore;
    String userId;
    LayoutInflater inflater;
    AlertDialog.Builder reset_alert;
    EditText email1;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        BottomNavigationView bottomNavigationView = findViewById( R.id.bottom_nav );
        bottomNavigationView.setSelectedItemId( R.id.mprofile );
        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( @NonNull MenuItem item ) {
                switch (item.getItemId()) {
                    case R.id.mHome:
                        startActivity( new Intent( getApplicationContext(), DashBoard.class ) );
                        overridePendingTransition( 0, 0 );
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
                        return true;
                }
                return false;
            }

        } );

        reset_alert = new AlertDialog.Builder( this );
        inflater = this.getLayoutInflater();
        email1 = findViewById( R.id.rest_email );

        inflater = this.getLayoutInflater();
        //initializing variables



    }
    private void setUserTypeOnButtonClick() {
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    //logout button function
    public void logout( View view ) {//logout
        FirebaseAuth.getInstance().signOut();
        startActivity( new Intent( getApplicationContext(), login.class ) );
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate( R.menu.option_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(  MenuItem item ) {

        if (item.getItemId() == R.id.resetUserPassword) {
            startActivity( new Intent( getApplicationContext(), ResetPassword.class ) );
        }
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity( new Intent( getApplicationContext(), login.class ) );
            finish();
           }
        if (item.getItemId() == R.id.updateEmailMenu) {
            View view1 = inflater.inflate( R.layout.reset_pop,null );
            reset_alert.setTitle("Update Email?").setMessage( "Enter new email address" )
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick( DialogInterface dialogInterface, int i ) {
                            EditText email1=view1.findViewById( R.id.rest_email );
                            if (email1.getText().toString().isEmpty()){
                                email1.setError( "Required Field" );
                                return;
                            }
                            FirebaseUser user= mfAuth.getCurrentUser();
                            user.updateEmail( email1.getText().toString() ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess( Void unused ) {
                                    Toast.makeText( MainActivity.this,"Email Updated",Toast.LENGTH_SHORT ).show();
                                }
                            } ).addOnFailureListener( new OnFailureListener() {
                                @Override
                                public void onFailure( @NonNull Exception e ) {
                                    Toast.makeText( MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT ).show();
                                }
                            } );
                        }}).setNegativeButton("Cancel,", null).setView( view1 ).create().show();
            }return super.onOptionsItemSelected( item );
    }
}