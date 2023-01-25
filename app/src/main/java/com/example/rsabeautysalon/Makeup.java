package com.example.rsabeautysalon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Makeup extends AppCompatActivity {
Button buttonback;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeup);
        buttonback = (Button)findViewById(R.id.buttonbackmakeup);
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent= new Intent(Makeup.this,DashBoard.class);
                startActivity(intent);

            }
        });}}
