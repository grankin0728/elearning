package com.suusoft.elistening.viewmodel.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import androidx.databinding.Bindable;
import android.os.Environment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.AbstractActivity;
import com.suusoft.elistening.base.vm.BaseViewModel;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.model.DisplayQuestionType;
import com.suusoft.elistening.model.GridInterface;
import com.suusoft.elistening.model.Language;
import com.suusoft.elistening.model.ListType;
import com.suusoft.elistening.model.Theme;
import com.suusoft.elistening.util.AppUtil;
import com.suusoft.elistening.util.DialogUtil;
import com.suusoft.elistening.view.activity.SplashLoadActivity;
import com.suusoft.elistening.view.adapter.SelectAdapter;
import com.suusoft.elistening.view.adapter.SelectGridColumnAdapter;
import com.suusoft.elistening.view.adapter.SelectLanguageAdapter;
import com.suusoft.elistening.view.adapter.SelectListTypeAdapter;
import com.suusoft.elistening.view.adapter.SelectThemeAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Ha on 9/12/2016.
 */
public class SettingsVM extends BaseViewModel {
    private boolean isScreenOn;
    private int texSize;

    @Bindable
    public String getBackgroundMain() {
        if (Config.TYPE_BACKGROUND_DARK.equals(DataStoreManager.getTypeBackgroundApp())) {
            return DataStoreManager.getTheme().getBackgroundMainDark();
        } else {
            return DataStoreManager.getTheme().getBackgroundMainLight();
        }
    }

    @Bindable
    public String getTextColorPrimary() {
        if (Config.TYPE_BACKGROUND_DARK.equals(DataStoreManager.getTypeBackgroundApp())) {
            return DataStoreManager.getTheme().getTextColorPrimaryDark();
        } else {
            return DataStoreManager.getTheme().getTextColorPrimaryLight();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void getData(int page) {

    }

    public SettingsVM(Context self) {
        super(self);
        texSize = DataStoreManager.getSettingTextSize();
        isScreenOn = DataStoreManager.getSettingScreenOn();
    }

    public void onClickApply(View view) {
        if (isScreenOn == DataStoreManager.getSettingScreenOn() && texSize == DataStoreManager.getSettingTextSize()) {
            ((AbstractActivity) self).showSnackBar(R.string.not_change);
        } else {
            ((AbstractActivity) self).showSnackBar(R.string.saved);
            DataStoreManager.setSettingScreenOn(isScreenOn);
            DataStoreManager.setSettingTextSize(texSize);
        }
    }

    public boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    public void onClickCleanCache(View view) {
        DialogUtil.showAlertDialog(self, R.string.clear_cache, R.string.title_clear_cache, new DialogUtil.IDialogConfirm() {
            @Override
            public void onClickOk() {
                boolean delete = deleteDirectory(new File(Environment.getExternalStorageDirectory() + Constant.FOLDER_EBOOK));
                if (delete) {
                    ((AbstractActivity) self).showSnackBar(R.string.successfully);
                } else {
                    ((AbstractActivity) self).showSnackBar(R.string.no_data_clean);
                }
            }
        });

    }

    public boolean getShowAdmob(){
        return DataStoreManager.isShowActivityAdmob();
    }

    public void onClickShowAdmob(View view){
        DataStoreManager.setShowActivityAdmob(!DataStoreManager.isShowActivityAdmob());
    }

    public void onClickChangeTheme(View view) {
        final ArrayList<Theme> mListTheme = Config.getListTheme(self);

        final Dialog dialog = new Dialog(self);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_select_theme);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        // Get view
        final RecyclerView rcvData = (RecyclerView) dialog.findViewById(R.id.rcvData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(self, LinearLayoutManager.VERTICAL, false);
        rcvData.setLayoutManager(linearLayoutManager);
        SelectThemeAdapter selectThemeAdapter = new SelectThemeAdapter(self, mListTheme,
                new SelectThemeAdapter.ItemClickListener() {
                    @Override
                    public void onClickItem(int position) {
                        DataStoreManager.saveTheme(mListTheme.get(position));
                        GlobalValue.setTheme(mListTheme.get(position));
                        AppUtil.sendBroadcastListener(self, Constant.LISTEN_CHANGE_THEME);
//                        notifyPropertyChanged(BR.themeName);
//                        notifyPropertyChanged(BR.backgroundMain);
//                        notifyPropertyChanged(BR.textColorPrimary);
                        dialog.dismiss();

                    }
                });
        rcvData.setAdapter(selectThemeAdapter);
        dialog.show();
    }

    public void onClickChangeListType(View view) {
        final ArrayList<ListType> mListType = new ArrayList<>();
        mListType.add(new ListType(Config.ID_TYPE_GRID, Config.TYPE_GRID_NAME));
        mListType.add(new ListType(Config.ID_TYPE_LIST, Config.TYPE_LIST_NAME));

        final Dialog dialog = new Dialog(self);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_select_list_type);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        // Get view
        final RecyclerView rcvData = (RecyclerView) dialog.findViewById(R.id.rcvData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(self, LinearLayoutManager.VERTICAL, false);
        rcvData.setLayoutManager(linearLayoutManager);
        SelectListTypeAdapter selectListTypeAdapter = new SelectListTypeAdapter(self, mListType,
                new SelectListTypeAdapter.ItemClickListener() {
                    @Override
                    public void onClickItem(int position) {
                        DataStoreManager.saveListType(mListType.get(position));
                        GlobalValue.setListType(mListType.get(position));
                        //notifyPropertyChanged(BR.listType);
                        dialog.dismiss();

                    }
                });
        rcvData.setAdapter(selectListTypeAdapter);
        dialog.show();
    }

    public void onClickChangeBackgoundApp(View view) {
        final ArrayList<String> mListBackgoundApp = new ArrayList<>();
        mListBackgoundApp.add(Config.TYPE_BACKGROUND_LIGHT);
        mListBackgoundApp.add(Config.TYPE_BACKGROUND_DARK);

        final Dialog dialog = new Dialog(self);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_select);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        // Get view
        final TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        tv_title.setText(self.getString(R.string.select_background_app));
        final RecyclerView rcvData = (RecyclerView) dialog.findViewById(R.id.rcvData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(self, LinearLayoutManager.VERTICAL, false);
        rcvData.setLayoutManager(linearLayoutManager);
        SelectAdapter selectAdapter = new SelectAdapter(self, mListBackgoundApp,
                new SelectAdapter.ItemClickListener() {
                    @Override
                    public void onClickItem(int position) {
                        DataStoreManager.saveTypeBackgroundApp(mListBackgoundApp.get(position));
                        GlobalValue.setStrBackgroundApp(mListBackgoundApp.get(position));
                        AppUtil.sendBroadcastListener(self, Constant.LISTEN_CHANGE_THEME);
//                        notifyPropertyChanged(BR.backgroundApp);
//                        notifyPropertyChanged(BR.backgroundMain);
//                        notifyPropertyChanged(BR.textColorPrimary);
                        dialog.dismiss();

                    }
                });
        rcvData.setAdapter(selectAdapter);
        dialog.show();
    }

    public void onClickChangeLanguage(View view) {
        final ArrayList<Language> mListLanguage = Config.getListLanguage(self);

        final Dialog dialog = new Dialog(self);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_select);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        // Get view
        final TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        tv_title.setText(self.getString(R.string.select_language));
        final RecyclerView rcvData = (RecyclerView) dialog.findViewById(R.id.rcvData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(self, LinearLayoutManager.VERTICAL, false);
        rcvData.setLayoutManager(linearLayoutManager);
        SelectLanguageAdapter selectLanguageAdapter = new SelectLanguageAdapter(self, mListLanguage,
                new SelectLanguageAdapter.ItemClickListener() {
                    @Override
                    public void onClickItem(int position) {
                        DataStoreManager.saveLanguage(mListLanguage.get(position));
                        //notifyPropertyChanged(BR.language);
                        dialog.dismiss();
                        DialogUtil.showAlertDialog(self, R.string.app_name, R.string.msg_confirm_restart_app,
                                new DialogUtil.IDialogConfirm() {
                            @Override
                            public void onClickOk() {
                                updateLanguage();
                                reloadActivity();
                            }
                        });
                    }
                });
        rcvData.setAdapter(selectLanguageAdapter);
        dialog.show();
    }

    public void updateLanguage() {
        Locale myLocale = new Locale(DataStoreManager.getLanguage().getCode());
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        ((Activity)self).getBaseContext().getResources().updateConfiguration(config, ((Activity)self).getBaseContext().getResources().getDisplayMetrics());
    }

    public void onClickChangeDisplayQuestion(View view){

        final ArrayList<DisplayQuestionType> mQuestionTypes = new ArrayList<>();
        mQuestionTypes.add(new DisplayQuestionType(Config.TYPE_QUESTION_DISPLAY_WITH_TEXT, Config.TYPE_QUESTION_DISPLAY_WITH_TEXT_NAME));
        mQuestionTypes.add(new DisplayQuestionType(Config.TYPE_LAST_QUESTION, Config.TYPE_LAST_QUESTION_NAME));

        final Dialog dialog = DialogUtil.showDialogCustomLayout(self, R.layout.layout_dialog_select_display_question, R.color.white, true);
        RadioButton rbQuestionWithText = (RadioButton) dialog.findViewById(R.id.radio_display_with_text);
        RadioButton rbQuestionLast = (RadioButton) dialog.findViewById(R.id.radio_display_last_question);
//        notifyPropertyChanged(BR.backgroundApp);
//        notifyPropertyChanged(BR.backgroundMain);
//        notifyPropertyChanged(BR.textColorPrimary);

        if(Config.TYPE_QUESTION_DISPLAY_WITH_TEXT ==DataStoreManager.getTypeDisplayQuestion().getId()){
            rbQuestionWithText.setChecked(true);
            rbQuestionLast.setChecked(false);
        }else {
            rbQuestionWithText.setChecked(false);
            rbQuestionLast.setChecked(true);
        }

        rbQuestionLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayQuestionType displayQuestionType = new DisplayQuestionType(Config.TYPE_LAST_QUESTION, Config.TYPE_LAST_QUESTION_NAME);
                DataStoreManager.saveTypeDisplayQuestion(displayQuestionType);
                GlobalValue.setDisplayQuestionType(displayQuestionType);
                dialog.dismiss();

            }
        });

        rbQuestionWithText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayQuestionType displayQuestionType = new DisplayQuestionType(Config.TYPE_QUESTION_DISPLAY_WITH_TEXT, Config.TYPE_QUESTION_DISPLAY_WITH_TEXT_NAME);
                DataStoreManager.saveTypeDisplayQuestion(displayQuestionType);
                GlobalValue.setDisplayQuestionType(displayQuestionType);
                dialog.dismiss();
            }
        });

//        final RecyclerView rcvData = (RecyclerView) dialog.findViewById(R.id.rcvData);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(self, LinearLayoutManager.VERTICAL, false);
//        rcvData.setLayoutManager(linearLayoutManager);
//        SelectListTypeDisplayQuestion selectListTypeDisplayQuestion = new SelectListTypeDisplayQuestion(self, mQuestionTypes,
//                new SelectListTypeDisplayQuestion.ItemClickListener() {
//                    @Override
//                    public void onClickItem(int position) {
//                        DataStoreManager.saveTypeDisplayQuestion(mQuestionTypes.get(position));
//                        notifyPropertyChanged(BR.listType);
//                        dialog.dismiss();
//                        AppUtil.showToast(self, mQuestionTypes.get(position).getName());
//
//                    }
//                });
//        rcvData.setAdapter(selectListTypeDisplayQuestion);
    }

    private void reloadActivity() {
        Intent intent = new Intent(self, SplashLoadActivity.class);
        self.startActivity(intent);
        ((Activity)self).finishAffinity();
    }

    public int getImage() {
        return R.drawable.suusoft;
    }

    public String getTitle() {
        return self.getString(R.string.URL_Company);
    }

    @Bindable
    public String getThemeName() {
        return DataStoreManager.getTheme().getName();
    }

    @Bindable
    public String getListType() {
        return DataStoreManager.getListType().getName();
    }

    @Bindable
    public String getListGridColumn() {
        return DataStoreManager.getGridColumn().getName();
    }

    public void onClickChangeListGridColumn(View view) {
        final ArrayList<GridInterface> mGridInterface = new ArrayList<>();
        mGridInterface.add(new GridInterface(Config.ID_GRID_2_COLUMN, Config.NAME_GRID_2_COLUMN));
        mGridInterface.add(new GridInterface(Config.ID_GRID_3_COLUMN, Config.NAME_GRID_3_COLUMN));

        final Dialog dialog = new Dialog(self);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_select_list_type);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        // Get view
        final RecyclerView rcvData = (RecyclerView) dialog.findViewById(R.id.rcvData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(self, LinearLayoutManager.VERTICAL, false);
        rcvData.setLayoutManager(linearLayoutManager);
        SelectGridColumnAdapter selectGridColumnAdapter = new SelectGridColumnAdapter(self, mGridInterface,
                new SelectGridColumnAdapter.ItemClickListener() {
                    @Override
                    public void onClickItem(int position) {
                        DataStoreManager.saveGridColumn(mGridInterface.get(position));
                        GlobalValue.setGridInterface(mGridInterface.get(position));
//                        notifyPropertyChanged(BR.listType);
                        dialog.dismiss();

                    }
                });
        rcvData.setAdapter(selectGridColumnAdapter);
        dialog.show();
    }

    @Bindable
    public String getDislayQuestionType(){

        if (Config.TYPE_LAST_QUESTION == DataStoreManager.getTypeDisplayQuestion().getId()) {
            String str = self.getResources().getString(R.string.take_exam_after_lesson);
            notifyChange();
            return str;
        } else {
            String str =self.getResources().getString(R.string.take_exam_during_lesson);
            notifyChange();
            return str;
        }


    }

    @Bindable
    public String getLanguage() {
        return DataStoreManager.getLanguage().getName();
    }

    @Bindable
    public String getBackgroundApp() {
        return DataStoreManager.getTypeBackgroundApp();
    }

    public int getSize() {
        return texSize;
    }

    public boolean isScreenOn() {
        return isScreenOn;
    }

    public void setScreenOn(boolean values) {
        isScreenOn = values;
        DataStoreManager.setSettingScreenOn(isScreenOn);
    }

    public String getDescription() {
        return self.getString(R.string.about_content1);
    }

    public void onValueChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
        this.texSize = 10 + progresValue;
        notifyChange();
    }

    public void setProgres(int progresValue) {
        this.texSize = 10 + progresValue;
    }

    public int getProgres() {
        return texSize - 10;
    }

}
