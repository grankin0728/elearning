package com.suusoft.elistening.base.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.network.BaseRequest;
import com.suusoft.elistening.util.AppUtil;

/**
 * Created by Suusoft on 7/10/2016.
 */
public abstract class AbstractActivity extends AppCompatActivity implements BaseRequest.ListenerLoading {


    public enum ToolbarType {
        MENU_LEFT, // get menu left
        NAVI,    // get button back
        NONE  , // none
        NORMAL
    }

    protected Context self;

    protected FrameLayout contentLayout;
    // toolbar
    protected Toolbar toolbar;
    protected TextView tvTitle;

    protected ProgressBar progressBar;

    protected abstract ToolbarType getToolbarType();

    protected abstract void getExtraData(Intent intent);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        getExtraData(getIntent());
        setView();
        initViewBase();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter(Constant.LISTEN_CHANGE_THEME));
    }

    private void setView() {
        if (getToolbarType() == ToolbarType.MENU_LEFT) {
            setContentView(R.layout._base_drawer);
            initToolbar();
        } else if (getToolbarType() == ToolbarType.NAVI) {
            setContentView(R.layout._base_frame);
            initToolbar();
        } else if (getToolbarType() == ToolbarType.NONE) {
            setContentView(R.layout.base_content);
        }else if (getToolbarType() == ToolbarType.NORMAL){
            setContentView(R.layout._base_frame);
            initToolbarNomal();
        }
    }

    private void initViewBase() {
        contentLayout = (FrameLayout) findViewById(R.id.content_main);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        BaseRequest.getInstance().setListenerLoading(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseRequest.getInstance().setListenerLoading(this);

    }

    public void showProgress(boolean isShow) {
        if (isShow) {
            if (!progressBar.isShown())
                progressBar.setVisibility(View.VISIBLE);
        } else {
            if (progressBar.isShown())
                progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * initialize toolbar
     */

    protected void initToolbarNomal(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor(DataStoreManager.getTheme().getColorPrimary()));
        toolbar.setTitleTextColor(Color.parseColor(DataStoreManager.getTheme().getColorTabSelected_dark()));
        tvTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        tvTitle.setSelected(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor(DataStoreManager.getTheme().getColorPrimary()));
        tvTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        tvTitle.setSelected(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        if (getToolbarType() == ToolbarType.NAVI) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * set title for toolbar
     */
    public void setToolbarTitle(String title) {
        tvTitle.setText(title);
    }

    public void setToolbarTitle(int title) {
        tvTitle.setText(getString(title));
    }

    /**
     * start activity
     *
     * @param clz class name
     */
    public void startActivity(Class<?> clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * show toast message
     *
     * @param message
     */
    public void showToast(String message) {
        AppUtil.showToast(self, message);
    }

    public void showToast(int message) {
        AppUtil.showToast(self, getString(message));
    }

    /**
     * show snack bar message
     *
     * @param message
     */
    public void showSnackBar(int message) {
        Snackbar.make(contentLayout, getString(message), Snackbar.LENGTH_LONG).show();
    }

    public void showSnackBar(String message) {
        Snackbar.make(contentLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onLoadingIsProcessing() {
        showProgress(true);
    }

    @Override
    public void onLoadingIsCompleted() {
        showProgress(false);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (toolbar!=null)
                toolbar.setBackgroundColor(Color.parseColor(DataStoreManager.getTheme().getColorPrimary()));
        }
    };
}
