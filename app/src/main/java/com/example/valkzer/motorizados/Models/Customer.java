package com.example.valkzer.motorizados.Models;

import com.example.valkzer.motorizados.Repositories.FireBase.CustomerFireBaseRepository;


public class Customer extends Model {

    private String name                  = "";
    private String address               = "";
    private String phone                 = "";
    private String email                 = "";
    private Double salary                = 0.0;
    private String workPlace             = "";
    private String identificationPicture = "";
    private String id                    = null;
    private String deliveryId            = null;

    public Customer(String id)
    {
        super(new CustomerFireBaseRepository());
        this.id = id;
    }

    public Customer(String name, String address, String phone, String email, Double salary, String workPlace, String identificationPicture)
    {
        super(new CustomerFireBaseRepository());
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.workPlace = workPlace;
        this.identificationPicture = identificationPicture;
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

    public String getDeliveryId()
    {
        return deliveryId;
    }

    public Customer setDeliveryId(String deliveryId)
    {
        this.deliveryId = deliveryId;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Double getSalary()
    {
        return salary;
    }

    public void setSalary(Double salary)
    {
        this.salary = salary;
    }

    public String getWorkPlace()
    {
        return workPlace;
    }

    public void setWorkPlace(String workPlace)
    {
        this.workPlace = workPlace;
    }

    public String getIdentificationPicture()
    {
        return identificationPicture;
    }

    public void setIdentificationPicture(String identificationPicture)
    {
        this.identificationPicture = identificationPicture;
    }

    //endregion
}
