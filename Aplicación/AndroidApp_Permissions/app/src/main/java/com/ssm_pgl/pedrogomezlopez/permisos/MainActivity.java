package com.ssm_pgl.pedrogomezlopez.permisos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ssm_pgl.pedrogomezlopez.permisos.R;


public class MainActivity extends AppCompatActivity
{

    FloatingActionButton safe;
    FloatingActionButton unsafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // We associate the two buttons
        safe = (FloatingActionButton) findViewById(R.id.safe);
        unsafe = (FloatingActionButton) findViewById(R.id.unsafe);


        // We relate each button with its class
        safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the SafeFragment activity
                startActivity(new Intent(getApplicationContext(), SafeFragment.class));
            }
        });

        unsafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the UnsafeFragment activity
                startActivity(new Intent(getApplicationContext(), UnsafeFragment.class));
            }
        });

    }

}