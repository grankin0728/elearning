package com.suusoft.elistening.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.modelmanager.RequestManager;
import com.suusoft.elistening.network.ApiResponse;
import com.suusoft.elistening.network.BaseRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SuuSoft on 3/8/2018.
 */

public class SaveLessonInDBService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
        RequestManager.getListLesson(this,DataStoreManager.getToken(),  new BaseRequest.CompleteListener() {
            @Override
            public void onSuccess(ApiResponse response) {
                List<Lesson> lessons = response.getDataList(Lesson.class);
                if (lessons!=null){
                    DataStoreManager.removeDataInTableLesson();
                    DataStoreManager.saveNewData((ArrayList<Lesson>) lessons);
                    DataStoreManager.setStatusGetData(true);
                    Log.e("SaveLessonInDBService", "setSaveData");
                }
            }

            @Override
            public void onError(String message) {
                Log.e("SaveLessonInDBService", "onError " + message);
            }
        });
    }
}
