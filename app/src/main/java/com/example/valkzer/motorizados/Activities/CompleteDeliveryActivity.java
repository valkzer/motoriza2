package com.example.valkzer.motorizados.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;

import com.example.valkzer.motorizados.R;
import com.example.valkzer.motorizados.Models.Delivery;

import java.util.Observer;
import java.util.Observable;

public class CompleteDeliveryActivity extends AppCompatActivity {

    private String deliveryId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_delivery);

        Intent intent = getIntent();
        this.deliveryId = intent.getStringExtra("deliveryId");
    }

    public void completeDelivery(View view)
    {
        Delivery delivery = new Delivery();
        delivery.find(this.deliveryId, new Observer() {
            @Override
            public void update(Observable o, Object arg)
            {
                Delivery delivery = (Delivery) arg;

                String customerIdentification = ((EditText) findViewById(R.id.txtCustomerIdentification)).getText().toString();

                Double  customerSalary;
                Integer creditCardMonth;
                Integer creditCardYear;
                Double  deliveryCost;
                try {
                    customerSalary = Double.parseDouble(((EditText) findViewById(R.id.txtCustomerSalary)).getText().toString());
                } catch (Exception e) {
                    customerSalary = 0.0;
                }
                String customerWorkPlace = ((EditText) findViewById(R.id.txtWorkplace)).getText().toString();
                String creditCardNumber  = ((EditText) findViewById(R.id.txtCreditCardNumber)).getText().toString();

                try {
                    creditCardMonth = Integer.parseInt(((EditText) findViewById(R.id.txtCreditCardExpMonth)).getText().toString());
                } catch (Exception e) {
                    creditCardMonth = 0;
                }
                try {
                    creditCardYear = Integer.parseInt(((EditText) findViewById(R.id.txtCreditCardExpYear)).getText().toString());
                } catch (Exception e) {
                    creditCardYear = 0;
                }

                try {
                    deliveryCost = Double.parseDouble(((EditText) findViewById(R.id.txtCustomerSalary)).getText().toString());
                } catch (Exception e) {
                    deliveryCost = 0.0;
                }

                delivery.getCustomer().setIdentification(customerIdentification);
                delivery.getCustomer().setSalary(customerSalary);
                delivery.getCustomer().setWorkPlace(customerWorkPlace);

                delivery.getCreditCard().setCardNumber(creditCardNumber);
                delivery.getCreditCard().setCardExpirationYear(creditCardYear);
                delivery.getCreditCard().setCardExpirationMonth(creditCardMonth);

                delivery.setCost(deliveryCost);
                delivery.setCompleted(true);

                delivery.update();
                //TODO: transalation of this, image uploading
                Toast.makeText(CompleteDeliveryActivity.this, "Delivery Completed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
