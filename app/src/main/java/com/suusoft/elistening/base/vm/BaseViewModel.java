package com.suusoft.elistening.base.vm;

import android.content.Context;

import androidx.databinding.BaseObservable;

import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.datastore.DataStoreManager;

/**
 * Created by Suusoft on 7/3/2016.
 */
public abstract class BaseViewModel extends BaseObservable {

    public Context self;

    public abstract void destroy();
    public abstract void getData(int page);

    public BaseViewModel(Context self) {
        this.self = self;
    }

    public String getBackgroundMain() {
        if (Config.TYPE_BACKGROUND_DARK.equals(DataStoreManager.getTypeBackgroundApp())) {
            return DataStoreManager.getTheme().getBackgroundMainDark();
        } else {
            return DataStoreManager.getTheme().getBackgroundMainLight();
        }
    }
}
