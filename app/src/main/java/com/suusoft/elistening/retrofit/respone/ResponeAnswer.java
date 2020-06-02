package com.suusoft.elistening.retrofit.respone;

import com.suusoft.elistening.model.modelLesson.Question;

import java.util.ArrayList;

public class ResponeAnswer extends BaseRespone {

    public ArrayList<Question> data;

    public ArrayList<Question> getData() {
        return data;
    }

    public void setData(ArrayList<Question> data) {
        this.data = data;
    }
}
