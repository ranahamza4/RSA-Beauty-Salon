package com.example.rsabeautysalon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Nail extends AppCompatActivity {
Button buttonback1;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nail);
        buttonback1 = (Button)findViewById(R.id.buttonbacknail);
        buttonback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent= new Intent(Nail.this,DashBoard.class);
                startActivity(intent);

            }
        });}}
