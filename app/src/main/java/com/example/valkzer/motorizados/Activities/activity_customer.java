package com.example.valkzer.motorizados.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.valkzer.motorizados.Models.CreditCard;
import com.example.valkzer.motorizados.Models.Customer;
import com.example.valkzer.motorizados.Models.Delivery;
import com.example.valkzer.motorizados.R;
import android.widget.Button;
import android.widget.EditText;

public class activity_customer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        final Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
addCustomer(view);
            }
        });
    }

    private void createDummyRecord()
    {
        EditText nombre= (EditText) findViewById(R.id.txtNombre);
        EditText Correo = (EditText) findViewById(R.id.txtCorreo);
        EditText Celular= (EditText) findViewById(R.id.txtTelefono);
        EditText Direccion= (EditText) findViewById(R.id.txtDireccion);
        Customer customer   = new Customer(nombre.getText().toString(), Direccion.getText().toString(), Celular.getText().toString(), Correo.getText().toString(), 0.0, "", "", "");
        customer.create();
    }
    public void addCustomer(View view){
        createDummyRecord();
    }
}
