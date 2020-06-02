package com.suusoft.elistening.widgets.mycustomview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.suusoft.elistening.R;
import com.suusoft.elistening.configs.Config;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.configs.GlobalValue;
import com.suusoft.elistening.databinding.HmediaplayerVmBinding;
import com.suusoft.elistening.service.MusicService;
import com.suusoft.elistening.util.AppUtil;
import com.suusoft.elistening.view.adapter.AdapterTranscript;
import com.suusoft.elistening.viewmodel.layout.MyMediaplayerVM;


/**
 * Created by ASUS on 3/23/2017.
 */

public class MyMediaPlayer extends RelativeLayout implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private MyMediaplayerVM viewModel;
    private LinearLayout llMediaPlayer;
    private ImageButton btnPlay, btnPrev, btnNext, btnStop, btnReplay;
    private SeekBar sbTime;
    private TextView txtTimecurent, txtTimetotal;
    private Context mContext;
    private AdapterTranscript.IClickSeekbar iClickSeekbar;
    private HmediaplayerVmBinding hmPlayerBinding;
    private IListenerHidePlayer iListenerHidePlayer;

    public void setiListenerHidePlayer(IListenerHidePlayer iListenerHidePlayer) {
        this.iListenerHidePlayer = iListenerHidePlayer;
    }

    public interface IListenerHidePlayer{
        void onHide();
    }

    public void setIclickSeekbar(AdapterTranscript.IClickSeekbar iclickSeekbar){
        this.iClickSeekbar = iclickSeekbar;
    }

    public SeekBar getSeekBar(){
        return sbTime;
    }

    public MyMediaPlayer(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }


    public MyMediaPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();

    }

    private void initView() {
        viewModel = new MyMediaplayerVM(mContext);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
//            hmPlayerBinding = DataBindingUtil.inflate(inflater, R.layout.hmediaplayer_vm, null,false);
//            hmPlayerBinding.setViewModel(viewModel);
            inflater.inflate(R.layout.hmediaplayer, this);
            llMediaPlayer = (LinearLayout) findViewById(R.id.llMediaPlayer);
            btnPlay = (ImageButton) findViewById(R.id.btn_play);
            btnNext = (ImageButton) findViewById(R.id.btn_next);
            btnPrev = (ImageButton) findViewById(R.id.btn_prev);
            btnReplay = (ImageButton) findViewById(R.id.btn_replay);
            btnStop = (ImageButton) findViewById(R.id.btn_stop);
            sbTime = (SeekBar) findViewById(R.id.sbtimeplay);
            txtTimecurent = (TextView) findViewById(R.id.txt_timecurent);
            txtTimetotal = (TextView) findViewById(R.id.txt_timetotal);
            btnPlay.setOnClickListener(this);
            btnPrev.setOnClickListener(this);
            btnNext.setOnClickListener(this);
            btnStop.setOnClickListener(this);
            btnReplay.setOnClickListener(this);
            sbTime.setOnSeekBarChangeListener(this);

            setImageDrawbleForViewUnderThem();
        }
    }

    private void setImageDrawbleForViewUnderThem() {
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())){
            btnReplay.setImageDrawable(getResources().getDrawable(R.drawable.ic_replay_all));
            btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
            btnPrev.setImageDrawable(getResources().getDrawable(R.drawable.ic_prev));
            btnNext.setImageDrawable(getResources().getDrawable(R.drawable.ic_next));
            btnStop.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop));
            txtTimecurent.setTextColor(mContext.getResources().getColor(R.color.whitetext));
            txtTimetotal.setTextColor(mContext.getResources().getColor(R.color.whitetext));
            sbTime.setThumb(mContext.getResources().getDrawable(R.drawable.scrubber_control_dark));
            sbTime.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.ic_progress_dark));

        }else {
            btnReplay.setImageDrawable(getResources().getDrawable(R.drawable.ic_replay_all_black));
            btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_black));
            btnPrev.setImageDrawable(getResources().getDrawable(R.drawable.ic_prev_black));
            btnNext.setImageDrawable(getResources().getDrawable(R.drawable.ic_next_black));
            btnStop.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop_black));
            txtTimecurent.setTextColor(mContext.getResources().getColor(R.color.black));
            txtTimetotal.setTextColor(mContext.getResources().getColor(R.color.black));
            sbTime.setThumb(mContext.getResources().getDrawable(R.drawable.scrubber_control_light));
            sbTime.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.ic_progress_light));

        }
    }


    @Override
    public void onClick(View v) {
        Intent i = new Intent(mContext, MusicService.class);
        if (v == btnPlay) {
//            if (MusicService.getMediaPlayer().isPlaying()){
//                iListenerHidePlayer.onHide();
//            }
            i.setAction(Constant.ACTION_PLAY);
            mContext.startService(i);
        } else if (v == btnNext) {
            i.setAction(Constant.ACTION_NEXT);
//            mContext.startService(i);
            AppUtil.showToast(getContext(), "No item");
        } else if (v == btnPrev) {
            i.setAction(Constant.ACTION_STOP);
            mContext.startService(i);
//            i.setAction(Constant.ACTION_PREV);
//            mContext.startService(i);
        } else if (v == btnReplay) {
            i.setAction(Constant.ACTION_REPLAY);
            mContext.startService(i);
        } else if (v == btnStop) {
            i.setAction(Constant.ACTION_STOP);
            mContext.startService(i);
        }
    }

    public void setReplayAll(boolean s) {
//        viewModel.setReplayAll(s);
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())){
            if (s) btnReplay.setImageDrawable(getResources().getDrawable(R.drawable.ic_replay_all));
            else btnReplay.setImageDrawable(getResources().getDrawable(R.drawable.ic_replay_one));
        }else {
            if (s) btnReplay.setImageDrawable(getResources().getDrawable(R.drawable.ic_replay_all_black));
            else btnReplay.setImageDrawable(getResources().getDrawable(R.drawable.ic_replay_one_black));
        }
    }

    public void setTimeCurrent(int time) {
        sbTime.setProgress(time);
        txtTimecurent.setText(timeToString(time));
    }

    public void setTxtTimetotal(int time) {
        sbTime.setMax(time);
        txtTimetotal.setText(timeToString(time));
    }

    public void setPlay() {
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())){
            btnPlay.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_play));
        }else {
            btnPlay.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_play_black));
        }
    }

    public void setPause() {
        if (Config.TYPE_BACKGROUND_DARK.equals(GlobalValue.getTypeBackgroundApp())){
            btnPlay.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_pause));
        }else {
            btnPlay.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_pause_black));
        }
    }

    private String timeToString(int time) {
        int min = time / 60000;
        int sec = time % 60000 / 1000;
        return min + ":" + String.format("%02d", sec);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        Log.e("MyMediaPlayer", " onProgressChanged " );
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.e("MyMediaPlayer", " onStartTrackingTouch " );
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.e("MyMediaPlayer", " onStopTrackingTouch " );
        if (MusicService.playState != null
                && (MusicService.playState == MusicService.PlayState.Playing
                || MusicService.playState == MusicService.PlayState.Pause)) {
            Intent i = new Intent(mContext, MusicService.class);
            Bundle b = new Bundle();
            i.setAction(Constant.ACTION_SEEK);
            b.putInt(Constant.K_SEEKTO, seekBar.getProgress());
            if (iClickSeekbar!=null)iClickSeekbar.onClickSeekbar(seekBar.getProgress());
            i.putExtras(b);
            mContext.startService(i);
        }

    }

}
