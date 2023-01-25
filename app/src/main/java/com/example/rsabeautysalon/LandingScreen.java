package com.example.rsabeautysalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingScreen extends AppCompatActivity {
Button button;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_landing_screen );
        button = (Button)findViewById(R.id.button_land);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent= new Intent(LandingScreen.this, Choose.class);
                startActivity(intent);
                finish();
            }
        });

    }
}