package com.suusoft.elistening.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.View;

import com.suusoft.elistening.AppController;
import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseFragment;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.model.modelLesson.Category;
import com.suusoft.elistening.modelmanager.RequestManager;
import com.suusoft.elistening.network.ApiResponse;
import com.suusoft.elistening.network.BaseRequest;
import com.suusoft.elistening.view.activity.MainActivity;

import java.util.ArrayList;


/**
 * Created by Trang on 6/29/2016.
 *
 */
public class HomeFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabViewPaggerAdapter tabViewPaggerAdapter;

    private ArrayList<Category> mCategories;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {

    }

    @Override
    public void onResume() {
        super.onResume();
//        String titleToolbar = getArguments().getString(Constant.KEY_TITLE_TOOLBAR, "");
        ((MainActivity) self).setToolbarTitle("Home");
    }

    @Override
    protected void initView(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pagger);
        mCategories = new ArrayList<>();

        tabViewPaggerAdapter = new  TabViewPaggerAdapter(getChildFragmentManager(), mCategories);
        viewPager.setAdapter(tabViewPaggerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
        //viewPager.setOffscreenPageLimit(mCategories.size());

        setColorTabPagerByTheme();
    }

    private void setColorTabPagerByTheme() {
        tabLayout.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getColorPrimary()));

        //if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())){
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor(GlobalValue.getTheme().getColorTabSelected_dark()));
            tabLayout.setTabTextColors(Color.parseColor(GlobalValue.getTheme().getColorTabNormal_dark()) ,
                    Color.parseColor(GlobalValue.getTheme().getColorTabSelected_dark()));

//        }else {
//            tabLayout.setSelectedTabIndicatorColor(Color.parseColor(GlobalValue.getTheme().getColorTabSelected_light()));
//            tabLayout.setTabTextColors(Color.parseColor(GlobalValue.getTheme().getColorTabNormal_light()) ,
//                    Color.parseColor(GlobalValue.getTheme().getColorTabSelected_light()));
//        }

    }

    @Override
    protected void getData() {
        getListCategory();
    }

    private void getListCategory() {
        RequestManager.getListCategory(self, DataStoreManager.getToken(), new BaseRequest.CompleteListener() {
            @Override
            public void onSuccess(ApiResponse response) {
                ArrayList<Category> categories = (ArrayList<Category>) response.getDataList(Category.class);
                if (categories!=null){
                    AppController.setCategories(categories);
                    mCategories.clear();
                    mCategories.addAll(categories);
                    tabViewPaggerAdapter.notifyDataSetChanged();
                    Log.e("ListLessonVM", "getListLesson addListData");
                }
            }

            @Override
            public void onError(String message) {
                Log.e("getListLesson", "onError " + message);
            }
        });
    }

    private class TabViewPaggerAdapter extends FragmentPagerAdapter {


        private ArrayList<Category> mCategories;

        public TabViewPaggerAdapter(FragmentManager fm, ArrayList<Category> categories) {
            super(fm);
            mCategories = categories;
        }


        @Override
        public Fragment getItem(int position) {
            Fragment fragment =  FragmentListLesson.newInstance(mCategories.get(position).getId(),true);
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.OPTIONS_FRAGMENT_LIST_LESSON, Constant.MENU_LEFT_HOME);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCategories.get(position).getName();
        }
    }

}
