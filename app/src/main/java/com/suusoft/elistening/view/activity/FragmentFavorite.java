package com.suusoft.elistening.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.suusoft.elistening.AppController;
import com.suusoft.elistening.DaoFavorite.FavoriteAdapter;
import com.suusoft.elistening.DaoFavorite.FavoriteDatabase;
import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseFragment;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.listener.IOnclickItem;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.service.MusicService;
import com.suusoft.elistening.util.AppUtil;
import com.suusoft.elistening.view.fragment.FragmentDetailQuestion;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavorite extends BaseFragment {


    final static String TAG = FragmentFavorite.class.getSimpleName();

    private RecyclerView rcv_fvorite;
    private FavoriteAdapter adapter;
    private static boolean mLoadMore;
    private Lesson lesson;
    private TextView tvNodata;

    public static FragmentFavorite newInstance(boolean loadmore){
        mLoadMore = loadmore;
        Bundle args = new Bundle();
        FragmentFavorite fragment = new FragmentFavorite();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutInflate() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initView(View view) {
        rcv_fvorite = view.findViewById(R.id.rv_favorite);
        tvNodata    = view.findViewById(R.id.tv_nodata);
        rcv_fvorite.hasFixedSize();
        rcv_fvorite.setLayoutManager(new GridLayoutManager(self, 2));

    }



    @Override
    protected void getData() {


        /**
         * get favorite list in Room data base
         * */

        final List<Lesson> favoriteLists = FavoriteDatabase.getInstance(self).favoriteDao().getFavoriteData();
        if (favoriteLists == null) {
            tvNodata.setText("No Favorite");
            tvNodata.setVisibility(View.VISIBLE);
        }else {
            tvNodata.setVisibility(View.GONE);
            adapter=new FavoriteAdapter(favoriteLists, self);
            adapter.setOnclickItem(new IOnclickItem() {
                @Override
                public void onItemClick(int position) {
                    lesson = favoriteLists.get(position);;
                    if (favoriteLists.get(position).getType().equals("video")) {
                        Intent gotoVideo = new Intent(self, VideoViewActivity.class);
                        AppController.getInstance().setArrLessons(favoriteLists);
                        AppController.getInstance().setLessonIndex(favoriteLists.indexOf(lesson));
                        startActivity(gotoVideo);
                        ((Activity) self).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    }else if (favoriteLists.get(position).getType().equals("audio")) {
                        Intent intent = new Intent(self, DetailActivity.class);
                        AppController.getInstance().setArrLessons(favoriteLists);
                        AppController.getInstance().setLessonIndex(favoriteLists.indexOf(lesson));
                        startActivityForResult(intent, FragmentDetailQuestion.REQUEST_CODE);
                        startMyService();
                        ((Activity) self).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    }else if (favoriteLists.get(position).getType().equals("quizz")) {
                        Intent gotoQuestion = new Intent(self , QuestionActivity.class);
                        AppController.getInstance().setArrLessons(favoriteLists);
                        AppController.getInstance().setLessonIndex(favoriteLists.indexOf(lesson));
                        startActivityForResult(gotoQuestion, QuestionActivity.REQUEST_CODE);
                        //startMyService();
                        ((Activity) self).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    }

                }
            });
            rcv_fvorite.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }




    }

    private void startMyService() {
        MusicService.intentStart(self, Constant.ACTION_OPEN);
    }

    @Override
    public void onResume() {
        super.onResume();
        String titleToolbar = getArguments().getString(Constant.KEY_TITLE_TOOLBAR, "");
    }
}
