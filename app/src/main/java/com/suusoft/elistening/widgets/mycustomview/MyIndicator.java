package com.suusoft.elistening.widgets.mycustomview;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.suusoft.elistening.R;

/**
 * Created by ASUS on 3/25/2017.
 */

public class MyIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private ImageView img1, img2, img3, img4;
    private Context mContext;

    public MyIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        intView();
    }

    private void intView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.indicator, this);
            img1 = (ImageView) findViewById(R.id.img1);
            img2 = (ImageView) findViewById(R.id.img2);
            img3 = (ImageView) findViewById(R.id.img3);
            img4 = (ImageView) findViewById(R.id.img4);
            img1.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle_fill));
        }
    }


    public void setViewPager(ViewPager pager) {
        this.viewPager = pager;
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0: {
                img1.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle_fill));
                img2.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                img3.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                img4.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                break;
            }
            case 1: {
                img2.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle_fill));
                img1.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                img3.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                img4.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                break;
            }
            case 2: {
                img3.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle_fill));
                img2.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                img1.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                img4.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                break;
            }

            case 3: {
                img4.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle_fill));
                img2.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                img1.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                img3.setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                break;
            }

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
