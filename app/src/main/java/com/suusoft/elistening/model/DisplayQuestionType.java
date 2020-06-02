package com.suusoft.elistening.model;

import com.google.gson.Gson;

/**
 * Created by Suusoft on 09/08/2017.
 */

public class DisplayQuestionType {

    private int id;
    private String name;

    public String toJSon() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public DisplayQuestionType() {
    }

    public DisplayQuestionType(int id, String name) {
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


}
