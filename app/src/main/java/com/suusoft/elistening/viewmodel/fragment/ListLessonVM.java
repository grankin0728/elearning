package com.suusoft.elistening.viewmodel.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseListFragmentBinding;
import com.suusoft.elistening.base.vm.BaseViewModelList;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.modelmanager.RequestManager;
import com.suusoft.elistening.network.ApiResponse;
import com.suusoft.elistening.network.BaseRequest;
import com.suusoft.elistening.view.activity.MainActivity;

import java.util.List;

/**
 * Created by Suusoft on 09/03/2020.
 */

public class ListLessonVM extends BaseViewModelList {

    private Bundle bundle;
    private String categoryId;

    public ListLessonVM(BaseListFragmentBinding context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(self, Config.COLUMN_3);
    }

    @Override
    public void getData(int page) {

        //hàm này để set icon toolbar khi vào từng fragment
        int menu = bundle.getInt(Constant.OPTIONS_FRAGMENT_LIST_LESSON);
        Log.e("eee", page + " Page");
        switch (menu) {
            case Constant.MENU_LEFT_HOME:
                ((MainActivity) self).showIconToolbar(R.id.action_search2, R.id.action_list_or_grid);
                initData();
                //addListData(DataStoreManager.getLessonAsCategory(categoryId));
                Log.e("ListLessonVM", " MENU_LEFT_HOME");
                break;

            case Constant.MENU_LEFT_FAVOURITE:
                ((MainActivity) self).showIconToolbar(R.id.action_search2, R.id.action_list_or_grid);
                addListData(DataStoreManager.getLessonAsFavoriteOrDownload(Constant.FAVORITE));
                Log.e("ListLessonVM", " MENU_LEFT_FAVOURITE");
                break;

            case Constant.MENU_LEFT_DOWNLOAD:
                ((MainActivity) self).showIconToolbar(R.id.action_search2, R.id.action_list_or_grid);
                addListData(DataStoreManager.getLessonAsFavoriteOrDownload(Constant.DOWNLOAD));
                Log.e("ListLessonVM", " MENU_LEFT_DOWNLOAD");
                break;


        }
    }

    private void initData() {
        RequestManager.getListLessonByCategory(self, DataStoreManager.getToken(), categoryId, "1", new BaseRequest.CompleteListener() {
            @Override
            public void onSuccess(ApiResponse response) {
                List<Lesson> lessons = response.getDataList(Lesson.class);

                addListData(lessons);
                checkLoadingMoreComplete(Integer.parseInt(response.getValueFromRoot("total_page")));
                // ListLessonVM.this.notifyDataChanged();
                Log.e("ListLessonVM", "categoryId" + categoryId);

            }

            @Override
            public void onError(String message) {
                Log.e("getListLesson", "onError " + message);
            }
        });
    }

    @Override
    public List<?> getListData() {
        return super.getListData();
    }

}
