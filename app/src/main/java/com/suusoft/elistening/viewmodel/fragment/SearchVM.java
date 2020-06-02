package com.suusoft.elistening.viewmodel.fragment;

import android.content.Context;

import com.suusoft.elistening.base.vm.BaseViewModel;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.datastore.DataStoreManager;

/**
 * Created by Ha on 9/12/2016.
 */
public class SearchVM extends BaseViewModel {

    public String getBackgroundMain() {
        if (Config.TYPE_BACKGROUND_DARK.equals(DataStoreManager.getTypeBackgroundApp())) {
            return DataStoreManager.getTheme().getBackgroundMainDark();
        } else {
            return DataStoreManager.getTheme().getBackgroundMainLight();
        }
    }

    public SearchVM(Context self) {
        super(self);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void getData(int page) {

    }
}
