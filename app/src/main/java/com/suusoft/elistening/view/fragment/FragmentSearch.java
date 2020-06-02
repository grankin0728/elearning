package com.suusoft.elistening.view.fragment;

import android.app.Activity;
import androidx.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseFragmentBinding;
import com.suusoft.elistening.base.vm.BaseViewModel;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.databinding.FragmentSearchBinding;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.retrofit.APIService;
import com.suusoft.elistening.retrofit.ApiUtils;
import com.suusoft.elistening.retrofit.respone.ResponeLesson;
import com.suusoft.elistening.util.AppUtil;
import com.suusoft.elistening.util.StringUtil;
import com.suusoft.elistening.util.UnsignUtils;
import com.suusoft.elistening.view.activity.MainActivity;
import com.suusoft.elistening.view.adapter.ListLessonAdapter;
import com.suusoft.elistening.viewmodel.fragment.SearchVM;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ha on 9/8/2016.
 */
public class FragmentSearch extends BaseFragmentBinding {

    private ListLessonAdapter mAdapter;
    private SearchVM viewModel;
    private FragmentSearchBinding binding;
    private String key;
    private int mMenuLeftHome;

    private List<Lesson> dataList;

    public static FragmentSearch newInstance(int menuLeftHome) {
        Bundle args = new Bundle();
        FragmentSearch fragment = new FragmentSearch();
        fragment.setArguments(args);
        fragment.mMenuLeftHome = menuLeftHome;
        return fragment;
    }

    public ListLessonAdapter getmAdapter() {
        return mAdapter;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected BaseViewModel getViewModel() {
        viewModel = new SearchVM(self);
        return viewModel;
    }

    @Override
    protected void setViewDataBinding(ViewDataBinding binding) {
        this.binding = (FragmentSearchBinding) binding;
        this.binding.setViewModel(viewModel);
        dataList = new ArrayList<>();
    }


    @Override
    protected void initView(View view) {
        binding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    binding.imvSearch.performClick();
                    return true;
                }
                return false;
            }
        });
        binding.imvSearch.setColorFilter(Color.BLACK);
        binding.imvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.hideSoftKeyboard((Activity) self);
                key = binding.edtSearch.getText().toString();
                dataList.clear();
                if (StringUtil.isEmpty(key)) {
                    ((MainActivity) self).showSnackBar(R.string.message_search_empty);
                    setDataRecycleView(dataList);
                    return;
                }
                ((MainActivity) self).showProgress(true);

                processingGetAndShowList();
                APIService apiService = ApiUtils.getAPIService();
                apiService.search(DataStoreManager.getToken(), key).enqueue(new Callback<ResponeLesson>() {
                    @Override
                    public void onResponse(Call<ResponeLesson> call, Response<ResponeLesson> response) {
                        dataList.addAll(response.body().getData());
                        setDataRecycleView(dataList);
                        if (dataList.isEmpty()) {
                            ((MainActivity) self).showSnackBar(R.string.no_data);
                        } else {
                            ((MainActivity) self).showSnackBar(R.string.successfully);
                        }
                        ((MainActivity) self).showProgress(false);
                    }

                    @Override
                    public void onFailure(Call<ResponeLesson> call, Throwable t) {
                        ((MainActivity) self).showSnackBar(R.string.error);
                        setDataRecycleView(dataList);
                        ((MainActivity) self).showProgress(false);
                    }
                });

//                RequestManager.getBookList(self, Constant.TYPE_ALL, "", key, String.valueOf(1),
//                        new ApiManager.CompleteListener() {
//                    @Override
//                    public void onSuccess(ApiResponse response) {
//                        dataList = response.getDataList(Book.class);
//                        setDataRecycleView(dataList);
//                        if (dataList.isEmpty()) {
//                            ((MainActivity) self).showSnackBar(R.string.no_data);
//                        } else {
//                            ((MainActivity) self).showSnackBar(R.string.successfully);
//                        }
//                        ((MainActivity) self).showProgress(false);
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        ((MainActivity) self).showSnackBar(R.string.error);
//                        setDataRecycleView(dataList);
//                        ((MainActivity) self).showProgress(false);
//                    }
//                });
            }
        });
    }

    private void processingGetAndShowList() {
        ArrayList<Lesson> lessons = null;
        if (mMenuLeftHome== Constant.MENU_LEFT_HOME) {
            lessons = DataStoreManager.getLessonAsLevel(Constant.LEVEL_LESSON_BEGINING);
        }else if (mMenuLeftHome== Constant.MENU_LEFT_DOWNLOAD){
            lessons = DataStoreManager.getLessonAsFavoriteOrDownload(Constant.DOWNLOAD);
        }else if (mMenuLeftHome== Constant.MENU_LEFT_FAVOURITE){
            lessons = DataStoreManager.getLessonAsFavoriteOrDownload(Constant.FAVORITE);
        }

        Lesson lesson;

        if (lessons.size()> 0 ){
            //add list
            for (int i = 0; i < lessons.size(); i++){
                lesson = lessons.get(i);

                if (UnsignUtils.getUnsign(lesson.getName().toLowerCase(Locale.getDefault()))
                        .contains(UnsignUtils.getUnsign(key))) {
                    dataList.add(lesson);
                }
            }

            //show list search
            setDataRecycleView(dataList);
            if (dataList.size()>0){
                ((MainActivity) self).showSnackBar(R.string.successfully);
            }else
                ((MainActivity) self).showSnackBar(R.string.no_data);
        }else
            ((MainActivity) self).showSnackBar(R.string.no_data);
        ((MainActivity) self).showProgress(false);
    }


    void setDataRecycleView(List<Lesson> dataList) {
        if (dataList==null) {
            dataList = new ArrayList<>();
        }
        if (Config.ID_TYPE_GRID == GlobalValue.getListType().getId()) {
            binding.rcvData.setLayoutManager(new GridLayoutManager(self, GlobalValue.getGridColumn().getId()));
        } else {
            binding.rcvData.setLayoutManager(new LinearLayoutManager(self));
        }
        mAdapter = new ListLessonAdapter(FragmentSearch.this, dataList, GlobalValue.getListType().getId());
        binding.rcvData.setAdapter(mAdapter);
    }

    public void setUpListOrGrid(int type) {
        if (type ==  Config.ID_TYPE_GRID) {
            binding.rcvData.setLayoutManager(new GridLayoutManager(self, GlobalValue.getGridColumn().getId()));
        } else {
            binding.rcvData.setLayoutManager(new LinearLayoutManager(self));
        }
        mAdapter.setType(type);
        binding.rcvData.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) self).setToolbarTitle(R.string.search);
        ((MainActivity) self).showIconToolbar(R.id.action_list_or_grid);
    }
}
