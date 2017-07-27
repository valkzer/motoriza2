package com.example.valkzer.motorizados.Models;

import java.util.ArrayList;

import com.example.valkzer.motorizados.Repositories.Interfaces.RepositoryInterface;

public abstract class Model {

    private RepositoryInterface repository = null;

    public Model(RepositoryInterface repository)
    {
        this.repository = repository;
    }

    public Model create()
    {
        return repository.create(this);
    }

    public Model update()
    {
        return this.repository.update(this);
    }

    public Model find(String id)
    {
        return this.repository.find(id);
    }

    public boolean delete()
    {
        return this.repository.delete(this);
    }

    public ArrayList getAll()
    {
        return this.repository.getAll();
    }

}
