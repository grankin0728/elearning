package com.suusoft.elistening.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseFragment;

/**
 * Created by Suusoft on 09/01/2017.
 */

public class FragmentDetailPlayingItem extends BaseFragment {

    private String item;
    private TextView tvItem;

    public static FragmentDetailPlayingItem newInstance(String item) {
        Bundle bundle = new Bundle();
        FragmentDetailPlayingItem fragment = new FragmentDetailPlayingItem();
        fragment.setArguments(bundle);
        fragment.item = item;
        return fragment;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.fragment_detail_playing_item;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initView(View view) {
        tvItem = (TextView)view.findViewById(R.id.tv_item);
    }

    @Override
    protected void getData() {
        tvItem.setText(item);
    }
}
