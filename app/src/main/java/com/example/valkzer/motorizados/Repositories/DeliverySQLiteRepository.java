package com.example.valkzer.motorizados.Repositories;

import java.util.Map;
import java.util.ArrayList;

import com.example.valkzer.motorizados.Models.Delivery;

public class DeliverySQLiteRepository extends SQLiteRepository implements DeliveryRepositoryInterface {

    public DeliverySQLiteRepository()
    {
        super("", "id", "deliveries");
    }

    @Override
    public Delivery createDelivery(Delivery delivery)
    {
        String[]  fieldList = new String[4];
        ArrayList valueList = new ArrayList();

        fieldList[0] = "credit_card_id";

        if (delivery.getCreditCard() != null) {
            valueList.add(delivery.getCreditCard().getId());
        } else {
            valueList.add(null);
        }

        fieldList[1] = "customer_id";
        if (delivery.getCustomer() != null) {
            valueList.add(delivery.getCreditCard().getId());
        } else {
            valueList.add(null);
        }

        fieldList[2] = "cost";
        valueList.add(delivery.getCost());
        fieldList[3] = "completed";
        valueList.add(delivery.isCompleted());

        this.insert(fieldList, valueList);
        return delivery;
    }

    @Override
    public Delivery getDelivery(Integer id)
    {
        Map<String, String> attributes = this.find(id.toString());
        return new Delivery(id);
    }

    @Override
    public ArrayList getAllDeliveries()
    {
        this.selectAll();
        return new ArrayList();
    }

    @Override
    public Delivery updateDelivery(Delivery delivery)
    {
        String[]  fieldList = new String[4];
        ArrayList valueList = new ArrayList();

        fieldList[0] = "credit_card_id";

        if (delivery.getCreditCard() != null) {
            valueList.add(delivery.getCreditCard().getId());
        } else {
            valueList.add(null);
        }

        fieldList[1] = "customer_id";
        if (delivery.getCustomer() != null) {
            valueList.add(delivery.getCreditCard().getId());
        } else {
            valueList.add(null);
        }

        fieldList[2] = "cost";
        valueList.add(delivery.getCost());
        fieldList[3] = "completed";
        valueList.add(delivery.isCompleted());

        this.update(fieldList, valueList, delivery.getId().toString());
        return delivery;
    }

    @Override
    public boolean deleteDelivery(Delivery delivery)
    {
        try {
            this.delete(delivery.getId().toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
