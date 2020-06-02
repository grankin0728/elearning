package com.suusoft.elistening.base.view;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suusoft.elistening.base.vm.BaseViewModel;

/**
 * Created by view.findViewById on 6/29/2016.
 */
public abstract class BaseFragmentBinding extends Fragment {

    protected Context self;
    protected ViewDataBinding binding;
    protected BaseViewModel viewModel;
    protected View view;

    protected abstract int getLayoutInflate();

    protected abstract void initialize();

    protected abstract BaseViewModel getViewModel();

    protected abstract void setViewDataBinding(ViewDataBinding binding);

    protected abstract void initView(View view);


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        self = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        self = getActivity();
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       /* View root = inflater.inflate(R.layout.base_content,container, false);
        ViewGroup content = (ViewGroup) root.findViewById(R.id.content_main);*/
        binding = DataBindingUtil.inflate(inflater, getLayoutInflate(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        this.view = view;
        viewModel = getViewModel();
        setViewDataBinding(binding);
        initView(view);
        viewModel.getData(1);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }
}
