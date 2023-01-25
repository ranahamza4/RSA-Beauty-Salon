package com.example.rsabeautysalon;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );
        BottomNavigationView bottomNavigationView = findViewById( R.id.bottom_nav );
        bottomNavigationView.setSelectedItemId( R.id.mprofile );
        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( @NonNull MenuItem item ) {
                switch (item.getItemId()) {

                    case R.id.mHome:
                        startActivity( new Intent( getApplicationContext(), DashBoard.class ) );
                        overridePendingTransition( 0, 0 );
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
                        return true;

                }
                return false;
            }

        } );

    }
}