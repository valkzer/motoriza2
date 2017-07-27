package com.example.valkzer.motorizados.Repositories.FireBase;

import java.util.ArrayList;

import com.example.valkzer.motorizados.Models.Model;
import com.example.valkzer.motorizados.Models.Customer;
import com.example.valkzer.motorizados.Repositories.Interfaces.CustomerRepositoryInterface;

public class CustomerFireBaseRepository extends BaseFireBaseRepository implements CustomerRepositoryInterface {

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
