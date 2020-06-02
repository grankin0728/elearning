package com.suusoft.elistening.view.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseFragment;

public class FragmentIntro extends BaseFragment {

    private int image;
    private ImageView img;

    public static FragmentIntro newInstance(int image){
        FragmentIntro fragment = new FragmentIntro();
        fragment.image = image;
        return fragment;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.fragment_intro;
    }

    @Override
    protected void init() {

    }


    @Override
    protected void initView(View view) {
        img = view.findViewById(R.id.img);
        img.setImageResource (image);
    }

    @Override
    protected void getData() {

    }
}
