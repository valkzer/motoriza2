package com.example.valkzer.motorizados.Activities;

import android.os.Bundle;

import com.example.valkzer.motorizados.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        super.setUpNavigation();
    }


}
