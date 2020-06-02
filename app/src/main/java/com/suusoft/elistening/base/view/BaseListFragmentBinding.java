package com.suusoft.elistening.base.view;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.vm.BaseViewModelList;
import com.suusoft.elistening.databinding.ListDataBinding;
import com.suusoft.elistening.listener.IDataChangedListener;

import java.util.List;

/**
 * Created by Suusoft on 7/11/2016.
 */
public abstract class BaseListFragmentBinding extends BaseFragmentBinding implements IDataChangedListener {

    protected ListDataBinding mBinding;
    private BaseViewModelList mViewModel;
    protected abstract void setUpRecyclerView(RecyclerView recyclerView);

    @Override
    protected int getLayoutInflate() {
        return R.layout.base_list;
    }

    @Override
    protected void setViewDataBinding(ViewDataBinding binding) {
        mViewModel = (BaseViewModelList) viewModel;
        // set listener data changed from viewmodel
        mViewModel.setDataListener(this);
        // get binding
        mBinding = (ListDataBinding) binding;
        // set view model
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void initView(View view) {
        setUpRecyclerView(mBinding.rcvData);
    }

    @Override
    public void onListDataChanged(List<?> data) {
        ((BaseAdapter) mBinding.rcvData.getAdapter()).setItems(data);
    }

}
