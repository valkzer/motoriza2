package com.example.valkzer.motorizados.Repositories.FireBase;

import java.util.ArrayList;

import com.example.valkzer.motorizados.Models.Model;
import com.example.valkzer.motorizados.Models.CreditCard;
import com.example.valkzer.motorizados.Repositories.Interfaces.CreditCardRepositoryInterface;

public class CreditCardFireBaseRepository extends BaseFireBaseRepository implements CreditCardRepositoryInterface {

    public CreditCardFireBaseRepository()
    {
        super("credit_cards");
    }

    @Override
    public Model create(Model model)
    {
        String     newKey     = this.repositoryReference.push().getKey();
        CreditCard creditCard = (CreditCard) model;
        this.repositoryReference.child(newKey).setValue(creditCard);
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
