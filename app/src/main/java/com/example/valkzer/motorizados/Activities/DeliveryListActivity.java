package com.example.valkzer.motorizados.Activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.example.valkzer.motorizados.R;
import com.example.valkzer.motorizados.Models.Customer;
import com.example.valkzer.motorizados.Models.Delivery;
import com.example.valkzer.motorizados.Models.CreditCard;
import com.example.valkzer.motorizados.Activities.Adapters.DeliveryListAdapter;

import java.util.List;
import java.util.Observer;
import java.util.ArrayList;
import java.util.Observable;

public class DeliveryListActivity extends AppCompatActivity {


    private List<Delivery> deliveryList = new ArrayList<>();
    private RecyclerView        recyclerView;
    private DeliveryListAdapter deliveryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_list);

        createDummyRecord();

        Observer observer = new Observer() {
            @Override
            public void update(Observable o, Object arg)
            {
                deliveryList = (ArrayList) arg;
                fillDeliveryList();
            }
        };

        Delivery delivery = new Delivery();
        delivery.getAll(observer);
    }

    private void createDummyRecord()
    {
        Customer   customer   = new Customer("Rolando", "Alajuela", "8889 7894", "rolando@rolando.com", 1000.0, "SJO", "");
        CreditCard creditCard = new CreditCard("**** **** **** 2410", 12, 12, "VISA");
        Delivery   delivery   = new Delivery(customer, creditCard, 10.0, false, "Core i9");
        delivery.create();
    }

    private void fillDeliveryList()
    {
        recyclerView = (RecyclerView) findViewById(R.id.deliveryList);
        deliveryListAdapter = new DeliveryListAdapter(deliveryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(deliveryListAdapter);
    }
}
