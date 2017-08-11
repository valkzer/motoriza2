package com.example.valkzer.motorizados.Activities;

import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.example.valkzer.motorizados.R;
import com.example.valkzer.motorizados.Models.Customer;
import com.example.valkzer.motorizados.Models.Delivery;
import com.example.valkzer.motorizados.Models.CreditCard;
import com.example.valkzer.motorizados.Activities.Adapters.DeliveryListAdapter;
import com.example.valkzer.motorizados.Activities.Listeners.RecyclerTouchListener;

import java.util.List;
import java.util.Observer;
import java.util.ArrayList;
import java.util.Observable;

public class DeliveryListActivity extends AppCompatActivity {


    private List<Delivery> deliveryList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_list);
        recyclerView = (RecyclerView) findViewById(R.id.deliveryList);
        //createDummyRecord();

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
        final Context context = this;
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position)
            {
                Delivery delivery = deliveryList.get(position);
                Intent   intent   = new Intent(context, DeliveryDetailActivity.class);
                intent.putExtra("deliveryId", delivery.getId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position)
            {

            }
        }));


    }

    private void createDummyRecord()
    {
        Customer   customer   = new Customer("Rolando", "Alajuela", "8889 7894", "rolando@rolando.com", 1000.0, "SJO", "", "11920030");
        CreditCard creditCard = new CreditCard("**** **** **** 2410", 12, 12, "VISA");
        Delivery   delivery   = new Delivery(customer, creditCard, 10.0, false, "Core i9");
        delivery.create();
    }

    private void fillDeliveryList()
    {
        DeliveryListAdapter        deliveryListAdapter = new DeliveryListAdapter(deliveryList);
        RecyclerView.LayoutManager mLayoutManager      = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(deliveryListAdapter);
    }
}
