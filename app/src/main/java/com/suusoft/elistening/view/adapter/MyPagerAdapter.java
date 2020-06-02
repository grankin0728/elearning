package com.suusoft.elistening.view.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.suusoft.elistening.R;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.listener.IListenerLoadAudio;
import com.suusoft.elistening.view.fragment.FragmentDetailPlayingSortByTime;
import com.suusoft.elistening.view.fragment.FragmentDetailQuestion;

/**
 * Created by ASUS on 3/22/2017.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private IListenerLoadAudio iListenerLoadAudio;
    private Context mContext;

    public MyPagerAdapter(Context context, FragmentManager fm, IListenerLoadAudio iListenerLoadAudio  ) {
        super(fm);
        mContext = context;
        this.iListenerLoadAudio = iListenerLoadAudio;
    }

    public void setFragmentPage(int pos){
        getItem(pos);
    }


    @Override
    public Fragment getItem(int position) {
            switch (position){
                case Constant.TAB_SELECTED_PLAYING: return  FragmentDetailPlayingSortByTime.newInstance(iListenerLoadAudio);
//                case Constant.TAB_SELECTED_VOCA: return  FragmentDetailVocabulary.newInstance();
//                case Constant.TAB_SELECTED_QUESTION: return  FragmentDetailQuestion.newInstance();
            }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case Constant.TAB_SELECTED_PLAYING:     return "";
//            case Constant.TAB_SELECTED_VOCA:        return "";
//            case Constant.TAB_SELECTED_QUESTION:    return mContext.getString(R.string.title_question);
        }
        return super.getPageTitle(position);
    }
}
