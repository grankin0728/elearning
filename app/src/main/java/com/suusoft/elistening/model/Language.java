package com.suusoft.elistening.model;

import com.google.gson.Gson;

import java.io.Serializable;

public class Language implements Serializable {

    private String code;
    private String name;

    public Language() {
    }

    public Language(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
