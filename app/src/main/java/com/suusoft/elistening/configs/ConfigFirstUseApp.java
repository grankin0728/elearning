package com.suusoft.elistening.configs;

import android.content.Context;

import com.suusoft.elistening.model.DisplayQuestionType;
import com.suusoft.elistening.model.GridInterface;
import com.suusoft.elistening.model.Language;
import com.suusoft.elistening.model.ListType;
import com.suusoft.elistening.model.Theme;

public class ConfigFirstUseApp {

    //Language default
    public static Language getLanguageDefault(Context context) {
        return Config.getListLanguage(context).get(0);
    }

    //Theme default
    public static Theme getThemeDefault(Context context) {
        return Config.getListTheme(context).get(0);
    }

    // List type default
    public static ListType listType = new ListType(Config.ID_TYPE_GRID, Config.TYPE_GRID_NAME);


    // List type default
    public static GridInterface gridInterface = new GridInterface(Config.ID_GRID_2_COLUMN, Config.NAME_GRID_2_COLUMN);

    // Display answer type default
    public static DisplayQuestionType displayQuestionType =
            new DisplayQuestionType(Config.TYPE_LAST_QUESTION, Config.TYPE_LAST_QUESTION_NAME);

    // Background defaul
    public static String backgroundDefault = Config.TYPE_BACKGROUND_LIGHT;
}
