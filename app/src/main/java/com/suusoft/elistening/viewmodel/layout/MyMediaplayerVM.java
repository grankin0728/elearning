package com.suusoft.elistening.viewmodel.layout;

import android.content.Context;
import android.content.Intent;

import com.suusoft.elistening.base.vm.BaseViewModel;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.service.MusicService;

/**
 * Created by Suusoft on 09/15/2017.
 */

public class MyMediaplayerVM extends BaseViewModel {

    private Intent i;
    private Context mContext;

    private boolean isReplayAll = false;

    public MyMediaplayerVM(Context self) {
        super(self);
        mContext = self;
    }

    public void setReplayAll(boolean isReplayAll){
        this.isReplayAll = isReplayAll;
        notifyChange();
    }

    public boolean getIsReplayAll(){
        return this.isReplayAll;
    }


    @Override
    public void destroy() {

    }

    @Override
    public void getData(int page) {

    }

    public boolean getIsBackgroundDark(){
        if (Config.TYPE_BACKGROUND_DARK.equals(DataStoreManager.getTypeBackgroundApp())) {
            return true;
        } else {
            return false;
        }
    }


    /// các hàm onclick

    public void onClickPlay(){
        i = new Intent(mContext, MusicService.class);
        i.setAction(Constant.ACTION_PLAY);
        mContext.startService(i);
    }


    public void onClickNext(){
        i = new Intent(mContext, MusicService.class);
        i.setAction(Constant.ACTION_NEXT);
        mContext.startService(i);
    }

    public void onClickStop(){
        i = new Intent(mContext, MusicService.class);
        i.setAction(Constant.ACTION_STOP);
        mContext.startService(i);
    }

    public void onClickPrev(){
        i = new Intent(mContext, MusicService.class);
        i.setAction(Constant.ACTION_PREV);
        mContext.startService(i);
    }

    public void onClickReplay(){
        i = new Intent(mContext, MusicService.class);
        i.setAction(Constant.ACTION_REPLAY);
        mContext.startService(i);
    }
}
