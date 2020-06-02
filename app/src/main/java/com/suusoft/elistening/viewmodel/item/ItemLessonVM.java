package com.suusoft.elistening.viewmodel.item;

import android.app.Activity;
import android.content.Intent;
import androidx.databinding.BindingAdapter;

import android.os.Bundle;
import android.os.Environment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.suusoft.elistening.AppController;
import com.suusoft.elistening.R;
import com.suusoft.elistening.base.vm.BaseViewModelAdapter;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.listener.IOnItemClickedListener;
import com.suusoft.elistening.listener.ISetDataListener;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.model.modelLesson.WatchLesson;
import com.suusoft.elistening.service.DownloadService;
import com.suusoft.elistening.service.MusicService;
import com.suusoft.elistening.util.AppUtil;
import com.suusoft.elistening.view.activity.DetailActivity;
import com.suusoft.elistening.view.activity.QuestionActivity;
import com.suusoft.elistening.view.activity.VideoViewActivity;
import com.suusoft.elistening.view.fragment.FragmentDetailQuestion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suusoft on 09/11/2017.
 */

public class ItemLessonVM extends BaseViewModelAdapter implements IOnItemClickedListener,
        ISetDataListener {

    private List<Lesson> lessons;
    private Activity activity;
    private Fragment fragment;

    public String getBackgroundRadius() {
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())) {
            return DataStoreManager.getTheme().getColorPrimaryDark();
        } else {
            return "#ffffff";
        }
    }

    public int getBackgroundBoder() {
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())) {
            return R.drawable.bg_boder_dark_transparent;
        } else {
            return R.drawable.bg_boder_light_transparent;
        }
    }

    public boolean getIsBackgroundDark() {
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())) {
            return true;
        } else {
            return false;
        }
    }


    private Lesson lesson;
    private WatchLesson watchLesson;

    public ItemLessonVM(Fragment fragment, Lesson lesson, ArrayList<Lesson> lessons) {
        super(fragment.getActivity());
        this.activity = fragment.getActivity();
        this.fragment = fragment;
        this.lesson = lesson;
        this.lessons = lessons;
        this.watchLesson = new WatchLesson(lesson.getId(), false, false,false );

    }

    public String getUrlImage() {
        return lesson.getImage();
    }

    @Override
    public void setData(Object object) {
        this.lesson = (Lesson) object;
        notifyChange();
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

//        if (DataStoreManager.getListType().getId() ==  Config.ID_TYPE_GRID) {
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .apply(options)
//                    .resize(AppController.layoutParamsGrid.width / 3, AppController.layoutParamsGrid.height/ 3)
                    .into(view);
//        }else {
//            Picasso.with(view.getContext())
//                    .load(imageUrl)
//                    .placeholder(R.drawable.placeholder)
//                    .resize(AppController.layoutParamsList.width / 3, AppController.layoutParamsList.height/ 3)
//                    .into(view);
//        }

    }

    public boolean isSelected() {
        return true;
    }

    public String getTitle() {
        return lesson.getName();
    }

    public String getImage() {
        return lesson.getImage();
    }

    public String getDetail() {
        return lesson.getOverview();
    }

    public boolean getIsDownLoad() {
//        return  DataStoreManager.isDownload(lesson);
        return  DataStoreManager.getWatchLesson(lesson.getId()).isDownload();
    }

    public boolean getIsFavorite() {
        return DataStoreManager.getWatchLesson(lesson.getId()).isFavorite();
//        return DataStoreManager.isFavorite(lesson);
    }

    public int getWatched() {
//        if ( DataStoreManager.isView(lesson))
        if ( DataStoreManager.getWatchLesson(lesson.getId()).isView())
            return View.VISIBLE;
        else
            return View.GONE;
    }

    public String getTextColorSecondary() {
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())) {
            return GlobalValue.getTheme().getTextColorSecondaryDark();
        } else {
            return GlobalValue.getTheme().getTextColorSecondaryLight();
        }
    }

    public String getTextColorPrimary() {
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())) {
            return GlobalValue.getTheme().getTextColorPrimaryDark();
        } else {
            return GlobalValue.getTheme().getTextColorPrimaryLight();
        }
    }


    @Override
    public void onItemClicked(View view) {
        Log.e("ItemLessonVM", "onItemClicked: On Clicker" );
        if (lesson.getType().equals("video")) {
            Intent gotoVideo = new Intent(activity, VideoViewActivity.class);
            AppController.getInstance().setArrLessons(lessons);
            AppController.getInstance().setLessonIndex(lessons.indexOf(lesson));
            fragment.startActivity(gotoVideo);
            ((Activity) self).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }else if (lesson.getType().equals("audio")) {
            Intent intent = new Intent(activity, DetailActivity.class);
            AppController.getInstance().setArrLessons(lessons);
            AppController.getInstance().setLessonIndex(lessons.indexOf(lesson));
            fragment.startActivityForResult(intent, FragmentDetailQuestion.REQUEST_CODE);
            startMyService();
            ((Activity) self).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }else if (lesson.getType().equals("quizz")) {
            Intent gotoQuestion = new Intent(activity, QuestionActivity.class);
            AppController.getInstance().setArrLessons(lessons);
            AppController.getInstance().setLessonIndex(lessons.indexOf(lesson));
            fragment.startActivityForResult(gotoQuestion, QuestionActivity.REQUEST_CODE);
            //startMyService();
            ((Activity) self).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }





    }

    // mo bai
    private void startMyService() {
        MusicService.intentStart(activity, Constant.ACTION_OPEN);
    }

    public void onClickMark(View view) {
//        if (DataStoreManager.isFavorite(lesson))
        if (DataStoreManager.getWatchLesson(lesson.getId()).isFavorite())
//            lesson.setFavorite(false);
            watchLesson.setFavorite(false);
        else
            watchLesson.setFavorite(true);
        DataStoreManager.updateFavoriteLesson(watchLesson);
        notifyChange();
    }

    private void removeLessonInFolder(int position) {
        Lesson lesson = this.lessons.get(position);
        this.lessons.remove(position);
//        lesson.setDownload(false);
        watchLesson.setDownload(false);
        String uriStorage = Environment.getExternalStorageDirectory() + File.separator + Constant.MY_DIR;
        File file = new File(uriStorage, DownloadService.getNameFileMp3(lesson.getImage(), lesson.getId()));
        if (file.exists()) file.delete();
//        DataStoreManager.updateDownload(lesson);
        DataStoreManager.updateDownloadLesson(watchLesson);
    }


    public void onClickDownload(View view) {
//        if (DataStoreManager.isDownload(lesson)) {
        if (DataStoreManager.getWatchLesson(lesson.getId()).isDownload()) {
            //xoa du lieu
                removeLessonInFolder(lessons.indexOf(lesson));
        } else {
            //download
//            if (DataStoreManager.isDownload(lesson)) {
            if (DataStoreManager.getWatchLesson(lesson.getId()).isDownload()) {
                AppUtil.showToast(activity, self.getString(R.string.msg_lesson_downloaded));
            } else {
                if (AppController.getInstance().addDlIndex(lesson.getId())) {
                    Intent intent = new Intent(activity, DownloadService.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constant.LESSON, lesson);
                    bundle.putInt(Constant.POS, lessons.indexOf(lesson));
                    intent.setAction(Constant.ACTION_DOWNLOAD);
                    intent.putExtras(bundle);
                    activity.startService(intent);
                } else {
                    AppUtil.showToast(activity, "Lesson on download queue");
                }
            }
        }
    }


}
