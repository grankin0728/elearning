package com.suusoft.elistening.model;

import com.google.gson.Gson;

public class Theme {

    private String name;

    private String colorPrimary;
    private String colorPrimaryDark;
    private String colorAccent;
    private String backgroundMainDark;
    private String textColorPrimaryDark;
    private String textColorSecondaryDark;
    private String backgroundMainLight;
    private String textColorPrimaryLight;
    private String textColorSecondaryLight;
    private String textPlayHightLight_light;
    private String textPlayHightLight_dark;
    private String colorTabSelected_light;
    private String colorTabSelected_dark;
    private String colorTabNormal_light;
    private String colorTabNormal_dark;

    public Theme() {
    }

    public Theme(String name,
                 String colorPrimary,
                 String colorPrimaryDark,
                 String colorAccent,
                 String backgroundMainDark,
                 String textColorPrimaryDark,
                 String textColorSecondaryDark,
                 String backgroundMainLight,
                 String textColorPrimaryLight,
                 String textColorSecondaryLight,
                 String textPlayHightLight_light,
                 String textPlayHightLight_dark,
                 String colorTabSelected_light,
                 String colorTabSelected_dark,
                 String colorTabNormal_light,
                 String colorTabNormal_dark) {
        this.name = name;
        this.colorPrimary = colorPrimary;
        this.colorPrimaryDark = colorPrimaryDark;
        this.colorAccent = colorAccent;
        this.backgroundMainDark = backgroundMainDark;
        this.textColorPrimaryDark = textColorPrimaryDark;
        this.textColorSecondaryDark = textColorSecondaryDark;
        this.backgroundMainLight = backgroundMainLight;
        this.textColorPrimaryLight = textColorPrimaryLight;
        this.textColorSecondaryLight = textColorSecondaryLight;
        this.textPlayHightLight_light = textPlayHightLight_light;
        this.textPlayHightLight_dark = textPlayHightLight_dark;
        this.colorTabSelected_light = colorTabSelected_light;
        this.colorTabSelected_dark = colorTabSelected_dark;
        this.colorTabNormal_light = colorTabNormal_light;
        this.colorTabNormal_dark = colorTabNormal_dark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorPrimary() {
        return colorPrimary;
    }

    public void setColorPrimary(String colorPrimary) {
        this.colorPrimary = colorPrimary;
    }

    public String getColorPrimaryDark() {
        return colorPrimaryDark;
    }

    public void setColorPrimaryDark(String colorPrimaryDark) {
        this.colorPrimaryDark = colorPrimaryDark;
    }

    public String getColorAccent() {
        return colorAccent;
    }

    public void setColorAccent(String colorAccent) {
        this.colorAccent = colorAccent;
    }

    public String getBackgroundMainDark() {
        return backgroundMainDark;
    }

    public void setBackgroundMainDark(String backgroundMainDark) {
        this.backgroundMainDark = backgroundMainDark;
    }

    public String getTextColorPrimaryDark() {
        return textColorPrimaryDark;
    }

    public void setTextColorPrimaryDark(String textColorPrimaryDark) {
        this.textColorPrimaryDark = textColorPrimaryDark;
    }

    public String getTextColorSecondaryDark() {
        return textColorSecondaryDark;
    }

    public void setTextColorSecondaryDark(String textColorSecondaryDark) {
        this.textColorSecondaryDark = textColorSecondaryDark;
    }

    public String getBackgroundMainLight() {
        return backgroundMainLight;
    }

    public void setBackgroundMainLight(String backgroundMainLight) {
        this.backgroundMainLight = backgroundMainLight;
    }

    public String getTextColorPrimaryLight() {
        return textColorPrimaryLight;
    }

    public void setTextColorPrimaryLight(String textColorPrimaryLight) {
        this.textColorPrimaryLight = textColorPrimaryLight;
    }

    public String getTextColorSecondaryLight() {
        return textColorSecondaryLight;
    }

    public void setTextColorSecondaryLight(String textColorSecondaryLight) {
        this.textColorSecondaryLight = textColorSecondaryLight;
    }

    public String getTextPlayHightLight_light() {
        return textPlayHightLight_light;
    }

    public void setTextPlayHightLight_light(String textPlayHightLight_light) {
        this.textPlayHightLight_light = textPlayHightLight_light;
    }

    public String getTextPlayHightLight_dark() {
        return textPlayHightLight_dark;
    }

    public void setTextPlayHightLight_dark(String textPlayHightLight_dark) {
        this.textPlayHightLight_dark = textPlayHightLight_dark;
    }

    public String getColorTabSelected_light() {
        return colorTabSelected_light;
    }

    public void setColorTabSelected_light(String colorTabSelected_light) {
        this.colorTabSelected_light = colorTabSelected_light;
    }

    public String getColorTabSelected_dark() {
        return colorTabSelected_dark;
    }

    public void setColorTabSelected_dark(String colorTabSelected_dark) {
        this.colorTabSelected_dark = colorTabSelected_dark;
    }

    public String getColorTabNormal_light() {
        return colorTabNormal_light;
    }

    public void setColorTabNormal_light(String colorTabNormal_light) {
        this.colorTabNormal_light = colorTabNormal_light;
    }

    public String getColorTabNormal_dark() {
        return colorTabNormal_dark;
    }

    public void setColorTabNormal_dark(String colorTabNormal_dark) {
        this.colorTabNormal_dark = colorTabNormal_dark;
    }

    //    public Theme(String name, String colorPrimary, String colorPrimaryDark, String colorAccent,
//                 String backgroundMainDark, String textColorPrimaryDark, String textColorSecondaryDark,
//                 String backgroundMainLight, String textColorPrimaryLight, String textColorSecondaryLight) {
//        this.name = name;
//        this.colorPrimary = colorPrimary;
//        this.colorPrimaryDark = colorPrimaryDark;
//        this.colorAccent = colorAccent;
//        this.backgroundMainDark = backgroundMainDark;
//        this.textColorPrimaryDark = textColorPrimaryDark;
//        this.textColorSecondaryDark = textColorSecondaryDark;
//        this.backgroundMainLight = backgroundMainLight;
//        this.textColorPrimaryLight = textColorPrimaryLight;
//        this.textColorSecondaryLight = textColorSecondaryLight;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getColorPrimary() {
//        return colorPrimary;
//    }
//
//    public void setColorPrimary(String colorPrimary) {
//        this.colorPrimary = colorPrimary;
//    }
//
//    public String getColorPrimaryDark() {
//        return colorPrimaryDark;
//    }
//
//    public void setColorPrimaryDark(String colorPrimaryDark) {
//        this.colorPrimaryDark = colorPrimaryDark;
//    }
//
//    public String getColorAccent() {
//        return colorAccent;
//    }
//
//    public void setColorAccent(String colorAccent) {
//        this.colorAccent = colorAccent;
//    }
//
//    public String getBackgroundMainDark() {
//        return backgroundMainDark;
//    }
//
//    public void setBackgroundMainDark(String backgroundMainDark) {
//        this.backgroundMainDark = backgroundMainDark;
//    }
//
//    public String getTextColorPrimaryDark() {
//        return textColorPrimaryDark;
//    }
//
//    public void setTextColorPrimaryDark(String textColorPrimaryDark) {
//        this.textColorPrimaryDark = textColorPrimaryDark;
//    }
//
//    public String getTextColorSecondaryDark() {
//        return textColorSecondaryDark;
//    }
//
//    public void setTextColorSecondaryDark(String textColorSecondaryDark) {
//        this.textColorSecondaryDark = textColorSecondaryDark;
//    }
//
//    public String getBackgroundMainLight() {
//        return backgroundMainLight;
//    }
//
//    public void setBackgroundMainLight(String backgroundMainLight) {
//        this.backgroundMainLight = backgroundMainLight;
//    }
//
//    public String getTextColorPrimaryLight() {
//        return textColorPrimaryLight;
//    }
//
//    public void setTextColorPrimaryLight(String textColorPrimaryLight) {
//        this.textColorPrimaryLight = textColorPrimaryLight;
//    }
//
//    public String getTextColorSecondaryLight() {
//        return textColorSecondaryLight;
//    }
//
//    public void setTextColorSecondaryLight(String textColorSecondaryLight) {
//        this.textColorSecondaryLight = textColorSecondaryLight;
//    }

    public String toJSon(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
