package com.suusoft.elistening.model.modelLesson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.suusoft.elistening.base.model.BaseModel;

public class Answer extends BaseModel {

    @SerializedName("A")
    @Expose
    private String a;
    @SerializedName("B")
    @Expose
    private String b;
    @SerializedName("C")
    @Expose
    private String c;
    @SerializedName("D")
    @Expose
    private String d;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }
}
