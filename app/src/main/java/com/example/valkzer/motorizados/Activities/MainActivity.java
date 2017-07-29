package com.example.valkzer.motorizados.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.valkzer.motorizados.R;
import com.example.valkzer.motorizados.Models.Customer;
import com.example.valkzer.motorizados.Models.Delivery;
import com.example.valkzer.motorizados.Models.CreditCard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Customer   customer   = new Customer("Rolando Valcarcel", "Alajuela", "8887-9874", "rvalcarcelvivas@gmail.com", 3000.00, "PM/WM", "https://valkzer.com");
        CreditCard creditCard = new CreditCard("**** **** **** 2410", 24, 10, "VISA");
        Delivery   d          = new Delivery(customer, creditCard, 13, false);
        d.create();
    }
}
