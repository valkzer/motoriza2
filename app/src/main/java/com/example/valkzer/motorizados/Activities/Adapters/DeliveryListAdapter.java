package com.example.valkzer.motorizados.Activities.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;

import com.example.valkzer.motorizados.R;
import com.example.valkzer.motorizados.Models.Delivery;

import java.util.List;

public class DeliveryListAdapter extends RecyclerView.Adapter<DeliveryListAdapter.MyViewHolder> {

    private List<Delivery> deliveryList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, address, phone;

        public MyViewHolder(View view)
        {
            super(view);
            name = (TextView) view.findViewById(R.id.lblName);
            phone = (TextView) view.findViewById(R.id.lblPhone);
        }
    }


    public DeliveryListAdapter(List<Delivery> moviesList)
    {
        this.deliveryList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.delivery_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Delivery delivery = deliveryList.get(position);
        holder.name.setText(delivery.getCustomer().getName());
        holder.phone.setText(delivery.getCustomer().getPhone());
    }

    @Override
    public int getItemCount()
    {
        return deliveryList.size();
    }
}