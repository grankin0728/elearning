package com.suusoft.elistening.configs;

import android.content.Context;

import com.suusoft.elistening.model.Language;
import com.suusoft.elistening.model.OurApp;
import com.suusoft.elistening.model.Theme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Config {

    // 0: pager; 1: list vertical
    public static final int TYPE_ITEM_CHAPTER = 0;
    public static final int COLUMN_3 = 3;


    // Config background
    public static final String TYPE_BACKGROUND_LIGHT = "Light";
    public static final String TYPE_BACKGROUND_DARK = "Dark";

    // config type list
    public static final String TYPE_LIST_NAME = "Listview";
    public static final String TYPE_GRID_NAME = "Gridview";
    public static final int ID_TYPE_LIST = 1;
    public static final int ID_TYPE_GRID = 2;

    // config type list
    public static final int ID_GRID_2_COLUMN = 2;
    public static final int ID_GRID_3_COLUMN = 3;
    public static final String NAME_GRID_2_COLUMN = "Two column";
    public static final String NAME_GRID_3_COLUMN = "Three column";

    // config type display answer
    public static final String TYPE_QUESTION_DISPLAY_WITH_TEXT_NAME = "Displayed with text";
    public static final String TYPE_LAST_QUESTION_NAME = "Last displayed question";
    public static final int TYPE_QUESTION_DISPLAY_WITH_TEXT = 1;
    public static final int TYPE_LAST_QUESTION = 2;

    // Config type category
    public static final int TYPE_CATEGORY_TYPE_1 = 1;// grid. 2 column
    public static final int TYPE_CATEGORY_TYPE_2 = 2;// list, full parent
    public static final int TYPE_CATEGORY_TYPE_3 = 3;// list, name:image
    public static final int TYPE_CATEGORY_TYPE_4 = 4;//list, image:name, description

    public static int getTypeCategory() {
        return TYPE_CATEGORY_TYPE_2;
    }

    // Config theme
    public static final String FLLE_THEME_NAME = "configTheme.json";
    public static final String KEY_DATA = "data";
    public static final String KEY_NAME = "name";
    public static final String KEY_COLOR_PRIMARY = "colorPrimary";
    public static final String KEY_COLOR_PRIMARY_DARK = "colorPrimaryDark";
    public static final String KEY_COLOR_ACCENT = "colorAccent";
    public static final String KEY_BACKGROUND_MAIN_DARK = "background_main_dark";
    public static final String KEY_TEXT_COLOR_PRIMARY_DARK = "textColorPrimary_dark";
    public static final String KEY_TEXT_COLOR_SECONDARY_DARK = "textColorSecondary_dark";
    public static final String KEY_BACKGROUND_MAIN_LIGHT = "background_main_light";
    public static final String KEY_TEXT_COLOR_PRIMARY_LIGHT = "textColorPrimary_light";
    public static final String KEY_TEXT_COLOR_SECONDARY_LIGHT = "textColorSecondary_light";
    public static final String KEY_TEXT_PLAY_HIGHTLIGHT_LIGHT = "textPlayHightLight_light";
    public static final String KEY_TEXT_PLAY_HIGHTLIGHT_DARK = "textPlayHightLight_dark";
    public static final String KEY_COLOR_TAB_SELECTED_LIGHT = "colorTabSelected_light";
    public static final String KEY_COLOR_TAB_SELECTED_DARK = "colorTabSelected_dark";
    public static final String KEY_COLOR_TAB_NORMAL_LIGHT = "colorTabNormal_light";
    public static final String KEY_COLOR_TAB_NORMAL_DARK = "colorTabNormal_dark";

    // Config More App
    public static final String FLLE_MORE_APP_NAME = "configMoreApp.json";
    public static final String KEY_ID = "id";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_LINK_SITE = "link_site";
    public static final String KEY_LINK_STORE = "link_store";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_SEARCH_MORE_APP_GOOGLE_PLAY = "suusoft";
    public static final int TYPE_MORE_APP_FROM_GOOGLE_PLAY = 0;
    public static final int TYPE_MORE_APP_FROM_FILE_JSON = 1;

    public static int getTypeMoreApp(Context context) {
        ArrayList<Theme> listTheme = getListTheme(context);
        if (listTheme != null && listTheme.size() > 0) {
            return Config.TYPE_MORE_APP_FROM_FILE_JSON;
        } else {
            return Config.TYPE_MORE_APP_FROM_GOOGLE_PLAY;
        }
    }

    // Config Language
    public static final String FLLE_LANGUAGE_NAME = "configLanguage.json";
    public static final String KEY_CODE = "code";

    public static String loadJSONFromAsset(Context context, String file) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static ArrayList<Theme> getListTheme(Context context) {
        ArrayList<Theme> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, FLLE_THEME_NAME));
            JSONArray m_jArry = obj.getJSONArray(KEY_DATA);
            Theme theme;
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                theme = new Theme(jo_inside.getString(KEY_NAME), jo_inside.getString(KEY_COLOR_PRIMARY),
                        jo_inside.getString(KEY_COLOR_PRIMARY_DARK), jo_inside.getString(KEY_COLOR_ACCENT),
                        jo_inside.getString(KEY_BACKGROUND_MAIN_DARK), jo_inside.getString(KEY_TEXT_COLOR_PRIMARY_DARK),
                        jo_inside.getString(KEY_TEXT_COLOR_SECONDARY_DARK), jo_inside.getString(KEY_BACKGROUND_MAIN_LIGHT),
                        jo_inside.getString(KEY_TEXT_COLOR_PRIMARY_LIGHT), jo_inside.getString(KEY_TEXT_COLOR_SECONDARY_LIGHT),
                        jo_inside.getString(KEY_TEXT_PLAY_HIGHTLIGHT_LIGHT), jo_inside.getString(KEY_TEXT_PLAY_HIGHTLIGHT_DARK),
                        jo_inside.getString(KEY_COLOR_TAB_SELECTED_LIGHT), jo_inside.getString(KEY_COLOR_TAB_SELECTED_DARK),
                        jo_inside.getString(KEY_COLOR_TAB_NORMAL_LIGHT), jo_inside.getString(KEY_COLOR_TAB_NORMAL_DARK));
                list.add(theme);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<OurApp> getListOurApp(Context context) {
        ArrayList<OurApp> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, FLLE_MORE_APP_NAME));
            JSONArray m_jArry = obj.getJSONArray(KEY_DATA);
            OurApp ourApp;
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                ourApp = new OurApp(jo_inside.getString(KEY_ID), jo_inside.getString(KEY_NAME),
                        jo_inside.getString(KEY_IMAGE), jo_inside.getString(KEY_LINK_SITE),
                        jo_inside.getString(KEY_LINK_STORE), jo_inside.getString(KEY_DESCRIPTION));
                list.add(ourApp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Language> getListLanguage(Context context) {
        ArrayList<Language> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, FLLE_LANGUAGE_NAME));
            JSONArray m_jArry = obj.getJSONArray(KEY_DATA);
            Language language;
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                language = new Language(jo_inside.getString(KEY_CODE), jo_inside.getString(KEY_NAME));
                list.add(language);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }



}
