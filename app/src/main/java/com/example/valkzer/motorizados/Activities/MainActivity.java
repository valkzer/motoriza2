package com.example.valkzer.motorizados.Activities;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.valkzer.motorizados.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Intent intent = new Intent(this, Principal.class);
        startActivity(intent);
    }

}
