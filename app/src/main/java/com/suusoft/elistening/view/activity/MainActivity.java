package com.suusoft.elistening.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.base.view.BaseActivity;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.configs.Global;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.listener.IFilter;
import com.suusoft.elistening.listener.IListenerSwitchDisplayType;
import com.suusoft.elistening.model.ListType;
import com.suusoft.elistening.model.MenuLeft;
import com.suusoft.elistening.util.AppUtil;
import com.suusoft.elistening.view.adapter.ListLessonAdapter;
import com.suusoft.elistening.view.adapter.MenuLeftAdapter;
import com.suusoft.elistening.view.fragment.FragmentAbout;
import com.suusoft.elistening.view.fragment.FragmentDownload;
import com.suusoft.elistening.view.fragment.FragmentFeedBack;
import com.suusoft.elistening.view.fragment.FragmentListLesson;
import com.suusoft.elistening.view.fragment.FragmentSearch;
import com.suusoft.elistening.view.fragment.FragmentSettings;
import com.suusoft.elistening.view.fragment.HomeFragment;
import com.suusoft.elistening.view.fragment.MoreAppFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private Fragment mCurrenFragment;
    private Bundle bundle;
    private int mIndex = 0;

    private RelativeLayout layoutMenuLeft;
    private TextView navClose;
    private Menu menu;
    public MenuItem itemListOrGrid;
    public FragmentSearch mFragmentSearch;

   // private AdView mAdView;
    private LinearLayout layoutAdmob;

    private ListView mLvMenu;
    private ArrayList<MenuLeft> mArrMenu;
    private Integer[] arrImageMenu;
    private MenuLeftAdapter mMenuLeftAdapter;
    private BottomNavigationView navigationView;
    private boolean checkSelectMenuLeft = true;
    private static IListenerSwitchDisplayType iListenerSwitchDisplayType;

    public static void setiListenerSwitchDisplayType(IListenerSwitchDisplayType iListenerSwitchDisplayType) {
        MainActivity.iListenerSwitchDisplayType = iListenerSwitchDisplayType;
    }

    private IFilter iFilter;

    @Override
    protected ToolbarType getToolbarType() {
        return ToolbarType.NORMAL;
    }

    @Override
    protected int getLayoutInflate() {
        return R.layout.activity_main;
    }

    @Override
    protected void getExtraData(Intent intent) {

    }

    public void setiFilter(IFilter iFilter) {
        this.iFilter = iFilter;
    }

    @Override
    protected void initilize() {
//        initNavigationView();
//        initMenuLeft();
        initBackStack();
    }

    private void initBackStack() {

    }

    @Override
    protected void initView() {
        navigationView = findViewById(R.id.bottom_naview);
        navigationView.setOnNavigationItemSelectedListener(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter(Constant.LISTEN_CHANGE_THEME));
        showBannerAdmob();
        //MyAdmob.initInterstitialAdmob(this);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager ()
                    .beginTransaction ()
                    .replace (R.id.content, fragment)
                    .commit ();
            return true;
        }
        return false;
    }

    private void showBannerAdmob() {
//        layoutAdmob = (LinearLayout) findViewById(R.id.layoutAdmob);
//        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())) {
//            layoutAdmob.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getBackgroundMainLight()));
//        } else {
//            layoutAdmob.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getBackgroundMainDark()));
//        }
//        mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//                Log.i("Ads", "onAdLoaded");
//                layoutAdmob.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//                Log.i("Ads", "onAdFailedToLoad");
//                layoutAdmob.setVisibility(View.GONE);
//            }
//        });
    }

    @Override
    protected void onViewCreated() {
        //setTheme(R.style.AppTheme2);
        bundle = new Bundle();
        bundle.putInt(Constant.OPTIONS_FRAGMENT_LIST_LESSON, Constant.MENU_LEFT_HOME);
//        bundle.putString(Constant.KEY_TITLE_TOOLBAR, getString(R.string.home));
        mCurrenFragment = FragmentListLesson.newInstance(true);
//        setToolbarTitle(getString(R.string.home));
        mCurrenFragment = HomeFragment.newInstance();
        mCurrenFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mCurrenFragment, "FRAGMENT_" + MenuLeft.FRAGMENT_HOME)
                .commit();


//        loadFragment(new HomeFragment());

    }


    private void initNavigationView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        ((MainActivity) self).showIconToolbar(R.id.action_search2, R.id.action_list_or_grid);
        return true;
    }

    public void showIconToolbar(Integer... id) {
        Log.e("eee", "3");
        if (null != menu) {
            MenuItem itemBookMarks = menu.findItem(R.id.action_bookmarks);
            MenuItem itemSearch = menu.findItem(R.id.action_search2);
            MenuItem itemClearHistory = menu.findItem(R.id.action_clear_history);
            itemListOrGrid = menu.findItem(R.id.action_list_or_grid);
            itemBookMarks.setVisible(false);
            itemSearch.setVisible(false);
            itemClearHistory.setVisible(false);
            itemListOrGrid.setVisible(false);
            if (Config.ID_TYPE_GRID == GlobalValue.getListType().getId()) {
                itemListOrGrid.setIcon(R.drawable.ic_list);
            } else {
                itemListOrGrid.setIcon(R.drawable.ic_grid);
            }
//            iListenerSwitchDisplayType.onChangeDisplayListOrItem();

//            if (DataStoreManager.isDisplayTypeListMain()) {
//                itemListOrGrid.setIcon(R.drawable.ic_list);
//            } else {
//                itemListOrGrid.setIcon(R.drawable.ic_grid);
//            }

            for (int i = 0; i < id.length; i++) {
                switch (id[i]) {
                    case R.id.action_bookmarks:
                        itemBookMarks.setVisible(true);
                        break;
                    case R.id.action_search2:
                        itemSearch.setVisible(true);
                        break;
                    case R.id.action_clear_history:
                        itemClearHistory.setVisible(true);
                        break;
                    case R.id.action_list_or_grid:
                        itemListOrGrid.setVisible(false);
                        break;
                }
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search2:
                int fragment = mCurrenFragment.getArguments().getInt(Constant.OPTIONS_FRAGMENT_LIST_LESSON);

                if (fragment == Constant.MENU_LEFT_HOME){
                    mFragmentSearch = FragmentSearch.newInstance(Constant.MENU_LEFT_HOME);
                }else if (fragment == Constant.MENU_LEFT_DOWNLOAD){
                    mFragmentSearch = FragmentSearch.newInstance(Constant.MENU_LEFT_DOWNLOAD);
                }else if (fragment == Constant.MENU_LEFT_FAVOURITE){
                    mFragmentSearch = FragmentSearch.newInstance(Constant.MENU_LEFT_FAVOURITE);
                }

                ((FragmentActivity) self).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, mFragmentSearch, Constant.FRAGMENT_SEARCH)
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.action_clear_history:
                new AlertDialog.Builder(self)
                        .setTitle(R.string.app_name)
                        .setMessage(R.string.message_clear_history)
                        .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                showSnackBar(R.string.successfully);
                                /*processing clear*/
//                                ((FragmentListBook) mCurrenFragment).getmAdapter().removeAll();
//                                DataStoreManager.clearBook(Constant.LIST_BOOK_HISTORY);
                            }
                        })
                        .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();

                return true;
            case R.id.action_list_or_grid:
                //clickActionListOrGrid();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickActionListOrGrid() {
        String tag = getSupportFragmentManager().findFragmentById(R.id.content).getTag();
        if (tag.equals("FRAGMENT_" + MenuLeft.FRAGMENT_HOME)
                || tag.equals("FRAGMENT_" + MenuLeft.FRAGMENT_DOWNLOAD)
                || tag.equals("FRAGMENT_" + MenuLeft.FRAGMENT_FAVOURITE)) {
            if (Config.ID_TYPE_LIST ==GlobalValue.getListType().getId()){
                ListType listType = new ListType(Config.ID_TYPE_GRID, Config.TYPE_GRID_NAME);
                DataStoreManager.saveListType(listType);
                GlobalValue.setListType(listType);
                itemListOrGrid.setIcon(R.drawable.ic_list);

            }else {
                ListType listType = new ListType(Config.ID_TYPE_LIST, Config.TYPE_LIST_NAME);
                DataStoreManager.saveListType(listType);
                GlobalValue.setListType(listType);
                itemListOrGrid.setIcon(R.drawable.ic_grid);
            }
            ((FragmentListLesson) mCurrenFragment).setUpListOrGrid(GlobalValue.getListType().getId());
        } else if (tag.equals(Constant.FRAGMENT_SEARCH)) {
            ListLessonAdapter adapter = mFragmentSearch.getmAdapter();
            if (adapter != null) {
                if (adapter.getType() == Config.ID_TYPE_GRID) {
                    itemListOrGrid.setIcon(R.drawable.ic_grid);
                    mFragmentSearch.setUpListOrGrid(Config.ID_TYPE_LIST);
                } else {
                    itemListOrGrid.setIcon(R.drawable.ic_list);
                    mFragmentSearch.setUpListOrGrid(Config.ID_TYPE_GRID);
                }
            }
//        } else {
//            ListBookAdapter adapter = ((FragmentListBook) mCurrenFragment).getmAdapter();
//            if (adapter.getType() == Config.ID_TYPE_GRID) {
//                itemListOrGrid.setIcon(R.drawable.ic_grid);
//                ((FragmentListBook) mCurrenFragment).setUpListOrGrid(Config.ID_TYPE_LIST);
//            } else {
//                itemListOrGrid.setIcon(R.drawable.ic_list);
//                ((FragmentListBook) mCurrenFragment).setUpListOrGrid(Config.ID_TYPE_GRID);
//            }
        }
    }


    public void showListOrGird(){
        ((FragmentListLesson) mCurrenFragment).setUpListOrGrid(GlobalValue.getListType().getId());
    }


    private void switchScreen(int values) {
        bundle = new Bundle();
        String tag = "FRAGMENT_" + values;
        switch (values) {
            case MenuLeft.FRAGMENT_HOME:
                checkSelectMenuLeft = true;
                bundle.putInt(Constant.OPTIONS_FRAGMENT_LIST_LESSON, Constant.MENU_LEFT_HOME);
                bundle.putString(Constant.KEY_TITLE_TOOLBAR, getString(R.string.home));
                //mCurrenFragment = FragmentListLesson.newInstance(true);
                setToolbarTitle(getString(R.string.home));
                mCurrenFragment = HomeFragment.newInstance();
                break;
//            case MenuLeft.FRAGMENT_BEGIN:
//                checkSelectMenuLeft = true;
//                bundle.putInt(Constant.OPTIONS_FRAGMENT_LIST_LESSON, Constant.MENU_LEFT_FEEDBACK);
//                bundle.putString(Constant.KEY_TITLE_TOOLBAR, getString(R.string.begin));
//                mCurrenFragment = FragmentListBook.newInstance(true);
//                break;
//            case MenuLeft.FRAGMENT_INTERMEDIATE:
//                checkSelectMenuLeft = true;
//                bundle.putString(Constant.KEY_TITLE_TOOLBAR, getString(R.string.intermediate));
//                mCurrenFragment = FragmentCategory.newInstance();
//                break;
//            case MenuLeft.FRAGMENT_ADVANCE:
//                checkSelectMenuLeft = true;
//                bundle.putInt(Constant.OPTIONS_FRAGMENT_LIST_LESSON, Constant.MENU_LEFT_MORE_APP);
//                bundle.putString(Constant.KEY_TITLE_TOOLBAR, getString(R.string.advance));
//                mCurrenFragment = FragmentListBook.newInstance(true);
//                break;
            case MenuLeft.FRAGMENT_FAVOURITE:
                checkSelectMenuLeft = true;
                bundle.putInt(Constant.OPTIONS_FRAGMENT_LIST_LESSON, Constant.MENU_LEFT_FAVOURITE);
                bundle.putString(Constant.KEY_TITLE_TOOLBAR, getString(R.string.favorite));
                mCurrenFragment = FragmentListLesson.newInstance(true);
                setToolbarTitle(getString(R.string.favorite));
                break;
            case MenuLeft.FRAGMENT_DOWNLOAD:
                checkSelectMenuLeft = true;
                bundle.putInt(Constant.OPTIONS_FRAGMENT_LIST_LESSON, Constant.MENU_LEFT_DOWNLOAD);
                bundle.putString(Constant.KEY_TITLE_TOOLBAR, getString(R.string.download));
                setToolbarTitle(getString(R.string.download));
                mCurrenFragment = FragmentListLesson.newInstance(true);
                break;
            case MenuLeft.FRAGMENT_SETTING:
                checkSelectMenuLeft = true;
                mCurrenFragment = FragmentSettings.newInstance();
                break;
            case MenuLeft.FRAGMENT_FEEDBACK:
                checkSelectMenuLeft = true;
                mCurrenFragment = FragmentFeedBack.newInstance();
                break;
            case MenuLeft.FRAGMENT_ABOUT:
                checkSelectMenuLeft = true;
                mCurrenFragment = FragmentAbout.newInstance();
                break;
            case MenuLeft.FRAGMENT_MORE_APP:
                int typeMoreApp = Config.getTypeMoreApp(self);
                if (Config.TYPE_MORE_APP_FROM_GOOGLE_PLAY == typeMoreApp) {
                    checkSelectMenuLeft = false;
                    AppUtil.goToResultByKeySearchOnGooglePlay(this, Config.KEY_SEARCH_MORE_APP_GOOGLE_PLAY);
                } else {
                    checkSelectMenuLeft = true;
                    mCurrenFragment = MoreAppFragment.newInstance();
                }
                break;

        }
        if (checkSelectMenuLeft) {
            mCurrenFragment.setArguments(bundle);
            if (!tag.equals(getSupportFragmentManager().findFragmentById(R.id.content).getTag())) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.translate_left_in,
                                R.anim.translate_right_out,
                                R.anim.translate_pop_in,
                                R.anim.translate_pop_out)
                        .replace(R.id.content, mCurrenFragment, tag)
                        .commit();
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        AppUtil.hideSoftKeyboard(this);

    }

    @Override
    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            String tag = getSupportFragmentManager().findFragmentById(R.id.content).getTag();
//            if (tag.equals(Constant.FRAGMENT_DETAILS_CATEGORY) || tag.equals(Constant.FRAGMENT_SEARCH)) {
//                super.onBackPressed();
//            } else {
//                DialogUtil.showAlertDialog(this, R.string.app_name, R.string.msg_confirm_exit, new DialogUtil.IDialogConfirm() {
//                    @Override
//                    public void onClickOk() {
//                        DataStoreManager.removeUser();
//                        finish();
//                        overridePendingTransition(0, R.anim.slide_out_left);
//                    }
//                });
//            }
//        }
        super.onBackPressed();
    }

    public void initMenuLeft() {
        layoutMenuLeft = (RelativeLayout) findViewById(R.id.layoutMenuLeft);
        navClose = (TextView) findViewById(R.id.nav_close);
        navClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        mLvMenu = (ListView) findViewById(R.id.lvMenu);
        initMenu();
        changeTheme();
        mLvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIndex = position;
                switchScreen(mArrMenu.get(position).getId());
                if (checkSelectMenuLeft) setSelectedItemMenu(position);
                mMenuLeftAdapter.notifyDataSetChanged();
    }
});
    }

    private void setSelectedItemMenu(int position) {
        if (mArrMenu != null && mArrMenu.size() > 0) {
            for (int i = 0; i < mArrMenu.size(); i++) {
                if (i == position) {
                    mArrMenu.get(i).setSelected(true);
                } else {
                    mArrMenu.get(i).setSelected(false);
                }
            }
        }
    }

    public void initMenu() {
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())) {
            arrImageMenu = new Integer[]{R.drawable.ic_menu_home_white,/* R.drawable.ic_menu_begin_white,
                    R.drawable.ic_menu_intemediate_white, R.drawable.ic_menu_advanced_white,*/
                    R.drawable.ic_menu_favorite_white,
                    R.drawable.ic_menu_download_white,
                    R.drawable.ic_menu_settings_white,/* R.drawable.ic_menu_feedback_white,
                    R.drawable.ic_menu_more_white,*/ R.drawable.ic_menu_info_white};
        } else {
            arrImageMenu = new Integer[]{R.drawable.ic_menu_home_black,/* R.drawable.ic_menu_begin_black,
                    R.drawable.ic_menu_intemediate_black, R.drawable.ic_menu_advanced_black,*/
                    R.drawable.ic_menu_favorite_black, R.drawable.ic_menu_download_black,
                    R.drawable.ic_menu_settings_black, /*R.drawable.ic_menu_feedback_black,
                    R.drawable.ic_menu_more_black, */R.drawable.ic_menu_info_black};
        }
        mArrMenu = new ArrayList<>();

        mArrMenu.add(new MenuLeft(MenuLeft.FRAGMENT_HOME, getString(R.string.home), false));
//        mArrMenu.add(new MenuLeft(MenuLeft.FRAGMENT_BEGIN, getString(R.string.begin), false));
//        mArrMenu.add(new MenuLeft(MenuLeft.FRAGMENT_INTERMEDIATE, getString(R.string.intermediate), false));
//        mArrMenu.add(new MenuLeft(MenuLeft.FRAGMENT_ADVANCE, getString(R.string.advance), false));
        mArrMenu.add(new MenuLeft(MenuLeft.FRAGMENT_FAVOURITE, getString(R.string.favorite), false));
        mArrMenu.add(new MenuLeft(MenuLeft.FRAGMENT_DOWNLOAD, getString(R.string.download), false));
        mArrMenu.add(new MenuLeft(MenuLeft.FRAGMENT_SETTING, getString(R.string.settings), false));
//        mArrMenu.add(new MenuLeft(MenuLeft.FRAGMENT_FEEDBACK, getString(R.string.feedback), false));
//        mArrMenu.add(new MenuLeft(MenuLeft.FRAGMENT_MORE_APP, getString(R.string.more_apps), false));
        mArrMenu.add(new MenuLeft(MenuLeft.FRAGMENT_ABOUT, getString(R.string.about), false));

        mArrMenu.get(mIndex).setSelected(true);
        mMenuLeftAdapter = new MenuLeftAdapter((MainActivity) self, mArrMenu, arrImageMenu);
        mLvMenu.setAdapter(mMenuLeftAdapter);
    }

    public void initTootlbarNavi(boolean values) {
        if (values) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.openDrawer(GravityCompat.START);
                }
            });
            initNavigationView();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 69 && resultCode == RESULT_OK) {
            mCurrenFragment.onActivityResult(requestCode, resultCode, new Intent());
        }


        //Fragmentquestion here
        if (requestCode ==QuestionActivity.REQUEST_CODE ){
            if (resultCode == QuestionActivity.RESULT_CODE_BACK_TO_BOARD){
                Log.e("MainActivity", "onActivityResult RESULT_CODE_BACK_TO_BOARD");
            }

            if (resultCode == DetailActivity.RESULT_WATCH){
                Log.e("MainActivity", "onActivityResult RESULT_WATCH");
            }
        }
    }

    private void changeTheme() {
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())) {
            if (layoutAdmob != null)
                layoutAdmob.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getBackgroundMainLight()));
            navigationView.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getBackgroundMainLight()));
            //layoutMenuLeft.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getColorPrimaryDark()));
            //navClose.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getColorPrimaryDark()));
        } else {
            if (layoutAdmob != null)
                layoutAdmob.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getBackgroundMainDark()));
//            layoutMenuLeft.setBackgroundColor(getResources().getColor(R.color.white));
            navigationView.setBackgroundColor(getResources().getColor(R.color.white));
//            navClose.setBackgroundColor(Color.parseColor(GlobalValue.getTheme().getColorPrimaryDark()));
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            changeTheme();
            //initMenuLeft();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity", "on RESULT_CODE_BACK_TO_BOARD");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        bundle = new Bundle();
        switch (menuItem.getItemId()) {
            case R.id.menunav_home:
                checkSelectMenuLeft = true;
                bundle.putInt(Constant.OPTIONS_FRAGMENT_LIST_LESSON, Constant.MENU_LEFT_HOME);
                bundle.putString(Constant.KEY_TITLE_TOOLBAR, getString(R.string.home));
                setToolbarTitle(getString(R.string.home));
                fragment = HomeFragment.newInstance();
                break;
            case R.id.menunav_favorite:

//                bundle.putInt(Constant.OPTIONS_FRAGMENT_LIST_LESSON, Constant.MENU_LEFT_FAVOURITE);
                bundle.putString(Constant.KEY_TITLE_TOOLBAR, getString(R.string.favorite));
                fragment = FragmentFavorite.newInstance(true);
                setToolbarTitle(getString(R.string.favorite));
                break;
            case R.id.menunav_download:
                bundle.putString(Constant.KEY_TITLE_TOOLBAR, getString(R.string.download));
                setToolbarTitle(getString(R.string.download));
                fragment = FragmentDownload.newInstance(true);
                break;
            case R.id.menunav_setting:
                checkSelectMenuLeft = true;
                fragment = FragmentSettings.newInstance();
                break;
        }
        fragment.setArguments(bundle);

        return loadFragment(fragment);
    }
}
