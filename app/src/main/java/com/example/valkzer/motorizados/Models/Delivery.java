package com.example.valkzer.motorizados.Models;

import com.example.valkzer.motorizados.Repositories.FireBase.DeliveryFireBaseRepository;

public class Delivery extends Model {

    private Customer   customer    = null;
    private CreditCard creditCard  = null;
    private Double     cost        = null;
    private String     description = null;
    private Boolean    completed   = null;

    public Delivery()
    {
        super(new DeliveryFireBaseRepository());
    }

    public Delivery(String id)
    {
        super(new DeliveryFireBaseRepository());
        this.id = id;
    }

    public Delivery(Customer customer, CreditCard creditCard, Double cost, Boolean completed, String description)
    {
        super(new DeliveryFireBaseRepository());
        this.customer = customer;
        this.description = description;
        this.creditCard = creditCard;
        this.cost = cost;
        this.completed = completed;
    }

    //region Setters and Getters

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    public Double getCost()
    {
        return cost;
    }

    public void setCost(Double cost)
    {
        this.cost = cost;
    }

    public Boolean isCompleted()
    {
        return completed;
    }

    public void setCompleted(Boolean completed)
    {
        this.completed = completed;
    }

    //endregion
}
