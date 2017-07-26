package com.example.valkzer.motorizados.Repositories;

import com.example.valkzer.motorizados.Models.Delivery;

import java.util.ArrayList;

public interface DeliveryRepositoryInterface {

    public Delivery createDelivery(Delivery delivery);

    public Delivery getDelivery(Integer id);

    public ArrayList getAllDeliveries();

    public Delivery updateDelivery(Delivery delivery);

    public boolean deleteDelivery(Delivery delivery);


}
