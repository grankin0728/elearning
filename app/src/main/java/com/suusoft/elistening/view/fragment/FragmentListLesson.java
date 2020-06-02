package com.suusoft.elistening.view.fragment;

import android.content.Intent;

import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.suusoft.elistening.base.view.BaseListFragmentBinding;
import com.suusoft.elistening.base.vm.BaseViewModel;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.view.activity.DetailActivity;
import com.suusoft.elistening.view.activity.QuestionActivity;
import com.suusoft.elistening.view.adapter.ListLessonAdapter;
import com.suusoft.elistening.viewmodel.fragment.ListLessonVM;

/**
 * Created by Suusoft on 09/11/2017.
 */

public class FragmentListLesson extends BaseListFragmentBinding {
    final static String TAG = FragmentListLesson.class.getSimpleName();

    private ListLessonAdapter mAdapter;
    private ListLessonVM viewModel;
    private RecyclerView mRecyclerView;
    private static boolean mLoadMore;
    private String mIdCategory;

    public static FragmentListLesson newInstance(String idCategory, boolean loadMore) {
        mLoadMore = loadMore;
        Bundle args = new Bundle();
        FragmentListLesson fragment = new FragmentListLesson();
        fragment.setArguments(args);
        fragment.mIdCategory = idCategory;
        return fragment;
    }

    public static FragmentListLesson newInstance( boolean loadMore) {
        mLoadMore = loadMore;
        Bundle args = new Bundle();
        FragmentListLesson fragment = new FragmentListLesson();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void setUpRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        mRecyclerView.setVisibility(View.VISIBLE);

        mAdapter = new ListLessonAdapter(FragmentListLesson.this, viewModel.getListData(), GlobalValue.getListType().getId());
        if (Config.ID_TYPE_GRID == GlobalValue.getListType().getId()) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(self, Config.COLUMN_3));
            mRecyclerView.hasFixedSize();
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(self));
        }
        mRecyclerView.setAdapter(mAdapter);


    }

    public void setUpListOrGrid(int type) {
        if (mRecyclerView !=null){
            if (type ==  Config.ID_TYPE_GRID) {
                mRecyclerView.setLayoutManager(new GridLayoutManager(self, GlobalValue.getGridColumn().getId()));
            } else {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(self));
            }
            mAdapter.setType(type);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected BaseViewModel getViewModel() {
        viewModel = new ListLessonVM(this, getArguments());
        viewModel.setCategoryId(mIdCategory);
        return viewModel;
    }

    @Override
    public void onResume() {
        super.onResume();
        String titleToolbar = getArguments().getString(Constant.KEY_TITLE_TOOLBAR, "");
//        if(!titleToolbar.equals(self.getString(R.string.home)))
//        ((MainActivity) self).setToolbarTitle(titleToolbar);
        setUpListOrGrid(GlobalValue.getListType().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //((MainActivity) self).initTootlbarNavi(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == QuestionActivity.REQUEST_CODE){
            Log.e("FragmentListLesson", "onActivityResult REQUEST_CODE");
            mAdapter.notifyDataSetChanged();

            if (resultCode == DetailActivity.RESULT_WATCH){
                Log.e("FragmentListLesson", "onActivityResult RESULT_WATCH");

            }
        }
    }

    @Override
    protected void setViewDataBinding(ViewDataBinding binding) {
        super.setViewDataBinding(binding);
    }

}
