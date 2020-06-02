package com.suusoft.elistening.retrofit.respone;

import android.content.Context;

import com.google.gson.Gson;
import com.suusoft.elistening.util.AppUtil;

public class BaseRespone {

    public String status;
    public int code;
    public String message;
    public String total_page;
//    public List<BannerModel> banner;
//
//    public List<BannerModel> getBanner() {
//        return banner;
//    }
//
//    public void setBanner(List<BannerModel> banner) {
//        this.banner = banner;
//    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess(){
        return  getCode()==200 || getCode() == 200;
    }


    public String getTotal_page() {
        return total_page;
    }

    public void setTotal_page(String total_page) {
        this.total_page = total_page;
    }

    public boolean isSuccess(Context sefl){
        AppUtil.showToast(sefl, getMessage());
        if ( getCode()==200 || getCode() == 200)
            return  true;

        else
            return false;
    }

}
