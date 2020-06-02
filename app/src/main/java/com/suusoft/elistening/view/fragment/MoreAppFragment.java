package com.suusoft.elistening.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseFragment;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.model.OurApp;
import com.suusoft.elistening.util.AppUtil;
import com.suusoft.elistening.view.activity.MainActivity;
import com.suusoft.elistening.view.adapter.MoreAppAdapter;

import java.util.ArrayList;
import java.util.List;

public class MoreAppFragment extends BaseFragment {

    private RecyclerView rcvData;
    private MoreAppAdapter mAdapter;
    private List<OurApp> listData;
    private TextView tvViewMore;

    public static MoreAppFragment newInstance() {
        Bundle args = new Bundle();
        MoreAppFragment fragment = new MoreAppFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.fragment_more_apps;
    }

    @Override
    protected void init() {
        listData = new ArrayList<>();
    }

    @Override
    protected void initView(View view) {
        tvViewMore = (TextView) view.findViewById(R.id.tvViewMore);
        tvViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.goToResultByKeySearchOnGooglePlay(getActivity(), Config.KEY_SEARCH_MORE_APP_GOOGLE_PLAY);
            }
        });
        RelativeLayout layoutAll = (RelativeLayout) view.findViewById(R.id.layoutAll);
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())) {
            layoutAll.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getBackgroundMainDark()));
            tvViewMore.setTextColor(Color.parseColor(GlobalValue.getTheme().getTextColorPrimaryDark()));
        } else {
            layoutAll.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getBackgroundMainLight()));
            tvViewMore.setTextColor(Color.parseColor(GlobalValue.getTheme().getTextColorPrimaryLight()));
        }
        rcvData = (RecyclerView) view.findViewById(R.id.rcv_data);
        rcvData.setLayoutManager(new LinearLayoutManager(self));
    }

    @Override
    protected void getData() {
        if (listData == null) listData = new ArrayList<>();
        else listData.clear();
        listData = Config.getListOurApp(self);
        Log.e("Our app size", listData.size() + "");
        mAdapter = new MoreAppAdapter(self, listData);
        rcvData.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) self).setToolbarTitle(R.string.more_apps);
        ((MainActivity) self).showIconToolbar();
    }
}
