package com.suusoft.elistening.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.suusoft.elistening.AppController;
import com.suusoft.elistening.DaoDownload.DownloadApdapter;
import com.suusoft.elistening.DaoDownload.DownloadDatabase;
import com.suusoft.elistening.DaoDownload.DownloadList;
import com.suusoft.elistening.DaoFavorite.FavoriteAdapter;
import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseFragment;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.listener.IOnclickItem;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.service.DownloadService;
import com.suusoft.elistening.service.MusicService;
import com.suusoft.elistening.util.AppUtil;
import com.suusoft.elistening.view.activity.DetailActivity;
import com.suusoft.elistening.view.activity.FragmentFavorite;
import com.suusoft.elistening.view.activity.VideoViewActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragmentDownload extends BaseFragment implements DownloadApdapter.ItemClickListener {

    final static String TAG = FragmentFavorite.class.getSimpleName();

    private RecyclerView rcv_download;
    private DownloadApdapter adapter;
    private static boolean mLoadMore;
    private DownloadList lesson;
    private TextView tvNodata;
    MediaPlayer player;

    public static FragmentDownload newInstance(boolean loadmore){
        mLoadMore = loadmore;
        Bundle args = new Bundle();
        FragmentDownload fragment = new FragmentDownload();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.fragment_download;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initView(View view) {
        rcv_download = view.findViewById(R.id.rv_download);
        tvNodata    = view.findViewById(R.id.tv_nodata);
        rcv_download.hasFixedSize();
        rcv_download.setLayoutManager(new GridLayoutManager(self, 2));
    }

    @Override
    protected void getData() {


        final List<DownloadList> downloadLists = DownloadDatabase.getInstance(self).downloadDao().getDownloadData();



        if (downloadLists == null) {
            tvNodata.setText("No Item Download");
            tvNodata.setVisibility(View.VISIBLE);

        }else {
            tvNodata.setVisibility(View.GONE);
            adapter = new DownloadApdapter(downloadLists, self);
            adapter.setOnclickItem(new IOnclickItem() {
                @Override
                public void onItemClick(int position) {

                    lesson = downloadLists.get(position);
                    String path = "content://"+Environment.getExternalStorageDirectory().getAbsolutePath() +
                            File.separator +
                            Constant.MY_DIR +"/"+ DownloadService.getNameFileMp3(lesson.getName(), lesson.getId());


                    File file = new File("/storage/emulated/0/elistening/"+ DownloadService.getNameFileMp3(lesson.getName(), lesson.getId()));
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    Uri uri = FileProvider.getUriForFile(self, self.getApplicationContext().getPackageName()
                            + ".provider", file);
                    String type = "audio/mp3";
                    intent.setDataAndType(uri, type);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);


//                    player = new MediaPlayer();
//                    player.stop();
//                    Log.e(TAG, "onItemClick: "+ path );
//
//                    try {
//                        player.setDataSource(path);
//                        player.prepare();
//                    } catch (IllegalArgumentException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        System.out.println("Exception of type : " + e.toString());
//                        e.printStackTrace();
//                    }
//                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mediaPlayer) {
//                            player.start();
//                        }
//                    });


//
                }
            });
            rcv_download.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setListener(this);
        }

    }

    @Override
    public void onItemStudentLongClicked(final int position) {

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
