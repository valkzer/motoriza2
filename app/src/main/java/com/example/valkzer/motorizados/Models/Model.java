package com.example.valkzer.motorizados.Models;

import java.util.ArrayList;

abstract class Model {

    public abstract Model create();

    public abstract Model update();

    public abstract Model find();

    public abstract boolean delete();

    public abstract ArrayList getAll();

}
