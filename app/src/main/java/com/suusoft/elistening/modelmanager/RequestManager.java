package com.suusoft.elistening.modelmanager;

import android.content.Context;

import com.suusoft.elistening.configs.Apis;
//import com.pt.template.gcm.MyGcmSharedPrefrences;

import com.suusoft.elistening.network.BaseRequest;

import java.util.HashMap;

/**
 */
public class RequestManager extends BaseRequest {

    private static final String TAG = RequestManager.class.getSimpleName();

    // Params
    private static final String PARAM_GCM_ID = "gcm_id";
    private static final String PARAM_STATUS = "status_hot";
    private static final String PARAM_IMEI = "ime";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_FULLNAME = "fullname";
    private static final String PARAM_IMAGE = "image";
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_USER_ID = "user_id";
    private static final String PARAM_MESSAGE = "message";
    private static final String PARAM_ID = "id";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_TASK_ID = "task_id";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_BOOK_ID = "book_id";
    private static final String PARAM_CATEGORY_ID = "category_id";
    private static final String PARAM_CHAPTER_ID = "chapter_id";
    private static final String PARAM_KEYWORD = "keyword";
    private static final String PARAM_PAGE = "page";


    public static void getListLesson( Context context, String token, final BaseRequest.CompleteListener completeListener){
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        get(Apis.getUrlLesson(context), params, true, true,  completeListener);
    }

    public static void getListCategory( Context context,String token, final BaseRequest.CompleteListener completeListener){
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        get(Apis.getUrlCategory(context), params, true, true,  completeListener);
    }

    public static void getListLessonByCategory( Context context,String token, String id, String page, final BaseRequest.CompleteListener completeListener){
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("category_id", id);
        params.put("number_per_page", "10");
        params.put("page", page);
        get(Apis.getUrlLessonByCategory(context), params, true, false,  completeListener);
    }


    public static void getSendFeedBack(Context context, String name, String email, String subject,
                                       String content, final BaseRequest.CompleteListener completeListener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("subject", subject);
        params.put("content", content);
        get(Apis.getUrlFeedBack(context), params, true, completeListener);
    }

}
