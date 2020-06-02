package com.suusoft.elistening.view.fragment;

import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseFragmentBinding;
import com.suusoft.elistening.base.vm.BaseViewModel;
import com.suusoft.elistening.databinding.FragmentFeedbackBinding;
import com.suusoft.elistening.view.activity.MainActivity;
import com.suusoft.elistening.viewmodel.fragment.FeedBackVM;

/**
 * Created by Ha on 9/8/2016.
 */
public class FragmentFeedBack extends BaseFragmentBinding {
    private FeedBackVM viewModel;
    private FragmentFeedbackBinding binding;

    public static FragmentFeedBack newInstance() {
        Bundle args = new Bundle();
        FragmentFeedBack fragment = new FragmentFeedBack();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.fragment_feedback;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected BaseViewModel getViewModel() {
        viewModel = new FeedBackVM(self);
        return viewModel;
    }

    @Override
    protected void setViewDataBinding(ViewDataBinding binding) {
        this.binding = (FragmentFeedbackBinding) binding;
        this.binding.setViewModel(viewModel);

        this.binding.txtContent.setHorizontallyScrolling(false);
        this.binding.txtContent.setMaxLines(4);
        this.binding.txtContent.setMinLines(4);
    }


    @Override
    protected void initView(View view) {
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) self).setToolbarTitle(R.string.feedback);
        ((MainActivity) self).showIconToolbar();
    }

}
