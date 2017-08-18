package com.example.valkzer.motorizados.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.valkzer.motorizados.Models.CreditCard;
import com.example.valkzer.motorizados.Models.Customer;
import com.example.valkzer.motorizados.Models.Delivery;
import com.example.valkzer.motorizados.R;

import android.widget.Button;
import android.widget.EditText;

public class CreateCustomerActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
        super.setUpNavigation();
        final Button btnCreateCustomer = (Button) findViewById(R.id.button3);
        btnCreateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                addCustomer(view);
            }
        });
    }

    private void createCustomerRecord()
    {
        String name    = ((EditText) findViewById(R.id.txtNombre)).getText().toString();
        String email   = ((EditText) findViewById(R.id.txtCorreo)).getText().toString();
        String phone   = ((EditText) findViewById(R.id.txtTelefono)).getText().toString();
        String address = ((EditText) findViewById(R.id.txtDireccion)).getText().toString();

        Customer   customer   = new Customer(name, address, phone, email, 0.0, "", "", "");
        CreditCard creditCard = new CreditCard("", 0, 0, "VISA");
        Delivery   delivery   = new Delivery(customer, creditCard, 0.0, false, "");
        delivery.create();

        Intent intent = new Intent(this, DeliveryListActivity.class);
        startActivity(intent);
    }

    public void addCustomer(View view)
    {
        createCustomerRecord();
    }
}
