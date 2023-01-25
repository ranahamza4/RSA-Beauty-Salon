package com.example.rsabeautysalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choose extends AppCompatActivity {
Button button, button1;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_choose );
        button = (Button)findViewById(R.id.button_user);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent= new Intent(Choose.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
        button1 = (Button)findViewById(R.id.button_admin);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent= new Intent(Choose.this, AdminDashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}