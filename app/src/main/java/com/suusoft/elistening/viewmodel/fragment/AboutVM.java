package com.suusoft.elistening.viewmodel.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.vm.BaseViewModel;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.datastore.DataStoreManager;

/**
 * Created by Ha on 9/12/2016.
 */
public class AboutVM extends BaseViewModel {

    public String getBackgroundMain() {
        if (Config.TYPE_BACKGROUND_DARK.equals(DataStoreManager.getTypeBackgroundApp())) {
            return DataStoreManager.getTheme().getBackgroundMainDark();
        } else {
            return DataStoreManager.getTheme().getBackgroundMainLight();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void getData(int page) {

    }

    public AboutVM(Context self) {
        super(self);
    }

    public String getImage() {
        return String.valueOf(R.drawable.suusoft);
    }

    public String getTitle() {
        return self.getString(R.string.URL_Company);
    }

    public String getDescription() {
        return self.getString(R.string.about_content1) + "\n" + self.getString(R.string.about_content2);
    }

    public String getTextColorSecondary() {
        if (Config.TYPE_BACKGROUND_DARK.equals(DataStoreManager.getTypeBackgroundApp())) {
            return DataStoreManager.getTheme().getTextColorSecondaryDark();
        } else {
            return DataStoreManager.getTheme().getTextColorSecondaryLight();
        }
    }

    public String getTextColorPrimary() {
        if (Config.TYPE_BACKGROUND_DARK.equals(DataStoreManager.getTypeBackgroundApp())) {
            return DataStoreManager.getTheme().getTextColorPrimaryDark();
        } else {
            return DataStoreManager.getTheme().getTextColorPrimaryLight();
        }
    }

    public void onClickFace(View view) {
        Intent link = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/suusoft"));
        self.startActivity(link);
    }

    public void onClickGoogle(View view) {
        Intent link = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com"));
        self.startActivity(link);
    }
}
