package com.example.valkzer.motorizados.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.valkzer.motorizados.Models.Customer;
import com.example.valkzer.motorizados.R;

import android.widget.Button;
import android.widget.EditText;

public class CreateCustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomer(view);
            }
        });
    }

    private void createCustomerRecord() {
        EditText name = (EditText) findViewById(R.id.txtNombre);
        EditText email = (EditText) findViewById(R.id.txtCorreo);
        EditText phone = (EditText) findViewById(R.id.txtTelefono);
        EditText address = (EditText) findViewById(R.id.txtDireccion);
        Customer customer = new Customer(name.getText().toString(), address.getText().toString(), phone.getText().toString(), email.getText().toString(), 0.0, "", "", "");
        customer.create();
    }

    public void addCustomer(View view) {
        createCustomerRecord();
    }
}
