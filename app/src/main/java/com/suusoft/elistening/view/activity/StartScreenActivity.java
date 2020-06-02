package com.suusoft.elistening.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseActivity;
import com.suusoft.elistening.view.adapter.ViewPagerAdapter;
import com.suusoft.elistening.view.fragment.FragmentIntro;

import me.relex.circleindicator.CircleIndicator;

public class StartScreenActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private CircleIndicator indicator;
    private ViewPagerAdapter adapter;
    private int current = 0;
    private ImageView imgBg;
    private TextView tvSkip;
    private TextView tvNext;

    private RelativeLayout rlMove;
    private RelativeLayout rlStart;



    @Override
    protected ToolbarType getToolbarType() {
        return ToolbarType.NONE;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.star_screen_activity;
    }

    @Override
    protected void getExtraData(Intent intent) {

    }

    @Override
    protected void initilize() {

    }

    @Override
    protected void initView() {
        imgBg = findViewById (R.id.img_background);
        viewPager = findViewById (R.id.view_pager);
        indicator = findViewById (R.id.indicator);
        tvSkip = findViewById (R.id.tv_skip);
        tvNext = findViewById (R.id.tv_next);

        rlMove = findViewById (R.id.rl_move);
        rlStart = findViewById (R.id.rl_start);

        tvSkip.setOnClickListener (this);
        tvNext.setOnClickListener (this);
        rlStart.setOnClickListener (this);
        setViewPager();
    }

    @Override
    protected void onViewCreated() {

    }

    private void setViewPager() {
        adapter = new ViewPagerAdapter (getSupportFragmentManager ());
        adapter.addFragment (FragmentIntro.newInstance (R.drawable.intro_1));
        adapter.addFragment (FragmentIntro.newInstance (R.drawable.intro_2));
        adapter.addFragment (FragmentIntro.newInstance (R.drawable.intro_3));
        viewPager.setAdapter (adapter);

        indicator.setViewPager (viewPager);
        viewPager.addOnPageChangeListener (listener);
        adapter.registerDataSetObserver (indicator.getDataSetObserver ());

    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener () {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            current = position;
            if (position == 2) {
                rlMove.setVisibility (View.GONE);
                rlStart.setVisibility (View.VISIBLE);
            } else {
                rlStart.setVisibility (View.GONE);
                rlMove.setVisibility (View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.tv_skip:
            case R.id.rl_start:
                startActivity (new Intent (this, SplashLoadActivity.class));
                finish ();
                break;
            case R.id.tv_next:
                current++;
                viewPager.setCurrentItem (current, true);
                if (current == 3) {
                    startActivity (new Intent (this, SplashLoadActivity.class));
                    finish ();
                }
                break;
        }
    }
}
