package com.example.valkzer.motorizados.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.valkzer.motorizados.Models.Delivery;
import com.example.valkzer.motorizados.R;

import java.util.Observable;
import java.util.Observer;

public class DeliveryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_detail);

        Intent intent     = getIntent();
        String deliveryId = intent.getStringExtra("deliveryId");

        Delivery delivery = new Delivery();
        delivery.find(deliveryId, new Observer() {
            @Override
            public void update(Observable o, Object arg)
            {
                Delivery delivery = (Delivery) arg;
                ((TextView) findViewById(R.id.lblCustomerName)).setText(delivery.getCustomer().getName());
                ((TextView) findViewById(R.id.lblCustomerAddress)).setText(delivery.getCustomer().getAddress());
                ((TextView) findViewById(R.id.lblCustomerPhone)).setText(delivery.getCustomer().getPhone());
                ((TextView) findViewById(R.id.lblDeliveryDescription)).setText(delivery.getDescription());
            }
        });

    }


    public void sendSMS(View view)
    {
        String phoneNumber = ((TextView) findViewById(R.id.lblCustomerPhone)).getText().toString();
        Intent intent      = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        startActivity(intent);
    }

    public void startCall(View view)
    {
        String phoneNumber = ((TextView) findViewById(R.id.lblCustomerPhone)).getText().toString();
        Intent intent      = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}
