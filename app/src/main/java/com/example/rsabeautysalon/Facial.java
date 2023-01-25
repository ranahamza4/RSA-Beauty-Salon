package com.example.rsabeautysalon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Facial extends AppCompatActivity {
Button buttonback;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facial);
        buttonback = (Button)findViewById(R.id.buttonbackfacial);
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent= new Intent(Facial.this,DashBoard.class);
                startActivity(intent);

            }
        });}}
