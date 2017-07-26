package com.example.valkzer.motorizados.Models;

import com.example.valkzer.motorizados.Repositories.DeliveryRepositoryInterface;
import com.example.valkzer.motorizados.Repositories.DeliverySQLiteRepository;

import java.util.ArrayList;

public class Delivery extends Model {

    private Integer    id         = null;
    private Customer   customer   = null;
    private CreditCard creditCard = null;
    private double     cost       = 0;
    private boolean    completed  = false;

    private DeliveryRepositoryInterface deliveryRepository = null;


    public Delivery(Integer id)
    {
        this.id = id;
        this.deliveryRepository = new DeliverySQLiteRepository();
    }

    public Delivery(Integer id, Customer customer, CreditCard creditCard, double cost, boolean completed)
    {
        this.id = id;
        this.customer = customer;
        this.creditCard = creditCard;
        this.cost = cost;
        this.completed = completed;
    }

    @Override
    public Model create()
    {
        return deliveryRepository.createDelivery(this);
    }

    @Override
    public Model update()
    {
        return this.deliveryRepository.updateDelivery(this);
    }

    @Override
    public Model find()
    {
        return this.deliveryRepository.getDelivery(this.id);
    }

    @Override
    public boolean delete()
    {
        return this.deliveryRepository.deleteDelivery(this);
    }

    @Override
    public ArrayList getAll()
    {
        return this.deliveryRepository.getAllDeliveries();
    }

    public Integer getId()
    {
        return id;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public CreditCard getCreditCard()
    {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard)
    {
        this.creditCard = creditCard;
    }

    public double getCost()
    {
        return cost;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    public boolean isCompleted()
    {
        return completed;
    }

    public void setCompleted(boolean completed)
    {
        this.completed = completed;
    }
}
