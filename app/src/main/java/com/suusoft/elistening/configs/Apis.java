package com.suusoft.elistening.configs;

import android.content.Context;

import com.suusoft.elistening.R;

/**
 * Created by Suusoft
 */
public class Apis {

    private static String getAppDomain(Context context){
        return context.getString(R.string.URL_API) + "api/";
    }

    public static String getUrlLesson(Context context){
        return getAppDomain(context) + "elearning/list-lesson";
    }
    public static String getUrlLessonByCategory(Context context){
        return getAppDomain(context) + "elearning/list-lesson-by-category";
    }

    public static String getUrlCategory(Context context){
        return getAppDomain(context) + "category/list";
    }

    public static String getUrlFeedBack(Context context) {
        return getAppDomain(context) + "app/api/feedback";
    }

}
