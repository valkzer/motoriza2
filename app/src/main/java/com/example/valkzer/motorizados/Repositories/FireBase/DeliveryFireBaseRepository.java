package com.example.valkzer.motorizados.Repositories.FireBase;

import java.util.ArrayList;

import com.example.valkzer.motorizados.Models.Model;
import com.example.valkzer.motorizados.Models.Delivery;
import com.example.valkzer.motorizados.Models.Customer;
import com.example.valkzer.motorizados.Models.CreditCard;
import com.example.valkzer.motorizados.Repositories.Interfaces.DeliveryRepositoryInterface;

public class DeliveryFireBaseRepository extends BaseFireBaseRepository implements DeliveryRepositoryInterface {

    public DeliveryFireBaseRepository()
    {
        super("deliveries");
    }

    @Override
    public Model create(Model model)
    {
        String     newKey     = this.repositoryReference.push().getKey();
        Delivery   delivery   = (Delivery) model;
        CreditCard creditCard = delivery.getCreditCard();
        Customer   customer   = delivery.getCustomer();
        creditCard.setDeliveryId(newKey).create();
        customer.setDeliveryId(newKey).create();
        delivery.setCustomer(null);
        delivery.setCreditCard(null);
        this.repositoryReference.child(newKey).setValue(delivery);
        return model;
    }

    @Override
    public Model find(String id)
    {
        return null;
    }

    @Override
    public ArrayList getAll()
    {
        return null;
    }

    @Override
    public Model update(Model model)
    {
        return null;
    }

    @Override
    public boolean delete(Model model)
    {
        return false;
    }
}
