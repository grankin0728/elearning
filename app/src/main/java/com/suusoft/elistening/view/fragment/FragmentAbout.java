package com.suusoft.elistening.view.fragment;

import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseFragmentBinding;
import com.suusoft.elistening.base.vm.BaseViewModel;
import com.suusoft.elistening.databinding.FragmentAboutBinding;
import com.suusoft.elistening.view.activity.MainActivity;
import com.suusoft.elistening.viewmodel.fragment.AboutVM;

/**
 * Created by Ha on 9/8/2016.
 */
public class FragmentAbout extends BaseFragmentBinding {
    private AboutVM viewModel;
    private FragmentAboutBinding binding;

    public static FragmentAbout newInstance() {
        Bundle args = new Bundle();
        FragmentAbout fragment = new FragmentAbout();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected BaseViewModel getViewModel() {
        viewModel = new AboutVM(self);
        return viewModel;
    }

    @Override
    protected void setViewDataBinding(ViewDataBinding binding) {
        this.binding = (FragmentAboutBinding) binding;
        this.binding.setViewModel(viewModel);
    }


    @Override
    protected void initView(View view) {
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) self).setToolbarTitle(R.string.about);
        ((MainActivity) self).showIconToolbar();
    }

}
