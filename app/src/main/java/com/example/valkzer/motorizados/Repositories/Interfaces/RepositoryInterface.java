package com.example.valkzer.motorizados.Repositories.Interfaces;

import com.example.valkzer.motorizados.Models.Model;

import java.util.Observer;

public interface RepositoryInterface {

    Model create(Model model);

    void find(String id, Observer observer, Boolean keepListening);

    void getAll(Observer observer);

    Model update(Model model);

    boolean delete(Model model);

}
