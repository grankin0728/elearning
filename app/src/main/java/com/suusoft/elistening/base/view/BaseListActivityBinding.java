package com.suusoft.elistening.base.view;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.vm.BaseViewModelList;
import com.suusoft.elistening.databinding.ListDataBinding;
import com.suusoft.elistening.listener.IDataChangedListener;

import java.util.List;

/**
 * Created by Suusoft on 7/15/2016.
 */
public abstract class BaseListActivityBinding extends BaseActivityBinding implements IDataChangedListener {

    private ListDataBinding mBinding;
    private BaseViewModelList mViewModel;

    protected abstract void setUpRecyclerView(RecyclerView recyclerView);

    @Override
    protected int getLayoutInflate() {
        return R.layout.base_list;
    }

    @Override
    protected void setViewDataBinding(ViewDataBinding binding) {
        mBinding= (ListDataBinding) binding;
        mViewModel = (BaseViewModelList) viewModel;

        // set listener data changed from viewmodel
        mViewModel.setDataListener(this);

        // set view model
        mBinding.setViewModel(mViewModel);

        // set up recyclerView
        setUpRecyclerView(mBinding.rcvData);

        // get data
        mViewModel.getData(1);
    }

    /**
     * listener #BaseViewmodel.notifyDataChanged()
     *  and refresh adapter
     *
     */
    @Override
    public void onListDataChanged(List<?> data) {
        BaseAdapter adapter = (BaseAdapter) mBinding.rcvData.getAdapter();
        adapter.setItems(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.destroy();
    }
}
