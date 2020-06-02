package com.suusoft.elistening.view.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suusoft.elistening.AppController;
import com.suusoft.elistening.BR;
import com.suusoft.elistening.DaoDownload.DownloadDatabase;
import com.suusoft.elistening.DaoDownload.DownloadList;
import com.suusoft.elistening.DaoFavorite.FavoriteDatabase;
import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseActivityBinding;
import com.suusoft.elistening.base.vm.BaseViewModel;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.listener.IListenerLoadAudio;
import com.suusoft.elistening.listener.IListenerSwitchDisplayType;
import com.suusoft.elistening.listener.IListenerVisibleKeybroad;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.model.modelLesson.WatchLesson;
import com.suusoft.elistening.service.DownloadService;
import com.suusoft.elistening.service.MusicService;
import com.suusoft.elistening.util.AppUtil;
import com.suusoft.elistening.view.adapter.MyPagerAdapter;
import com.suusoft.elistening.view.fragment.FragmentDetailPlayingSortByTime;
import com.suusoft.elistening.viewmodel.activity.DetailActivityVM;
import com.suusoft.elistening.widgets.mycustomview.MyMediaPlayer;

import java.io.File;
import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Suusoft on 09/15/2017.
 */

public class DetailActivity extends BaseActivityBinding  implements TabLayout.OnTabSelectedListener,
        IListenerLoadAudio , View.OnClickListener, MyMediaPlayer.IListenerHidePlayer , IListenerVisibleKeybroad {
    public static int RESULT_WATCH = 111;
    private static final int PERMISSION_REQUEST_CODE = 200;

    private View viewCheck;
    private RelativeLayout layout_detail;
    private LinearLayout layoutAdmob;
   // private AdView mAdView;
    private int visibleAdMob = View.GONE;

    private DetailActivityVM viewModel;
    public static MyMediaPlayer player;
    private static MyPagerAdapter myAdapter;
    private Toolbar toolbar;
    private TextView tvTitle;
    private RelativeLayout btnBack;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static TextView btnPlay;
    private ImageView imgBackground;
    private DetailActivity.TimeReceiver receiver;
    private DetailActivity.IMove iMove;
    private Lesson lesson;
    private DownloadList download;
    private WatchLesson watchLesson;
    private Menu mMenu;
    private MenuItem mMenuItem;
    private DetailActivity.IChange changePlay;
    private DetailActivity.IChange changeWord;
    private DetailActivity.IChange changePlayList;
    private DetailActivity.IChange changeQuestion;
    private ArrayList<IListenerSwitchDisplayType> arrListenerSwitchDisplayType = new ArrayList<>();
//    public static FavoriteDatabase favoriteDatabase;

    public static MyMediaPlayer getPlayer() {
        return player;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public static MyPagerAdapter getMyPageAdapter() {
        return myAdapter;
    }

    public void setChangeWord(DetailActivity.IChange changeWord) {
        this.changeWord = changeWord;
    }

    public void setChangePlayList(DetailActivity.IChange changePlayList) {
        this.changePlayList = changePlayList;
    }

    public void setChangeQuestion(DetailActivity.IChange changeQuestion) {
        this.changeQuestion = changeQuestion;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkPermission()) {
            openActivity();
        } else {
            if (checkPermission()) {
                requestPermissionAndContinue();
            } else {
                    openActivity();
            }
        }

    }

    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle(getString(R.string.permission_necessary));
                alertBuilder.setMessage(R.string.storage_permission_is_encessary_to_wrote_event);
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
    }

    private void openActivity() {
        //add your further process after giving permission or to download images from remote server.
    }

    @Override
    public void onClick(View v) {
//        if (v==btnPlay){
//            btnPlay.setVisibility(View.GONE);
//            player.setVisibility(View.VISIBLE);
//            playAudioWhilePaused();
//        }
    }

    @Override
    public void onHide() {
        //btnPlay.setVisibility(View.VISIBLE);
       // player.setVisibility(View.VISIBLE);
    }




    /*lắng nghe sự thay đổi của seekbar*/
    public interface IMove {
        void move(int i);
    }

    /*Lắng nghe sự thay đổi khi mediaplayer chuyển sang bài khác*/
    public interface IChange {
        void change();
    }

    public void setChangePlay(DetailActivity.IChange changePlay) {
        this.changePlay = changePlay;
    }

    public void setiMove(DetailActivity.IMove iMove) {
        this.iMove = iMove;
    }

    public void addListenerSwitchDisplayType(IListenerSwitchDisplayType iListenerSwitchDisplayType) {
        arrListenerSwitchDisplayType.add(iListenerSwitchDisplayType);
    }

    public void notifiChangeDisplay() {
        for (IListenerSwitchDisplayType listenerSwitchDisplayType : arrListenerSwitchDisplayType) {
            listenerSwitchDisplayType.onChangeDisplayListOrItem();
        }
    }

    @Override
    protected ToolbarType getToolbarType() {
        return ToolbarType.NAVI;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.activity_mydetail_vm;
    }

    @Override
    protected void getExtraData(Intent intent) {

    }

    @Override
    protected void initialize() {


    }

    @Override
    protected void initView(View view) {
        //Log.e("getScreenWidth", "getScreenWidth " + AppUtil.getScreenWidth(this)+ " -  getScreenWidthAsInch " + AppUtil.getScreenWidthAsInch(this));
        initView();
        onViewCreated();
        AppUtil.checkKeybroadIsShow(viewCheck, self, this);
        //showBannerAdmob();


    }

    /*check show or hide of keybroad*/
    @Override
    public void onShowKeybroad() {

        hiddenTheSuccessions();
    }



    @Override
    public void onHideKeybroad() {
        tabLayout.setVisibility(View.VISIBLE);
//        layoutAdmob.setVisibility(visibleAdMob);
    }

    private void hiddenTheSuccessions() {
        findViewById(R.id.edtInputAnswer).setFocusable(true);
        tabLayout.setVisibility(View.GONE);
        layoutAdmob.setVisibility(View.GONE);
    }

    @Override
    protected BaseViewModel getViewModel() {
        viewModel = new DetailActivityVM(self);
        return viewModel;
    }

    @Override
    protected void setViewDataBinding(ViewDataBinding binding) {
        binding.setVariable(BR.viewModel, viewModel);

    }

//    private void showBannerAdmob() {
//        MobileAds.initialize(this, getResources().getString(R.string.app_id_admob));
//        layoutAdmob = (LinearLayout) findViewById(R.id.layoutAdmob);
//        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())) {
//            layoutAdmob.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getBackgroundMainLight()));
//        } else {
//            layoutAdmob.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getBackgroundMainDark()));
//        }
//        mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        setListenerAdmob();
//    }
//
//    private void setListenerAdmob() {
//        mAdView.setAdListener(adListener);
//    }
//
//
//    private AdListener adListener = new AdListener(){
//        @Override
//        public void onAdLoaded() {
//            // Code to be executed when an ad finishes loading.
//            Log.i("Ads", "onAdLoaded");
//            visibleAdMob = View.VISIBLE;
//            layoutAdmob.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        public void onAdFailedToLoad(int errorCode) {
//            // Code to be executed when an ad request fails.
//            Log.i("Ads", "onAdFailedToLoad");
//            visibleAdMob = View.GONE;
//            layoutAdmob.setVisibility(View.GONE);
//        }
//    };

    @Override
    protected void onResume() {
        receiver = new DetailActivity.TimeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BC_TIME);
        filter.addAction(Constant.BC_TIMEALL);
        filter.addAction(Constant.BC_CHANGE);
        filter.addAction(Constant.BC_REPLAY);
        registerReceiver(receiver, filter);
        setmDetail();

        Log.e("DetailActivity", "onResum ");
        onViewCreated();
        super.onResume();

    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        pauseAudioWhilePlaying();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        pauseAudioWhilePlaying();
        super.onBackPressed();
    }

    public void setTabsetlectQuestionFragment(){
        tabLayout.getTabAt(2).select();
        viewPager.setCurrentItem(2);
    }


    protected void initView() {
        checkCountAccessToShowAdmob();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow();
//            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        indicator = (MyIndicator) findViewById(R.id.indicator);
//        toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
//        tvTitle = (TextView)findViewById(R.id.tvTilte);
//        tvTitle.setSelected(true);
//        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        viewCheck = findViewById(R.id.content_main);
        layout_detail = (RelativeLayout) findViewById(R.id.layout_detail);
        player = (MyMediaPlayer) findViewById(R.id.media);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager_detail);
        imgBackground = (ImageView) findViewById(R.id.img_backgroundplaying);
//        btnPlay = (TextView) findViewById(R.id.btnPlay);
//        btnPlay.setOnClickListener(this);
        player .setiListenerHidePlayer(this);


    }

    private void setDetail() {
        lesson = AppController.getInstance().getLessonAt(AppController.getInstance().getLessonIndex());
        if(lesson!=null){
            watchLesson = DataStoreManager.getWatchLesson(lesson.getId());
            String c = lesson.getName();
            String imgURL = lesson.getImage();
            setToolbarTitle(c);
        }

//        tvTitle.setText(c);
//        getSupportActionBar().setTitle(c);
        /*Load background image*/
//        if (imgURL != null) {
//            Picasso.with(this).load(imgURL).error(getResources().getDrawable(R.drawable.ic_star_black)).into(new Target() {
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                    imgBackground.setImageBitmap(BlurUtil.blur(getApplication(), bitmap));
//                }
//
//                @Override
//                public void onBitmapFailed(Drawable errorDrawable) {
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable placeHolderDrawable) {
//                    Bitmap bitmap = Bitmap.createBitmap(200, 400, Bitmap.Config.ARGB_8888);
//                    bitmap.eraseColor(getResources().getColor(R.color.grey_bold));
//                    imgBackground.setImageBitmap(BlurUtil.blur(getApplication(), bitmap));
//                }
//            });
//        }
    }

    private void setmDetail() {
        setDetail();
        if (mMenu != null ) {
//            if (DataStoreManager.getWatchLesson(lesson.getId()).isFavorite()) {
//                mMenu.getItem(1).setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_fill_yellow));
//            } else {
//                mMenu.getItem(1).setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_border_white));
//            }

            //change icon menu option
//            if (tabLayout.getSelectedTabPosition()==Constant.TAB_SELECTED_PLAYING){
//                if (DataStoreManager.isDisplayTypeList())
//                    mMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_list_white_24dp));
//                else
//                    mMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_grid_white_24dp));
//
//            }else  if (tabLayout.getSelectedTabPosition()==Constant.TAB_SELECTED_VOCA) {
//                if (DataStoreManager.isExpandListWord())
//                    mMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_expand_less_white_24dp));
//                else
//                    mMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_expand_more_white_24dp));
//            }else  mMenu.getItem(0).setIcon(null);
        }
    }

    protected void onViewCreated() {

        setDetail();
        myAdapter = new MyPagerAdapter(self, getSupportFragmentManager(), this);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);
        FragmentDetailPlayingSortByTime.setMediaPlayer(player);

        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())){
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor(GlobalValue.getTheme().getColorTabSelected_dark()));
            tabLayout.setTabTextColors(Color.parseColor(GlobalValue.getTheme().getColorTabNormal_dark()) ,
                    Color.parseColor(GlobalValue.getTheme().getColorTabSelected_dark()));
//            btnPlay.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getColorTabSelected_dark()));
//            btnPlay.setTextColor(Color.parseColor(GlobalValue.getTheme().getColorTabSelected_light()));

        }else {
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor(GlobalValue.getTheme().getColorTabSelected_light()));
            tabLayout.setTabTextColors(Color.parseColor(GlobalValue.getTheme().getColorTabNormal_light()) ,
                    Color.parseColor(GlobalValue.getTheme().getColorTabSelected_light()));
//            btnPlay.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getColorTabSelected_light()));
//            btnPlay.setTextColor(Color.parseColor(GlobalValue.getTheme().getColorTabSelected_dark()));
        }



    }

    /*popup show admob*/
    private void checkCountAccessToShowAdmob() {
//        if (DataStoreManager.getCountAccess() >= 2) {
//            DataStoreManager.saveCountAccess(0);
//            MyAdmob.showInterstitialAdmob(self);
//        } else {
//            DataStoreManager.saveCountAccess(DataStoreManager.getCountAccess() + 1);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        tabLayout.setOnTabSelectedListener(this);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_detail, menu);
//        if (DataStoreManager.isFavorite(lesson)) {
//        if (DataStoreManager.getWatchLesson(lesson.getId()).isFavorite()) {
//            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_fill_yellow));
//        } else menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_border_white));


        if (FavoriteDatabase.getInstance(this).favoriteDao().isFavorite(lesson.getId()) == 1) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_fill_yellow));
        } else menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_item_favourite_border_white));




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.mMenuItem = item;
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.btn_menu_favorite) {
            Lesson favoriteList = new Lesson();

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

            favoriteList.setId(id);
            //favoriteList.setCode(code);
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
        else if (item.getItemId() == R.id.btn_menu_download){

            final DownloadList downloadList = new DownloadList();

            int id       = lesson.getId();
            String name  = lesson.getName();
            String image = lesson.getImage();
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
            String crea_user   = lesson.getCreatedUser();
            String application = lesson.getApplicationId();

            downloadList.setId(id);
            //downloadList.setCode(code);
            downloadList.setName(name);
            downloadList.setImage(image);
            downloadList.setOverview(overview);
            downloadList.setContent(conten);
            downloadList.setAttachment(attachment);
            downloadList.setLinkUrl(url_link);
            downloadList.setType(type);
            downloadList.setCategoryId(category);
            downloadList.setIsActive(isActive);
            downloadList.setCode(code);
            downloadList.setSubtitle(subTitle);
            downloadList.setAuthor(auThor);
            downloadList.setSortOrder(sort);
            downloadList.setIsHot(isHot);
            downloadList.setIsTop(top);
            downloadList.setRate(rate);
            downloadList.setRateCount(countRate);
            downloadList.setViewCount(viewCount);
            downloadList.setLevel(level);
            downloadList.setCreatedDate(date);
            downloadList.setModifiedDate(modifile);
            downloadList.setModifiedUser(modifileUser);
            downloadList.setCreatedUser(crea_user);
            downloadList.setApplicationId(application);

            if (DownloadDatabase.getInstance(this).downloadDao().isDownload(id)!=1) {
                DownloadDatabase.getInstance(self).downloadDao().addDataDownload(downloadList);
                processingDownloadLessonCurrentPlaying(item);

            }else {

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(self);
                builder.setMessage("You have already downloaded this song, do you want to delete it to reload it")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                DownloadDatabase.getInstance(self).downloadDao().deleteDownload(downloadList);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AppUtil.showToast(self, "good bye");
                            }
                        });
                android.app.AlertDialog alert11 = builder.create();
                alert11.show();


            }




        }
        return true;
    }

    public void processingDownloadLessonCurrentPlaying(MenuItem item) {
//        if (DataStoreManager.isDownload(lesson)) {
        if (DataStoreManager.getWatchLesson(lesson.getId()).isDownload()) {
            AppUtil.showToast(DetailActivity.this, R.string.msg_lesson_downloaded);
        } else {
//            if (DataStoreManager.isDownload(lesson)) {
            if (DataStoreManager.getWatchLesson(lesson.getId()).isDownload()) {
                AppUtil.showToast(DetailActivity.this, self.getString(R.string.msg_lesson_downloaded));
            } else {
                if (AppController.getInstance().addDlIndex(lesson.getId())) {

                    Intent intent = new Intent(DetailActivity.this, DownloadService.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constant.LESSON, lesson);
                    intent.setAction(Constant.ACTION_DOWNLOAD);
                    intent.putExtras(bundle);
                    DetailActivity.this.startService(intent);
                } else {
                    AppUtil.showToast(DetailActivity.this, "Lesson on download queue");
                }
            }
        }
    }

    private void removeLessonInFolder(Lesson lesson) {
        lesson.setDownload(false);
        String uriStorage = Environment.getExternalStorageDirectory() + File.separator + Constant.MY_DIR;
        File file = new File(uriStorage, DownloadService.getNameFileMp3(lesson.getName(), lesson.getId()));
        if (file.exists()) file.delete();
        DataStoreManager.updateDownload(lesson);
    }

    private void processingSwitchList(MenuItem item) {
        //while show Fragment playing
        if (tabLayout.getSelectedTabPosition()== Constant.TAB_SELECTED_PLAYING){
            //KHi câu hỏi đc hiển thị, ko cho phép người dùng chuyển đổi kiểu hiển thị
            if (AppController.isShowQuestionInPagePlaying) {
                AppUtil.showToast(self, getResources().getString(R.string.you_have_not_answered_the_question_yet));
            } else {
//                if (DataStoreManager.isDisplayTypeList()) {
//                    DataStoreManager.saveDisplayTypeList(false);
//                    notifiChangeDisplay();
//                    item.setIcon(getResources().getDrawable(R.drawable.ic_grid_white_24dp));
//                } else {
//                    DataStoreManager.saveDisplayTypeList(true);
//                    notifiChangeDisplay();
//                    item.setIcon(getResources().getDrawable(R.drawable.ic_list_white_24dp));
//                }
            }

            //while show Fragment voca
        }else if (tabLayout.getSelectedTabPosition()== Constant.TAB_SELECTED_VOCA){

//            if (DataStoreManager.isExpandListWord()) {
//                DataStoreManager.saveExpandListWord(false);
//                notifiChangeDisplay();
//                item.setIcon(getResources().getDrawable(R.drawable.ic_expand_more_white_24dp));
//            } else {
//                DataStoreManager.saveExpandListWord(true);
//                notifiChangeDisplay();
//                item.setIcon(getResources().getDrawable(R.drawable.ic_expand_less_white_24dp));
//            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("DetailActivity", "onStop");
        setResult(RESULT_WATCH);
        pauseAudioWhilePlaying();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("DetailActivity", "onDestroy");
        setResult(RESULT_WATCH);
        pauseAudioWhilePlaying();

    }


    class TimeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle b = intent.getExtras();
            switch (action) {
                case Constant.BC_TIMEALL:
                    int anInt = b.getInt(Constant.K_DUR, 0);
                    player.setTxtTimetotal(anInt);
                    break;
                case Constant.BC_TIME:
                    int time = b.getInt(Constant.K_POS, 0);
                    if (time> 0){
                        if (tabLayout.getTabAt(Constant.TAB_SELECTED_PLAYING).isSelected())
                            checkShowBtnPlayOrMediaplayer();
                        else setHidePlayer();
                    }
//                    int time = time1;
//                    if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1){
//                        time = time1*100;
//                    }

                    player.setTimeCurrent(time);
                    Log.e("setTimeCurrent", time + "");
                    if (iMove != null)
                        iMove.move(time );
                    break;
                case Constant.BC_CHANGE:
                    player.setTxtTimetotal(0);
                    player.setTimeCurrent(0);

                    if (changePlay == null){
                        AppUtil.showToast(DetailActivity.this, "No Item");
                    }else {
                        changePlay.change();
                    }
                    if (changeWord != null) changeWord.change();
                    if (changePlayList != null) changePlayList.change();
                    if (changeQuestion != null) changeQuestion.change();
                    setmDetail();

                    break;
                case Constant.BC_REPLAY:
                    MusicService.ReplayState replayState = (MusicService.ReplayState) b.getSerializable(Constant.K_REPLAY);
                    if (replayState == MusicService.ReplayState.ReplayAll)
                        player.setReplayAll(true);
                    else player.setReplayAll(false);
                    break;
            }
            if (MusicService.playState == MusicService.PlayState.Playing) {
                player.setPause();
            } else {
                player.setPlay();
                if (MusicService.playState == MusicService.PlayState.Stopped) {
                    player.setTxtTimetotal(0);
                    player.setTimeCurrent(0);
                }
            }
        }
    }

    @Override
    public void onLoadedAudio() {
        processingPlayOrStopMusicWhileNextLesson();

    }




//    ------------------------------------------------------------------------------------------------------------------------




    private void processingPlayOrStopMusicWhileNextLesson() {
        if (tabLayout.getSelectedTabPosition()==Constant.TAB_SELECTED_QUESTION){
            if (GlobalValue.getTypeDisplayQuestion().getId() == Config.TYPE_LAST_QUESTION){
                pauseAudioWhilePlaying();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == FragmentDetailQuestion.REQUEST_CODE) {
//            if (resultCode == FragmentDetailQuestion.RESULT_CODE_REPLY_AGAIN) {
//                Log.e("MyDetailAvtivity", "on REPLY_AGAIN");
//                FragmentDetailQuestion.newInstance().reloadQuestion();
//            }
//
//            if (resultCode == FragmentDetailQuestion.RESULT_CODE_BACK_TO_BOARD) {
//                Log.e("MyDetailAvtivity", "on RESULT_CODE_BACK_TO_BOARD");
//                setResult(FragmentDetailQuestion.RESULT_CODE_BACK_TO_BOARD);
//                finish();
//            }
//
//            if (resultCode == FragmentDetailQuestion.RESULT_CODE_NEXT_LESSON) {
//                Log.e("MyDetailAvtivity", "on RESULT_CODE_NEXT_LESSON");
//                FragmentDetailQuestion.newInstance().onNextLesson();
//            }
//        }
    }

    private void setHidePlayer(){
//        player.setVisibility(View.GONE);
//        btnPlay.setVisibility(View.GONE);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()){
            //Show icon item menu
            case Constant.TAB_SELECTED_PLAYING:
//                checkShowBtnPlayOrMediaplayer();
//
//                if (!mMenu.getItem(0).isVisible()) mMenu.getItem(0).setVisible(true);
//                if (DataStoreManager.isDisplayTypeList())
//                    mMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_list_white_24dp));
//                else mMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_grid_white_24dp));
//
//                playAudioWhilePaused();
//
//
//                break;

            case Constant.TAB_SELECTED_VOCA:
//

            case Constant.TAB_SELECTED_QUESTION:
                pauseAudioWhilePlaying();
                /*Thay đổi chế độ: ko play audio khi chuyển sang tab Quiz*/
//                if (DataStoreManager.getTypeDisplayQuestion().getId() == Config.TYPE_LAST_QUESTION){
//                    if (MusicService.getMediaPlayer().isPlaying()){
//                        Intent it = new Intent(self, MusicService.class);
//                        it.setAction(Constant.ACTION_PLAY);
//                        self.startService(it);
//                    }
//                }

                if (mMenu.getItem(0).isVisible())
                    mMenu.getItem(0).setVisible(false);
                setHidePlayer();
                break;

//            case Constant.TAB_SELECTED_PLAY_LIST:
//                if (mMenu.getItem(0).isVisible())
//                    mMenu.getItem(0).setVisible(false);
//                break;
        }
    }

    private void pauseAudioWhilePlaying() {
        if (MusicService.getMediaPlayer().isPlaying()){
            MusicService.intentStart(self,Constant.ACTION_PLAY );
        }
    }

    private void playAudioWhilePaused() {
        if (!MusicService.getMediaPlayer().isPlaying()){
            MusicService.intentStart(self,Constant.ACTION_PLAY );
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
//        switch (tab.getPosition()) {
//            //Show icon item menu
//            case Constant.TAB_SELECTED_PLAYING:
//               checkShowBtnPlayOrMediaplayer();
//                break;
//
//            case Constant.TAB_SELECTED_VOCA:
//                setHidePlayer();
//                break;
//
//            case Constant.TAB_SELECTED_QUESTION:
//                setHidePlayer();
//                break;
//        }
    }

    private void checkShowBtnPlayOrMediaplayer(){
        if (AppController.isShowQuestionInPagePlaying){
            setHidePlayer();
        }else{
            //if (MusicService.getMediaPlayer()!=null)
                if (MusicService.getMediaPlayer().isPlaying()){
                    player.setVisibility(View.VISIBLE);
                    //btnPlay.setVisibility(View.GONE);
                } else {
                    player.setVisibility(View.VISIBLE);
                    //btnPlay.setVisibility(View.VISIBLE);
                }
        }
    }

}
