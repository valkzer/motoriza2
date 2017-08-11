package com.example.valkzer.motorizados.Repositories.FireBase;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import com.example.valkzer.motorizados.Models.Model;
import com.example.valkzer.motorizados.Models.Delivery;
import com.example.valkzer.motorizados.Models.Customer;
import com.example.valkzer.motorizados.Models.CreditCard;
import com.example.valkzer.motorizados.Repositories.Interfaces.DeliveryRepositoryInterface;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

public class DeliveryFireBaseRepository extends BaseFireBaseRepository implements DeliveryRepositoryInterface {

    private final ArrayList<Delivery> deliveries = new ArrayList<>();
    private final Observable          observable = this;

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
        this.repositoryReference.child(newKey).setValue(delivery);
        return model;
    }

    @Override
    public void find(String id, final Observer observer, Boolean keepListening)
    {
        if (keepListening) {
            this.repositoryReference.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    Delivery delivery = dataSnapshot.getValue(Delivery.class);
                    delivery.setId(dataSnapshot.getKey());
                    observer.update(observable, delivery);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {

                }
            });

        } else {
            this.repositoryReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    Delivery delivery = dataSnapshot.getValue(Delivery.class);
                    delivery.setId(dataSnapshot.getKey());
                    observer.update(observable, delivery);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {

                }
            });
        }
    }

    @Override
    public void getAll(final Observer observer)
    {
        this.repositoryReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Delivery delivery = dataSnapshot.getValue(Delivery.class);
                delivery.setId(dataSnapshot.getKey());
                deliveries.add(delivery);
                observer.update(observable, deliveries);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                Integer  childToUpdateIndex = getDeliveryPositionInArray(dataSnapshot.getKey());
                Delivery delivery           = dataSnapshot.getValue(Delivery.class);
                delivery.setId(dataSnapshot.getKey());
                if (childToUpdateIndex != null) {
                    deliveries.remove((int) childToUpdateIndex);
                    deliveries.add((int) childToUpdateIndex, delivery);
                    observer.update(observable, deliveries);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {
                Integer childToDeleteIndex = getDeliveryPositionInArray(dataSnapshot.getKey());
                if (childToDeleteIndex != null) {
                    deliveries.remove((int) childToDeleteIndex);
                    observer.update(observable, deliveries);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    @Override
    public Model update(Model model)
    {
        final Delivery delivery   = (Delivery) model;
        final String   deliveryId = delivery.getId();

        this.repositoryReference.child(deliveryId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Delivery deliveryFromDB = dataSnapshot.getValue(Delivery.class);

                Boolean completed   = delivery.isCompleted() == null ? deliveryFromDB.isCompleted() : delivery.isCompleted();
                Double  cost        = delivery.getCost() == null ? deliveryFromDB.getCost() : delivery.getCost();
                String  description = delivery.getDescription() == null ? deliveryFromDB.getDescription() : delivery.getDescription();

                deliveryFromDB.setCompleted(completed);
                deliveryFromDB.setCost(cost);
                deliveryFromDB.setDescription(description);

                delivery.getCreditCard().update();
                delivery.getCustomer().update();
                repositoryReference.child(deliveryId).setValue(deliveryFromDB);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        return model;
    }

    @Override
    public boolean delete(Model model)
    {
        return false;
    }

    private Integer getDeliveryPositionInArray(String id)
    {
        Integer positionInArray = null;
        for (int index = 0; index < deliveries.size(); index++) {
            Delivery delivery = deliveries.get(index);
            if (Objects.equals(delivery.getId(), id)) {
                positionInArray = index;
            }
        }

        return positionInArray;
    }
}
