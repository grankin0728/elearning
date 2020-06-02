package com.suusoft.elistening.configs;

import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.model.DisplayQuestionType;
import com.suusoft.elistening.model.GridInterface;
import com.suusoft.elistening.model.ListType;
import com.suusoft.elistening.model.Theme;

/**
 * Created by Suusoft on 10/31/2017.
 */

public class GlobalValue {

    private static Theme mTheme ;

    private static String mStrBackgroundApp ;

    private static ListType mListType;

    private static GridInterface mGridInterface;

    private static DisplayQuestionType mDisplayQuestionType;

    public static void updateValue(){
        mTheme = DataStoreManager.getTheme();
        mStrBackgroundApp = DataStoreManager.getTypeBackgroundApp();
        mListType = DataStoreManager.getListType();
        mGridInterface = DataStoreManager.getGridColumn();
        mDisplayQuestionType = DataStoreManager.getTypeDisplayQuestion();
    }

    public static Theme getTheme() {
        if (mTheme==null)
            mTheme = DataStoreManager.getTheme();
        return mTheme;
    }

    public static void setTheme(Theme mTheme) {
        GlobalValue.mTheme = mTheme;
    }

    public static String getTypeBackgroundApp() {
        if (mStrBackgroundApp==null)
            mStrBackgroundApp = DataStoreManager.getTypeBackgroundApp();
        return mStrBackgroundApp;
    }

    public static void setStrBackgroundApp(String mStrBackgroundApp) {
        GlobalValue.mStrBackgroundApp = mStrBackgroundApp;
    }

    public static ListType getListType() {
        if (mListType==null)
            mListType = DataStoreManager.getListType();
        return mListType;
    }

    public static void setListType(ListType mListType) {
        GlobalValue.mListType = mListType;
    }

    public static GridInterface getGridColumn() {
        if (mGridInterface==null)
            mGridInterface = DataStoreManager.getGridColumn();
        return mGridInterface;
    }

    public static void setGridInterface(GridInterface mGridInterface) {
        GlobalValue.mGridInterface = mGridInterface;
    }

    public static DisplayQuestionType getTypeDisplayQuestion() {
        if (mDisplayQuestionType==null)
            mDisplayQuestionType = DataStoreManager.getTypeDisplayQuestion();
        return mDisplayQuestionType;
    }

    public static void setDisplayQuestionType(DisplayQuestionType mDisplayQuestionType) {
        GlobalValue.mDisplayQuestionType = mDisplayQuestionType;
    }
}
