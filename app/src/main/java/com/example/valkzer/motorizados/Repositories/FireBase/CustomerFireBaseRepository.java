package com.example.valkzer.motorizados.Repositories.FireBase;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.Observer;
import java.util.ArrayList;
import java.util.Observable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

import com.example.valkzer.motorizados.Models.Model;
import com.example.valkzer.motorizados.Models.Customer;
import com.example.valkzer.motorizados.Repositories.Interfaces.CustomerRepositoryInterface;

public class CustomerFireBaseRepository extends BaseFireBaseRepository implements CustomerRepositoryInterface {

    private final ArrayList<Customer> customers  = new ArrayList<>();
    private final Observable          observable = this;

    public CustomerFireBaseRepository()
    {
        super("customers");
    }

    @Override
    public Model create(Model model)
    {
        String   newKey   = this.repositoryReference.push().getKey();
        Customer customer = (Customer) model;
        this.repositoryReference.child(newKey).setValue(customer);
        customer.setId(newKey);
        return model;
    }

    @Override
    public void find(String id, final Observer observer)
    {
        this.repositoryReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Customer customer = dataSnapshot.getValue(Customer.class);
                observer.update(observable, customer);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    @Override
    public void getAll(final Observer observer)
    {
        this.repositoryReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Customer customer = dataSnapshot.getValue(Customer.class);
                customer.setId(dataSnapshot.getKey());
                customers.add(customer);
                observer.update(observable, customers);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                Integer  childToUpdateIndex = getCustomerPositionInArray(dataSnapshot.getKey());
                Customer customer           = dataSnapshot.getValue(Customer.class);
                customer.setId(dataSnapshot.getKey());
                if (childToUpdateIndex != null) {
                    customers.remove((int) childToUpdateIndex);
                    customers.add((int) childToUpdateIndex, customer);
                    observer.update(observable, customers);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {
                Integer childToDeleteIndex = getCustomerPositionInArray(dataSnapshot.getKey());
                if (childToDeleteIndex != null) {
                    customers.remove((int) childToDeleteIndex);
                    observer.update(observable, customers);
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
        final Customer customer   = (Customer) model;
        final String   customerId = customer.getId();

        this.repositoryReference.child(customerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Customer customerFromDB = dataSnapshot.getValue(Customer.class);

                String deliveryId            = customer.getDeliveryId() == null ? customerFromDB.getDeliveryId() : customer.getDeliveryId();
                String address               = customer.getAddress() == null ? customerFromDB.getAddress() : customer.getAddress();
                String email                 = customer.getEmail() == null ? customerFromDB.getEmail() : customer.getEmail();
                String identificationPicture = customer.getIdentificationPicture() == null ? customerFromDB.getIdentificationPicture() : customer.getIdentificationPicture();
                String name                  = customer.getName() == null ? customerFromDB.getName() : customer.getName();
                String phone                 = customer.getPhone() == null ? customerFromDB.getPhone() : customer.getPhone();
                Double salary                = customer.getSalary() == null ? customerFromDB.getSalary() : customer.getSalary();
                String workPlace             = customer.getWorkPlace() == null ? customerFromDB.getWorkPlace() : customer.getWorkPlace();

                customerFromDB.setDeliveryId(deliveryId);
                customerFromDB.setAddress(address);
                customerFromDB.setEmail(email);
                customerFromDB.setIdentificationPicture(identificationPicture);
                customerFromDB.setName(name);
                customerFromDB.setPhone(phone);
                customerFromDB.setSalary(salary);
                customerFromDB.setWorkPlace(workPlace);

                Map<String, Object> updates = new HashMap<String, Object>();
                updates.put("/" + repositoryReference.getKey() + "/" + dataSnapshot.getKey(), customerFromDB);
                updates.put("/deliveries/" + customerFromDB.getDeliveryId() + "/customer", customerFromDB);

                repositoryReference.getRoot().updateChildren(updates);
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

    private Integer getCustomerPositionInArray(String id)
    {
        Integer positionInArray = null;
        for (int index = 0; index < customers.size(); index++) {
            Customer customer = customers.get(index);
            if (Objects.equals(customer.getId(), id)) {
                positionInArray = index;
            }
        }

        return positionInArray;
    }
}
