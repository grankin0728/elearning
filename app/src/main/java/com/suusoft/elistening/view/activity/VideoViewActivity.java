package com.suusoft.elistening.view.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.suusoft.elistening.AppController;
import com.suusoft.elistening.DaoFavorite.FavoriteDatabase;
import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseActivity;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.model.modelLesson.WatchLesson;
import com.suusoft.elistening.service.DownloadService;
import com.suusoft.elistening.util.AppUtil;

public class VideoViewActivity extends BaseActivity {

    public static int REQUEST_CODE = 10;
    private VideoView videoView;
    private MediaController mediaController;
    private int position = 0;
    private Lesson lesson;
    private Menu mMenu;
    private MenuItem mMenuItem;
    private WatchLesson watchLesson;
    private TextView tvContent;


    @Override
    protected ToolbarType getToolbarType() {
        return ToolbarType.NAVI;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.activity_video;
    }

    @Override
    protected void getExtraData(Intent intent) {
        lesson = AppController.getInstance().getLessonAt(AppController.getInstance().getLessonIndex());
    }

    @Override
    protected void initilize() {

    }

    @Override
    protected void initView() {
        setToolbarTitle(lesson.getName());
        videoView   = findViewById(R.id.videoview);
        tvContent   = findViewById(R.id.tv_content);
        tvContent.setMovementMethod(new ScrollingMovementMethod());
        getData();
    }

    @Override
    protected void onViewCreated() {

    }

    private void getData(){
        Log.e("Video", "getData: "+ lesson.getAttachment());
        if (lesson != null) {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.setMargins(10,10,10,10);
//            tvContent.setLayoutParams(params);
            tvContent.setText(lesson.getContent());
            if (mediaController == null) {
                mediaController = new MediaController(this);

                // Neo vị trí của MediaController với VideoView.
                mediaController.setAnchorView(videoView);

                // Sét đặt bộ điều khiển cho VideoView.
                videoView.setMediaController(mediaController);
            }

            // Sự kiện khi file video sẵn sàng để chơi.
            videoView.setVideoPath(lesson.getAttachment());
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    videoView.seekTo(position);
                    if (position == 0) {
                        videoView.start();
                    }

                    // Khi màn hình Video thay đổi kích thước
                    mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                            // Neo lại vị trí của bộ điều khiển của nó vào VideoView.
                            mediaController.setAnchorView(videoView);
                        }
                    });
                }
            });
        }
    }

    // Khi bạn xoay điện thoại, phương thức này sẽ được gọi
    // nó lưu trữ lại ví trí file video đang chơi.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Lưu lại vị trí file video đang chơi.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }


    // Sau khi điện thoại xoay chiều xong. Phương thức này được gọi,
    // bạn cần tái tạo lại ví trí file nhạc đang chơi.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Lấy lại ví trí video đã chơi.
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.video_menu, menu);


        if (FavoriteDatabase.getInstance(this).favoriteDao().isFavorite(lesson.getId()) == 1) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_fill_yellow));
        } else menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_border_white));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mMenuItem = item;
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.btn_menu_favorite) {
            Lesson favoriteList = new Lesson();

            //get Data
            int id            = lesson.getId();
            String name       = lesson.getName();
            String image      = lesson.getImage();
            String overview   = lesson.getOverview();
            String conten     = lesson.getContent();
            String attachment = lesson.getAttachment();
            String url_link   = lesson.getLinkUrl();
            String type       = lesson.getType();
            String category   = lesson.getCategoryId();
            int isActive      = lesson.getIsActive();
            String code       = lesson.getCode();
            String subTitle   = lesson.getSubtitle();
            String auThor     = lesson.getAuthor();
            String sort       = lesson.getSortOrder();
            int isHot         = lesson.getIsHot();
            int top           = lesson.getIsTop();
            int rate          = lesson.getRate();
            int countRate     = lesson.getRateCount();
            int viewCount     = lesson.getViewCount();
            String level      = lesson.getLevel();
            String date       = lesson.getCreatedDate();
            String modifile   = lesson.getModifiedDate();
            String modifileUser = lesson.getModifiedUser();
            String crea_user    = lesson.getCreatedUser();
            String application  = lesson.getApplicationId();

            //set data to database
            favoriteList.setId(id);
            favoriteList.setName(name);
            favoriteList.setImage(image);
            favoriteList.setOverview(overview);
            favoriteList.setContent(conten);
            favoriteList.setAttachment(attachment);
            favoriteList.setLinkUrl(url_link);
            favoriteList.setType(type);
            favoriteList.setCategoryId(category);
            favoriteList.setIsActive(isActive);
            favoriteList.setCode(code);
            favoriteList.setSubtitle(subTitle);
            favoriteList.setAuthor(auThor);
            favoriteList.setSortOrder(sort);
            favoriteList.setIsHot(isHot);
            favoriteList.setIsTop(top);
            favoriteList.setRate(rate);
            favoriteList.setRateCount(countRate);
            favoriteList.setViewCount(viewCount);
            favoriteList.setLevel(level);
            favoriteList.setCreatedDate(date);
            favoriteList.setModifiedDate(modifile);
            favoriteList.setModifiedUser(modifileUser);
            favoriteList.setCreatedUser(crea_user);
            favoriteList.setApplicationId(application);

            if (FavoriteDatabase.getInstance(this).favoriteDao().isFavorite(id)!=1){
                item.setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_border_white));
                FavoriteDatabase.getInstance(this).favoriteDao().addData(favoriteList);

            }else {
                item.setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_fill_yellow));
                FavoriteDatabase.getInstance(this).favoriteDao().delete(favoriteList);

            }

        }
        return true;
    }

    private void processingDownloadLessonCurrentPlaying(MenuItem item) {
//        if (DataStoreManager.isDownload(lesson)) {
        if (DataStoreManager.getWatchLesson(lesson.getId()).isDownload()) {
            AppUtil.showToast(VideoViewActivity.this, R.string.msg_lesson_downloaded);
        } else {
//            if (DataStoreManager.isDownload(lesson)) {
            if (DataStoreManager.getWatchLesson(lesson.getId()).isDownload()) {
                AppUtil.showToast(VideoViewActivity.this, self.getString(R.string.msg_lesson_downloaded));
            } else {
                if (AppController.getInstance().addDlIndex(lesson.getId())) {
                    Intent intent = new Intent(VideoViewActivity.this, DownloadService.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constant.LESSON, lesson);
                    intent.setAction(Constant.ACTION_DOWNLOAD);
                    intent.putExtras(bundle);
                    VideoViewActivity.this.startService(intent);
                } else {
                    AppUtil.showToast(VideoViewActivity.this, "Lesson on download queue");
                }
            }
        }
    }
}
