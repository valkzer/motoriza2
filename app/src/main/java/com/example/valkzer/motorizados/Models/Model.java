package com.example.valkzer.motorizados.Models;

import java.util.Observer;

import com.example.valkzer.motorizados.Repositories.Interfaces.RepositoryInterface;

public abstract class Model {

    protected String              id         = null;
    private   RepositoryInterface repository = null;

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

    public void find(String id, Observer observer, Boolean keepListening)
    {
        this.repository.find(id, observer, keepListening);
    }

    public boolean delete()
    {
        return this.repository.delete(this);
    }

    public void getAll(Observer observer)
    {
        this.repository.getAll(observer);
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
