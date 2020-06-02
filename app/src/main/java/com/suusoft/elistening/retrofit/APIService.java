package com.suusoft.elistening.retrofit;

import com.suusoft.elistening.retrofit.respone.ResponeAnswer;
import com.suusoft.elistening.retrofit.respone.ResponeLesson;
import com.suusoft.elistening.retrofit.respone.ResponeUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.suusoft.elistening.retrofit.Param.PARAM_CATEGORY_ID;
import static com.suusoft.elistening.retrofit.Param.PARAM_EMAIL;
import static com.suusoft.elistening.retrofit.Param.PARAM_KEYWORD;
import static com.suusoft.elistening.retrofit.Param.PARAM_LESSON_ID;
import static com.suusoft.elistening.retrofit.Param.PARAM_LOGIN_TYPE;
import static com.suusoft.elistening.retrofit.Param.PARAM_NUMBER_PER_PAGE;
import static com.suusoft.elistening.retrofit.Param.PARAM_PAGE;
import static com.suusoft.elistening.retrofit.Param.PARAM_PASSWORD;
import static com.suusoft.elistening.retrofit.Param.PARAM_TOKEN;
import static com.suusoft.elistening.retrofit.Param.PARAM_USERNAME;

/**
 * Created by suusoft.com on 11/12/19.
 */


public interface APIService {


    @GET("api/user/login")
    @Headers("Cache-Control:no-cache") // no cache
    Call<ResponeUser> login(
                            @Query(PARAM_USERNAME) String username,
                            @Query(PARAM_EMAIL) String email,
                            @Query(PARAM_PASSWORD) String password,
                            @Query(PARAM_LOGIN_TYPE) String login_type);


    @GET("api/elearning/search-lesson")
    @Headers("Cache-Control:no-cache") // no cache
    Call<ResponeLesson> search(@Query(PARAM_TOKEN) String token,
                               @Query(PARAM_KEYWORD) String password);


    @GET("api/elearning/list-quizz")
    @Headers("Cache-Control:no-cache") // no cache
    Call<ResponeAnswer> quizzById (@Query(PARAM_TOKEN) String token,
                                   @Query(PARAM_LESSON_ID) int id,
                                   @Query(PARAM_PAGE) int page,
                                   @Query(PARAM_NUMBER_PER_PAGE) int number_per_page);



}
