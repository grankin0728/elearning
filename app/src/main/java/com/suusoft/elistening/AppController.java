package com.suusoft.elistening;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;
import android.widget.LinearLayout;


import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.suusoft.elistening.DaoDownload.DownloadList;
import com.suusoft.elistening.datastore.BaseDataStore;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.model.modelLesson.Category;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.modelmanager.RequestManager;
import com.suusoft.elistening.network.ApiResponse;
import com.suusoft.elistening.network.BaseRequest;
import com.suusoft.elistening.network.VolleyRequestManager;
import com.suusoft.elistening.util.AppUtil;
import com.suusoft.elistening.widgets.textview.TypeFaceUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Suusoft on 1/8/2016.
 */
public class AppController extends Application {

    private List<Lesson> lessons;

    // TODO: 7/11/2016 Need comment
    private static AppController instance;
    private String token;

    //Used to lock the menu option when the question is displayed
    public static boolean isShowQuestionInPagePlaying = false,
            isPausePlayerInPageQuestion = false;
    public static int position = 0;

    public static LinearLayout.LayoutParams layoutParamsGrid;
    public static LinearLayout.LayoutParams layoutParamsList;

    private int global;
    // array Lesson
    private static List<Lesson> arrLessons;
    private static List<DownloadList> arrDownload;
    private static ArrayList<Category> categories;

    public static ArrayList<Category> getCategories() {
        return categories;
    }

    public static void setCategories(ArrayList<Category> categories) {
        AppController.categories = categories;
    }

    // index of lesson
    private int lessonIndex = 0;

    public static AppController getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        BaseDataStore.init(getApplicationContext());
        VolleyRequestManager.init(getApplicationContext());
        BaseRequest.init(this);
        TypeFaceUtil.getInstant().initTypeFace(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

//        initData1();
//        initData();

    }

    private void initData1() {
        DataStoreManager.setStatusGetData(false);
        RequestManager.getListLesson(getApplicationContext(),DataStoreManager.getToken(), new BaseRequest.CompleteListener() {
            @Override
            public void onSuccess(ApiResponse response) {
                List<Lesson>  lessons = response.getDataList(Lesson.class);
                if (lessons!=null){
                    DataStoreManager.removeDataInTableLesson();
                    DataStoreManager.saveNewData((ArrayList<Lesson>) lessons);
                    DataStoreManager.setStatusGetData(true);
                    Log.e("saveData", "setSaveData");
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }


    private void initData() {
        DataStoreManager.setStatusGetData(false);
        RequestManager.getListLesson(getApplicationContext(),DataStoreManager.getToken(), new BaseRequest.CompleteListener() {
            @Override
            public void onSuccess(ApiResponse response) {
                List<Lesson>  lessons = response.getDataList(Lesson.class);
                if (lessons!=null){
                    if (DataStoreManager.getSaveData()){
                        Log.e("saveData", "getSaveData = true ");
                        for (int i = 0; i < lessons.size(); i++){
                            lessons.get(i).setDownload(DataStoreManager.isDownload( lessons.get(i)));
                            lessons.get(i).setFavorite(DataStoreManager.isFavorite( lessons.get(i)));
                            lessons.get(i).setView(DataStoreManager.isView( lessons.get(i)));
                        }
                    }
                    DataStoreManager.saveListLesson((ArrayList<Lesson>) lessons);
                    DataStoreManager.setSaveData(true);
                    DataStoreManager.setStatusGetData(true);
                    Log.e("saveData", "setSaveData");
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }



    public boolean addDlIndex(int index) {
        if (dlIndex == null) dlIndex = new ArrayList<>();
        if (dlIndex.size() > 0) {
            for (int i = 0; i < dlIndex.size(); i++) {
                if (dlIndex.get(i).intValue() == index) return false;
            }
        }
        dlIndex.add(new Integer(index));
        return true;
    }

    public void removeDlIndex(int index) {
        if (dlIndex == null) dlIndex = new ArrayList<>();
        if (dlIndex.size() > 0) {
            for (int i = 0; i < dlIndex.size(); i++) {
                if (dlIndex.get(i).intValue() == index) dlIndex.remove(i);
                break;
            }
        }
    }

    public void setArrLessons(List<Lesson> arrLessons) {
        this.arrLessons = arrLessons;
    }


    private ArrayList<Integer> dlIndex;

    public int getLessonIndex() {
        return lessonIndex;
    }

    public void setLessonIndex(int index) {
        if (index < 0) {
            this.lessonIndex = 0;
            return;
        } else if (index > this.arrLessons.size() - 1) {
            this.lessonIndex = this.arrLessons.size() - 1;
            return;
        }
        this.lessonIndex = index;
    }

    public Lesson getLessonAt(int i) {
        if(arrLessons!=null){
            if (arrLessons.get(i)==null){
                return null;
            }else
                return arrLessons.get(i);
        }else return null;

    }

    public List<Lesson> getArrLessons() {
        if (this.arrLessons == null) {
            arrLessons = new ArrayList<>();
        }
        return arrLessons;
    }


    public String getToken() {
        if (token == null)
            token = DataStoreManager.getToken();
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getGlobal() {
        return global;
    }

    public void setGlobal(int global) {
        this.global = global;
    }


}
