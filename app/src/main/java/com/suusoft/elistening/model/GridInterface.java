package com.suusoft.elistening.model;

import com.google.gson.Gson;

/**
 * Created by Suusoft on 10/25/2017.
 */

public class GridInterface {

    private int id;
    private String name;

    public GridInterface() {
    }

    public GridInterface(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toJSon() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
