package com.suusoft.elistening.view.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.suusoft.elistening.R;
import com.suusoft.elistening.configs.ConfigFirstUseApp;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.listener.IListenerGetData;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.modelmanager.RequestManager;
import com.suusoft.elistening.network.ApiResponse;
import com.suusoft.elistening.network.BaseRequest;
import com.suusoft.elistening.util.AppUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SplashLoadActivity extends AppCompatActivity  {

    private ProgressBar progressBar;
    private IListenerGetData iListenerGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_load);
        checkFirstSettingApp();
        gotoMain();

        //initData();
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
                    Log.e("getListLesson", "setSaveData");

                    gotoMain();

                }
            }

            @Override
            public void onError(String message) {
                Log.e("getListLesson", "onError " + message);
            }
        });
    }

    public void gotoMain(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setLanguage();
                if (DataStoreManager.getUser() != null) {
                    AppUtil.startActivityLTR(SplashLoadActivity.this, MainActivity.class);
                }else {
                    AppUtil.startActivityLTR(SplashLoadActivity.this, LoginActivity.class);
                }

                //startService(new Intent(SplashLoadActivity.this, SaveLessonInDBService.class));
                finish();
            }
        }, 2000);
    }

    /**
     * check first setting app
     */
    private void checkFirstSettingApp() {
        if (!DataStoreManager.getFirstInstall()) {
            Log.e("FIRST INSTALL APP:", "First install app");
            DataStoreManager.saveFirstInstall(true);
            DataStoreManager.saveTheme(ConfigFirstUseApp.getThemeDefault(this));
            DataStoreManager.saveListType(ConfigFirstUseApp.listType);
            DataStoreManager.saveGridColumn(ConfigFirstUseApp.gridInterface);
            DataStoreManager.saveTypeBackgroundApp(ConfigFirstUseApp.backgroundDefault);
            DataStoreManager.saveLanguage(ConfigFirstUseApp.getLanguageDefault(this));
            DataStoreManager.saveTypeDisplayQuestion(ConfigFirstUseApp.displayQuestionType);
        }

        GlobalValue.updateValue();

    }

    public void setLanguage() {
        Locale myLocale = new Locale(DataStoreManager.getLanguage().getCode());
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources()
                .getDisplayMetrics());
    }

}
