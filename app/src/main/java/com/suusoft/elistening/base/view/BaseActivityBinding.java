package com.suusoft.elistening.base.view;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.suusoft.elistening.base.vm.BaseViewModel;

/**
 * Created by Suusoft on 7/6/2016.
 */
public abstract class BaseActivityBinding extends AbstractActivity {

    protected BaseViewModel viewModel;
    protected ViewDataBinding binding;

    protected abstract ToolbarType getToolbarType();

    protected abstract int getLayoutInflate();

    protected abstract void getExtraData(Intent intent);

    protected abstract void initialize();

    protected abstract BaseViewModel getViewModel();

    protected abstract void setViewDataBinding(ViewDataBinding binding);

    protected abstract void initView(View view);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        viewModel = getViewModel();
        binding = getViewDataBinding();
        setViewDataBinding(binding);
        initView(binding.getRoot());
    }

    /**
     * get ViewDatabinding is referenced to #getLayoutInflate()
     */
    public final ViewDataBinding getViewDataBinding() {
        return DataBindingUtil.inflate(getLayoutInflater(), getLayoutInflate(), contentLayout, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.destroy();

    }

}
