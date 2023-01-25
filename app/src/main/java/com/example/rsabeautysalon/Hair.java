package com.example.rsabeautysalon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Hair extends AppCompatActivity {
Button buttonback3;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair);
        buttonback3 = (Button)findViewById(R.id.buttonbackhair);
        buttonback3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent= new Intent(Hair.this,DashBoard.class);
                startActivity(intent);

            }
        });}}
