package com.suusoft.elistening.datastore;

import com.google.gson.Gson;
import com.suusoft.elistening.datastore.db.DbConnection;
import com.suusoft.elistening.model.DisplayQuestionType;
import com.suusoft.elistening.model.GridInterface;
import com.suusoft.elistening.model.Language;
import com.suusoft.elistening.model.ListType;
import com.suusoft.elistening.model.Theme;
import com.suusoft.elistening.model.User;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.model.modelLesson.WatchLesson;
import com.suusoft.elistening.util.StringUtil;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Suusoft on 5/20/2016.
 */
public class DataStoreManager extends BaseDataStore {

    // ============== User ============================
    private static final String PREF_USER = "PREF_USER";
    private static final String PREF_TOKEN_USER = "PREF_TOKEN_USER";
    public static final String CHECK_FIRST_INSTALL_APP = "CHECK_FIRST_INSTALL_APP";
    public static final String CHECK_SAVE_DATA = "CHECK_SAVE_DATA";
    public static final String PREF_STATUS_GET_DATA = "PREF_STATUS_GET_DATA";
    private static final String PREF_DISPLAY_TYPE_LIST = "PREF_DISPLAY_TYPE_LIST";
    private static final String PREF_EXPAND_WORD = "PREF_EXPAND_WORD";
    private static final String PREF_DISPLAY_TYPE_LIST_MAIN = "PREF_DISPLAY_TYPE_LIST_MAIN";
    private static final String PREF_COUNT_ACCESS = "PREF_COUNT_ACCESS";
    public static final String PREF_THEME = "PREF_THEME";
    public static final String PREF_LIST_TYPE = "PREF_LIST_TYPE";
    public static final String PREF_TYPE_DISPLAY_QUESTION = "PREF_TYPE_DISPLAY_QUESTION";
    public static final String PREF_TYPE_BACKGROUND_APP = "PREF_TYPE_BACKGROUND_APP";
    public static final String PREF_LANGUAGE = "PREF_LANGUAGE";
    public static final String PREF_LANGUAGES = "PREF_LANGUAGES";

    public static final String PREF_SHOW_ACTIVITI_ADMOB = "PREF_SHOW_ACTIVITI_ADMOB";
    public static final String PREF_GRID_COLUMN = "PREF_GRID_COLUMN";


    private static DataStoreManager instance;
    private MySharedPreferences sharedPreferences;
    private DbConnection dbConnection;


    /**
     * save and get user
     */
    public static void saveUser(User user) {
        if (user != null) {
            String jsonUser = user.toJSon();
            getInstance().sharedPreferences.putStringValue(PREF_USER, jsonUser);
        }
    }

    public static void removeUser() {
        getInstance().sharedPreferences.putStringValue(PREF_USER, null);
    }

    public static User getUser() {
        String jsonUser = getInstance().sharedPreferences.getStringValue(PREF_USER);
        User user = new Gson().fromJson(jsonUser, User.class);
        return user;
    }

    /**
     * save and get user's token
     */
    public static void saveToken(String token) {
        getInstance().sharedPreferences.putStringValue(PREF_TOKEN_USER, token);
    }

    public static String getToken() {
        return getInstance().sharedPreferences.getStringValue(PREF_TOKEN_USER);
    }

    /**
     * save and get caching time
     */
    public static String getCaching(String request) {
        return getInstance().dbConnection.getCaching(StringUtil.getAction(request));
    }

    public static void saveCaching(String url, String objectRoot, String timeCaching) {
        getInstance().dbConnection.saveCaching(StringUtil.getAction(url), objectRoot, timeCaching);
    }


    public static ArrayList<Lesson> getLessonAsLevel(String level) {
        try {
            return getInstance().dbConnection.getLessonAsLevel(level);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ArrayList<Lesson> getLessonAsCategory(String idCategory) {
        try {
            return getInstance().dbConnection.getLessonAsCategory(idCategory);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ArrayList<Lesson> getLessonAsFavoriteOrDownload(String att) {
//        try {
//            return getInstance().dbConnection.getLessonByAtt(att);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;

        try {
            return getInstance().dbConnection.getLessonByFavoriteOrDownload(att);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void clearBook(String key) {
        getInstance().sharedPreferences.putStringValue(key, "");
    }

    public static void putInt(String key, int values) {
        getInstance().sharedPreferences.putIntValue(key, values);
    }

    public static int getInt(String key, int defaultValues) {
        return getInstance().sharedPreferences.getIntValue(key, defaultValues);
    }

    public static boolean getBoolean(String key) {
        return getInstance().sharedPreferences.getBooleanValue(key);
    }

    public static void putBoolean(String key, boolean values) {
        getInstance().sharedPreferences.putBooleanValue(key, values);
    }

    // Setting

    public static final String Key_SETTING_TEXT_SIZE = "Key_SETTING_TEXT_SIZE";

    public static int getSettingTextSize() {
        return getInt(Key_SETTING_TEXT_SIZE, 12);
    }

    public static void setSettingTextSize(int textSize) {
        putInt(Key_SETTING_TEXT_SIZE, textSize);
    }


    public static final String KEY_SETTING_SCREEN_ON = "KEY_SETTING_SCREEN_ON";

    public static boolean getSettingScreenOn() {
        return getBoolean(KEY_SETTING_SCREEN_ON);
    }

    public static void setSettingScreenOn(boolean screenOn) {
        putBoolean(KEY_SETTING_SCREEN_ON, screenOn);
    }


    /**
     * save first install
     */
    public static void saveFirstInstall(boolean isFirstInstall) {
        getInstance().sharedPreferences.putBooleanValue(CHECK_FIRST_INSTALL_APP, isFirstInstall);
    }

    /**
     * get firt install
     */
    public static boolean getFirstInstall() {
        return getInstance().sharedPreferences.getBooleanValue(CHECK_FIRST_INSTALL_APP);
    }

    /**
     * get save data
     */
    public static boolean getSaveData() {
        return getInstance().sharedPreferences.getBooleanValue(CHECK_SAVE_DATA);
    }

    /**
     * save data
     */
    public static void setSaveData(boolean isSaveData) {
        getInstance().sharedPreferences.putBooleanValue(CHECK_SAVE_DATA, isSaveData);
    }


    public static void setStatusGetData(boolean isStatusGetData) {
        getInstance().sharedPreferences.putBooleanValue(PREF_STATUS_GET_DATA, isStatusGetData);
    }


    public static boolean getStatusGetData( ) {
       return getInstance().sharedPreferences.getBooleanValue(PREF_STATUS_GET_DATA);
    }

    /**
     * save Theme to sharePreferences
     */
    public static void saveTheme(Theme theme) {
        if (theme != null) {
            String jsonTheme = theme.toJSon();
            DataStoreManager.getInstance().sharedPreferences.putStringValue(PREF_THEME, jsonTheme);
        }
    }

    /**
     * get Theme from sharePreferences
     */
    public static Theme getTheme() {
        String jsonTheme = DataStoreManager.getInstance().sharedPreferences.getStringValue(PREF_THEME);
        Theme theme = new Gson().fromJson(jsonTheme, Theme.class);
        return theme;
    }

    /**
     * save list type to sharePreferences
     */
    public static void saveListType(ListType listType) {
        if (listType != null) {
            String jsonListType = listType.toJSon();
            DataStoreManager.getInstance().sharedPreferences.putStringValue(PREF_LIST_TYPE, jsonListType);
        }
    }

    /**
     * get list type from sharePreferences
     */
    public static ListType getListType() {
        String jsonListType = DataStoreManager.getInstance().sharedPreferences.getStringValue(PREF_LIST_TYPE);
        ListType listType = new Gson().fromJson(jsonListType, ListType.class);
        return listType;
    }


    /**
     * save list type to sharePreferences
     */
    public static void saveGridColumn(GridInterface gridInterface) {
        if (gridInterface != null) {
            String jsonListType = gridInterface.toJSon();
            DataStoreManager.getInstance().sharedPreferences.putStringValue(PREF_GRID_COLUMN, jsonListType);
        }
    }

    /**
     * get list type from sharePreferences
     */
    public static GridInterface getGridColumn() {
        String jsonListType = DataStoreManager.getInstance().sharedPreferences.getStringValue(PREF_GRID_COLUMN);
        GridInterface gridInterface = new Gson().fromJson(jsonListType, GridInterface.class);
        return gridInterface;
    }



    /**
     * save type display question to sharePreferences
     */
    public static void saveTypeDisplayQuestion(DisplayQuestionType displayQuestionType) {
        if (displayQuestionType != null) {
            String jsonTypeDisplayQuestion = displayQuestionType.toJSon();
            DataStoreManager.getInstance().sharedPreferences.putStringValue(PREF_TYPE_DISPLAY_QUESTION, jsonTypeDisplayQuestion);
        }
    }

    /**
     * get type display question from sharePreferences
     */
    public static DisplayQuestionType getTypeDisplayQuestion() {
            String jsonTypeDisplayQuestion = DataStoreManager.getInstance().sharedPreferences.getStringValue(PREF_TYPE_DISPLAY_QUESTION);
            DisplayQuestionType displayQuestionType = new Gson().fromJson(jsonTypeDisplayQuestion, DisplayQuestionType.class);
            return displayQuestionType;
    }

    /**
     * save type background app to sharePreferences
     */
    public static void saveTypeBackgroundApp(String type) {
        DataStoreManager.getInstance().sharedPreferences.putStringValue(PREF_TYPE_BACKGROUND_APP, type);
    }

    /**
     * get type background app from sharePreferences
     */
    public static String getTypeBackgroundApp() {
       return DataStoreManager.getInstance().sharedPreferences.getStringValue(PREF_TYPE_BACKGROUND_APP);
    }
    /**
     * save language app to sharePreferences
     */
    public static void saveLanguage(Language language) {
        if (language != null) {
            String jsonLanguage = language.toJSon();
            DataStoreManager.getInstance().sharedPreferences.putStringValue(PREF_LANGUAGE, jsonLanguage);
        }
    }

    /**
     * get language app from sharePreferences
     */
    public static Language getLanguage() {
        String jsonLanguage = DataStoreManager.getInstance().sharedPreferences.getStringValue(PREF_LANGUAGE);
        Language language = new Gson().fromJson(jsonLanguage, Language.class);
        return language;
    }


    public static void saveLanguages(int lang) {
        getInstance().sharedPreferences.putIntValue(PREF_LANGUAGES, lang);
    }

    public static int getLanguages() {
        int lang = DataStoreManager.getInstance().sharedPreferences.getIntValue(PREF_LANGUAGES);
        return lang;
    }

    /**
     * save data
     */

    public static void saveListLesson(ArrayList<Lesson> arrLesson){
        if (!DataStoreManager.getSaveData()) {
            for (int i = 0; i < arrLesson.size(); i ++){
                getInstance().dbConnection.saveLessonFrist(arrLesson.get(i));
            }
        }else {
            for (int i = 0; i < arrLesson.size(); i ++){
                getInstance().dbConnection.saveLesson(arrLesson.get(i));
            }
        }


    }

    /**
     * save new data
     */

    public static void saveNewData(ArrayList<Lesson> arrLesson){
        getInstance().dbConnection.saveNewData(arrLesson);
    }

    /**
     * save status view of lesson
     */

    public static void saveWatchLessonFirst(int idLesson){
        getInstance().dbConnection.saveWatchLessonFirst(idLesson);
    }

    /**
     * get status view of lesson
     */

    public static WatchLesson getWatchLesson(int idLesson){
        return getInstance().dbConnection.getStatusWatchLesson(idLesson);
    }

    public static void updateViewLesson(WatchLesson watchLesson){
        getInstance().dbConnection.updateViewLesson(watchLesson);
    }

    public static void updateDownloadLesson(WatchLesson watchLesson){
        getInstance().dbConnection.updateDownloadLesson(watchLesson);
    }

    public static void updateFavoriteLesson(WatchLesson watchLesson){
        getInstance().dbConnection.updateFavoriteLesson(watchLesson);
    }


    public static void removeDataInTableLesson(){
        getInstance().dbConnection.removeDataInTable();
    }


    /**
     * update data
     */

    public static void updateListLessonInDb(ArrayList<Lesson> arrLesson){
        getInstance().dbConnection.upDateListLesson(arrLesson);
    }


    public static void updateFavorite(Lesson lesson) {
        getInstance().dbConnection.updateFavorite(lesson);
    }

    public static boolean isFavorite(Lesson lesson) {
        return getInstance().dbConnection.isFavorite(lesson);
    }


    public static void updateDownload(Lesson lesson) {
        getInstance().dbConnection.updateDownload(lesson);
    }




    public static void updateView(Lesson lesson) {
        getInstance().dbConnection.updateView(lesson);
    }

    public static boolean isDownload(Lesson lesson){
        return getInstance().dbConnection.getDownload(lesson);
    }


    public static boolean isView(Lesson lesson){
        return getInstance().dbConnection.isWatch(lesson);
    }


    /**
     * save and get display type
     */

    public static boolean isDisplayTypeList() {
        return getInstance().sharedPreferences.getBooleanValue(PREF_DISPLAY_TYPE_LIST);
    }

    public static void saveDisplayTypeList(boolean isDisplayTypeList) {
        getInstance().sharedPreferences.putBooleanValue(PREF_DISPLAY_TYPE_LIST, isDisplayTypeList);
    }


    /**
     * save and get ExpandListWord type
     */

    public static boolean isExpandListWord() {
        return getInstance().sharedPreferences.getBooleanValue(PREF_EXPAND_WORD);
    }

    public static void saveExpandListWord(boolean isDisplayTypeList) {
        getInstance().sharedPreferences.putBooleanValue(PREF_EXPAND_WORD, isDisplayTypeList);
    }

    /**
     * save and get display type MAIN
     */

    public static boolean isDisplayTypeListMain() {
        return getInstance().sharedPreferences.getBooleanValue(PREF_DISPLAY_TYPE_LIST_MAIN);
    }

    public static void saveDisplayTypeListMain(boolean isDisplayTypeList) {
        getInstance().sharedPreferences.putBooleanValue(PREF_DISPLAY_TYPE_LIST_MAIN, isDisplayTypeList);
    }


    /**
     * save and get count access to show admob. khi vào page nghe nhạc quá 5 lần
     */

    public static int getCountAccess() {
        return getInstance().sharedPreferences.getIntValue(PREF_COUNT_ACCESS);
    }

    public static void saveCountAccess(int countAccess) {
        getInstance().sharedPreferences.putIntValue(PREF_COUNT_ACCESS, countAccess);
    }

    public static boolean isShowActivityAdmob() {
        return getInstance().sharedPreferences.getBooleanValue(PREF_SHOW_ACTIVITI_ADMOB);
    }

    public static void setShowActivityAdmob(boolean isShowActivityAdmob) {
        getInstance().sharedPreferences.putBooleanValue(PREF_SHOW_ACTIVITI_ADMOB, isShowActivityAdmob);
    }

    public static boolean isGridTwoColumn() {
        return getInstance().sharedPreferences.getBooleanValue(PREF_GRID_COLUMN);
    }

    public static void setGridTwoColumn(boolean isGridTwoColumn) {
        getInstance().sharedPreferences.putBooleanValue(PREF_GRID_COLUMN, isGridTwoColumn);
    }

}
