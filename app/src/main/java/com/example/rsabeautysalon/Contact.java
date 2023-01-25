package com.example.rsabeautysalon;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Contact extends AppCompatActivity {
    EditText etTo, etSub, etMsg;
    Button btSend;
    String to, subject, message;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_contact );
        etTo = (EditText) findViewById( R.id.etTo );
        etSub = (EditText) findViewById( R.id.etSub );
        etMsg = (EditText) findViewById( R.id.etMsg );
        btSend = (Button) findViewById( R.id.btSend );

        btSend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                to = etTo.getText().toString();
                subject = etSub.getText().toString();
                message = etMsg.getText().toString();


                Intent email = new Intent( Intent.ACTION_SEND );
                email.putExtra( Intent.EXTRA_EMAIL, new String[]{to} );
                email.putExtra( Intent.EXTRA_SUBJECT, subject );
                email.putExtra( Intent.EXTRA_TEXT, message );

                //need this to prompts email client only
                email.setType( "message/rfc822" );
                startActivity( Intent.createChooser( email, "Choose Email client :" ) );
            }
        } );
    

    BottomNavigationView bottomNavigationView = findViewById( R.id.bottom_nav );
        bottomNavigationView.setSelectedItemId(R.id.mcontact);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()

    {
        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item){
        switch (item.getItemId()) {

            case R.id.mHome:
                startActivity( new Intent( getApplicationContext(), DashBoard.class ) );
                overridePendingTransition( 0, 0 );
                return true;

            case R.id.mcontact:

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

    });
}
    public void displaytoast( View view ) {
        Toast.makeText( Contact.this, "Email has been sent Successfully!", Toast.LENGTH_SHORT ).show();
    }
}