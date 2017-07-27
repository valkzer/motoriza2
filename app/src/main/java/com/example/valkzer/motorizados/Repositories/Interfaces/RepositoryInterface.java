package com.example.valkzer.motorizados.Repositories.Interfaces;

import com.example.valkzer.motorizados.Models.Model;

import java.util.ArrayList;

public interface RepositoryInterface {

    Model create(Model model);

    Model find(String id);

    ArrayList getAll();

    Model update(Model model);

    boolean delete(Model model);

}
