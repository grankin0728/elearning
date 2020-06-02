package com.suusoft.elistening.retrofit;

import com.suusoft.elistening.AppController;
import com.suusoft.elistening.R;

public class ApiUtils {

    public static final String BASE_URL = AppController.getInstance().getString(R.string.URL_API);

    public static final String TAG = ApiUtils.class.getSimpleName ();

    public static APIService getAPIService() {
        //Log.e (TAG, "getAPIService: " + RetrofitClient.getClient (BASE_URL).toString () );
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);

    }

}
