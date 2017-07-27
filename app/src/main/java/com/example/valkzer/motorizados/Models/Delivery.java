package com.example.valkzer.motorizados.Models;

import com.example.valkzer.motorizados.Repositories.FireBase.DeliveryFireBaseRepository;

public class Delivery extends Model {

    private String     id         = null;
    private Customer   customer   = null;
    private CreditCard creditCard = null;
    private double     cost       = 0;
    private boolean    completed  = false;

    public Delivery(String id)
    {
        super(new DeliveryFireBaseRepository());
        this.id = id;
    }

    public Delivery(Customer customer, CreditCard creditCard, double cost, boolean completed)
    {
        super(new DeliveryFireBaseRepository());
        this.customer = customer;
        this.creditCard = creditCard;
        this.cost = cost;
        this.completed = completed;
    }

    //region Setters and Getters

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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

    //endregion
}
