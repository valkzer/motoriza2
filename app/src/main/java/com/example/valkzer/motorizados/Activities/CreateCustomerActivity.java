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

import java.lang.reflect.Array;

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
        boolean flag = false;
        String name    = ((EditText) findViewById(R.id.txtNombre)).getText().toString();
        String email   = ((EditText) findViewById(R.id.txtCorreo)).getText().toString();
        String phone   = ((EditText) findViewById(R.id.txtTelefono)).getText().toString();
        String address = ((EditText) findViewById(R.id.txtDireccion)).getText().toString();

        EditText[ ] arrayEditText = new EditText[]{((EditText) findViewById(R.id.txtNombre)),((EditText) findViewById(R.id.txtCorreo)),((EditText) findViewById(R.id.txtTelefono))
                ,((EditText) findViewById(R.id.txtDireccion))};

        for(int i=0; i< arrayEditText.length; i++){
            flag = valitedFields(arrayEditText[i]);
        }

        if (!isValidEmailAddress(email)){
            ((EditText) findViewById(R.id.txtCorreo)).setError(getString(R.string.provide_a_valid_email));
            ((EditText) findViewById(R.id.txtCorreo)).setHint("aaaa@eee.com");
            flag = false;
        }

        if (flag){
            Customer   customer   = new Customer(name, address, phone, email, 0.0, "", "", "");
            CreditCard creditCard = new CreditCard("", 0, 0, "VISA");
            Delivery   delivery   = new Delivery(customer, creditCard, 0.0, false, "");
            delivery.create();
            Intent intent = new Intent(this, DeliveryListActivity.class);
            startActivity(intent);
        }
    }

    public void addCustomer(View view)
    {
        createCustomerRecord();
    }

    protected boolean valitedFields(EditText editText){
        if (editText.getText().toString().isEmpty()){
            editText.setError(getResources().getString(R.string.ErrorValidateFields));
            return false;
        }else{
            return true;
        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
