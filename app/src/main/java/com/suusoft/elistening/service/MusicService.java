package com.suusoft.elistening.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.suusoft.elistening.AppController;
import com.suusoft.elistening.DaoDownload.DownloadList;
import com.suusoft.elistening.R;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.model.modelLesson.WatchLesson;
import com.suusoft.elistening.view.activity.DetailActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.IOException;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener

{
    private RemoteViews views, bigViews;
    private NotificationCompat.Builder builder;
    private static MediaPlayer player;
    private Handler handler;
    public static PlayState playState = PlayState.Stopped;
    private ReplayState replayState = ReplayState.ReplayAll;


    public enum PlayState {Playing, Pause, Stopped, Prepairing}

    public enum ReplayState {ReplayAll, ReplayOne}

    public MusicService() {

    }

    public static MediaPlayer getMediaPlayer(){
        return player;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
//        playState = PlayState.Stopped;
//        replayState = ReplayState.ReplayAll;
//        handler = new Handler();
//        Log.e("MusicService", "onCreate");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            showNotification();
//
//        }else {
//            startForeground(1, new Notification());
//        }
        playState = PlayState.Stopped;
        replayState = ReplayState.ReplayAll;
        handler = new Handler();
        Log.e("MusicService", "onCreate");
        //showNotification();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = "";
        if (intent != null) action = intent.getAction();
        switch (action) {
            case Constant.ACTION_PLAY:
                try {
                    processPlay();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.ACTION_PREV:
                try {
                    processPrev();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.ACTION_NEXT:
                try {
                    processNext();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.ACTION_CLOSE:
                processClose();
                break;
            case Constant.ACTION_STOP:
                processStop();
                break;
            case Constant.ACTION_REPLAY:
                processRePlay();
                break;
            case Constant.ACTION_SEEK:
                processSeek(intent);
                break;
            case Constant.ACTION_OPEN:
                try {
                    processOpen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        updateNotification();
        return START_STICKY;
    }

    private void processRePlay() {
        if (replayState == ReplayState.ReplayAll) {
            replayState = ReplayState.ReplayOne;
        } else replayState = ReplayState.ReplayAll;
        sendBroadcastToFG(Constant.BC_REPLAY);
    }

    private void processOpen() throws IOException {
        playState = PlayState.Stopped;
        sendBroadcastToFG(Constant.BC_CHANGE);
        updateNotification();
        if (handler != null) stopSendTime();
        if (player != null) {
            player.reset();
            player.release();
            player = null;
        }
        Lesson lesson = AppController.getInstance().getArrLessons().get(AppController.getInstance().getLessonIndex());
        //DownloadList downloadList = AppController.getInstance().getArrDownloads().get(AppController.getInstance().getLessonIndex());
        WatchLesson watchLesson = DataStoreManager.getWatchLesson(lesson.getId());
//        if (DataStoreManager.isDownload(lesson)) {
        if (DataStoreManager.getWatchLesson(lesson.getId()).isDownload()) {
//            String path = Environment.getExternalStorageDirectory()
//                    + File.separator + Constant.MY_DIR
//                    + File.separator + lesson.getId() + ".mp3";
            String path = Environment.getExternalStorageDirectory()
                    + File.separator + Constant.MY_DIR
                    + File.separator + DownloadService.getNameFileMp3(lesson.getName(), lesson.getId());
            File file = new File(path);
            if (file.exists()) {
                Uri uri = Uri.parse(path);
                setResource(uri);
            } else {
//                lesson.setDownload(false);
//                DataStoreManager.updateDownload(lesson);
                watchLesson.setDownload(false);
                DataStoreManager.updateDownloadLesson(watchLesson);

                checkResource(lesson);
            }
        } else {

            checkResource(lesson);
//            setResource(R.raw.until_you);
        }
//        else if (downloadList != null) {
//            WatchLesson watchLesson = DataStoreManager.getWatchLesson(lesson.getId());
//            if (DataStoreManager.getWatchLesson(downloadList.getId()).isDownload()) {
//                String path = Environment.getExternalStorageDirectory()
//                        + File.separator + Constant.MY_DIR
//                        + File.separator + DownloadService.getNameFileMp3(lesson.getName(), lesson.getId());
//                File file = new File(path);
//                if (file.exists()) {
//                    Uri uri = Uri.parse(path);
//                    setResource(uri);
//                } else {
//                    watchLesson.setDownload(false);
//                    DataStoreManager.updateDownloadLesson(watchLesson);
//
//                    checkResources(downloadList);
//                }
//            } else {
//
//                checkResources(downloadList);
//            }
//        }

        //setUpNotification(lesson);
    }

    private void checkResource(Lesson lesson) throws IOException {
        if (isExistFileMp3(lesson.getAttachment())){
            setResource(lesson.getAttachment());
            Log.e("url audio","Play getUrlAudio = " +lesson.getImage() );
        }else if (isExistFileMp3(lesson.getAttachment())){
            setResource(lesson.getAttachment());
            Log.e("url audio","Play getLink_url = " +lesson.getAttachment() );
        }
    }

    private void checkResources(DownloadList lesson) throws IOException {
        if (isExistFileMp3(lesson.getAttachment())){
            setResource(lesson.getAttachment());
            Log.e("url audio","Play getUrlAudio = " +lesson.getImage() );
        }else if (isExistFileMp3(lesson.getAttachment())){
            setResource(lesson.getAttachment());
            Log.e("url audio","Play getLink_url = " +lesson.getAttachment() );
        }
    }


    public boolean isExistFileMp3(String s){
        if (!s.equals("") && s.endsWith(".mp3"))
            return true;
        else return false;
    }


    private void processSeek(Intent i) {
        if (player != null && playState != PlayState.Prepairing) {
            Bundle b = i.getExtras();
            player.seekTo(b.getInt(Constant.K_SEEKTO));
        }
    }

    private void processStop() {
        playState = PlayState.Stopped;
        if (player != null) player.stop();
        closeNotify();
    }

    private void processClose() {
        playState = PlayState.Stopped;
        if (player != null) {
            sendBroadcastToFG(Constant.BC_TIMEALL);
            sendBroadcastToFG(Constant.BC_TIME);
        }
        closeNotify();
        stopSelf();
    }

    private void processNext() throws IOException {
        int index = AppController.getInstance().getLessonIndex();
        if (index < AppController.getInstance().getArrLessons().size() - 1) {
            AppController.getInstance().setLessonIndex(index + 1);
            processOpen();
        }
    }

    public void processPrev() throws IOException {
        int index = AppController.getInstance().getLessonIndex();
        if (index > 0) {
            AppController.getInstance().setLessonIndex(index - 1);
            processOpen();
        }
    }

    private void processPlay() throws IOException {
        if (player != null) {
            if (playState != PlayState.Prepairing) {
                if (player.isPlaying()) {
                    playState = PlayState.Pause;
                    player.pause();
                } else if (playState != PlayState.Stopped) {
                    playState = PlayState.Playing;
                    player.start();
                } else {
                    processOpen();
                }
            } else Toast.makeText(this, "Wait to load media", Toast.LENGTH_SHORT).show();
        } else processOpen();
        updateNotification();
    }

    private void sendBroadcastToFG(String action) {
        Intent i = new Intent();
        i.setAction(action);
        Bundle b = new Bundle();
        switch (action) {
            case Constant.BC_TIME:
                int pos = player.getCurrentPosition();
                b.putInt(Constant.K_POS, pos);
                break;
            case Constant.BC_TIMEALL:
                int dur = player.getDuration();
                b.putInt(Constant.K_DUR, dur);
                break;
            case Constant.BC_REPLAY:
                b.putSerializable(Constant.K_REPLAY, replayState);
                break;
        }
        i.putExtras(b);
        sendBroadcast(i);
    }

    private void startMedia() {
        if (!player.isPlaying()) {
            playState = PlayState.Playing;
            player.start();
            startSendTime();
        }
    }

    private void startSendTime() {
        handler.postDelayed(sendTime, Constant.DELAY_MILLIS);
    }

    private void stopSendTime() {
        handler.removeCallbacks(sendTime);
    }

    private Runnable sendTime = new Runnable() {
        @Override
        public void run() {
            sendBroadcastToFG(Constant.BC_TIME);
            sendBroadcastToFG(Constant.BC_TIMEALL);
            handler.postDelayed(sendTime, Constant.DELAY_MILLIS);
        }
    };

    private void closeNotify() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(Constant.notifyId);
    }

    private void updateNotification() {
        views = new RemoteViews(getPackageName(), R.layout.layout_notify);
        bigViews = new RemoteViews(getPackageName(), R.layout.layout_notify_expand);
        if (builder != null) {
            if (playState == PlayState.Playing) {
                views.setImageViewResource(R.id.btn_play, R.drawable.ic_black_pause);
                bigViews.setImageViewResource(R.id.btn_play, R.drawable.ic_black_pause);
            } else {
                views.setImageViewResource(R.id.btn_play, R.drawable.ic_black_play);
                bigViews.setImageViewResource(R.id.btn_play, R.drawable.ic_black_play);
            }
            builder.setCustomContentView(views);
            builder.setCustomBigContentView(bigViews);
            startForeground(Constant.notifyId, builder.build());
        }
    }

    private void setUpNotification(Lesson lesson) {
        views = new RemoteViews(getPackageName(), R.layout.layout_notify);
        bigViews = new RemoteViews(getPackageName(), R.layout.layout_notify_expand);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (builder != null) {
            Picasso.with(getApplicationContext()).load(lesson.getImage()).error(R.mipmap.ic_launcher).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    bigViews.setImageViewBitmap(R.id.img_lesson, bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    bigViews.setImageViewResource(R.id.img_lesson, R.mipmap.ic_launcher);
                }
            });
            Picasso.with(getApplicationContext()).load(lesson.getImage()).error(R.mipmap.ic_launcher).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    views.setImageViewBitmap(R.id.img_lesson, bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    views.setImageViewResource(R.id.img_lesson, R.mipmap.ic_launcher);
                }
            });
            bigViews.setTextViewText(R.id.txt_lessontitle, lesson.getName());
            views.setTextViewText(R.id.txt_lessontitle, lesson.getName());
            builder.setCustomContentView(views);
            builder.setCustomBigContentView(bigViews);
            startForeground(Constant.notifyId, builder.build());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotification() {
        views = new RemoteViews(getPackageName(), R.layout.layout_notify);
        bigViews = new RemoteViews(getPackageName(), R.layout.layout_notify_expand);
        Intent playItent = new Intent(this, MusicService.class);
        playItent.setAction(Constant.ACTION_PLAY);
        PendingIntent pdPlay = PendingIntent.getService(this, 0, playItent, 0);
        views.setOnClickPendingIntent(R.id.btn_play, pdPlay);
        bigViews.setOnClickPendingIntent(R.id.btn_play, pdPlay);

        Intent prevItent = new Intent(this, MusicService.class);
        prevItent.setAction(Constant.ACTION_PREV);
        PendingIntent prevdPlay = PendingIntent.getService(this, 0, prevItent, 0);
        views.setOnClickPendingIntent(R.id.btn_prev, prevdPlay);
        bigViews.setOnClickPendingIntent(R.id.btn_prev, prevdPlay);

        Intent nextItent = new Intent(this, MusicService.class);
        nextItent.setAction(Constant.ACTION_NEXT);
        PendingIntent ndIntent = PendingIntent.getService(this, 0, nextItent, 0);
        views.setOnClickPendingIntent(R.id.btn_next, ndIntent);
        bigViews.setOnClickPendingIntent(R.id.btn_next, ndIntent);

        Intent closeIntent = new Intent(this, MusicService.class);
        closeIntent.setAction(Constant.ACTION_CLOSE);
        PendingIntent clIntent = PendingIntent.getService(this, 0, closeIntent, 0);
        views.setOnClickPendingIntent(R.id.btn_close, clIntent);
        bigViews.setOnClickPendingIntent(R.id.btn_close, clIntent);

        String NOTIFICATION_CHANNEL_ID = "com.suusoft.elistening";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

//        builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
//        Notification notification = builder.setOngoing(true)
//                .setSmallIcon(R.drawable.ic_headset)
//                .setContentTitle("App is running in background")
//                .setPriority(NotificationManager.IMPORTANCE_MIN)
//                .setCategory(Notification.CATEGORY_SERVICE)
//                .build();
//        startForeground(2, notification);

        builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder.setCustomContentView(views);
        builder.setCustomBigContentView(bigViews);
        builder.setSmallIcon(R.drawable.ic_headset);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(false).setOngoing(true).setDeleteIntent(pendingIntent);
        startForeground(Constant.notifyId, builder.build());
    }

    public void setResource(String url) throws IOException {
        Log.e("setResource", "setURL " + url);
        if (url != null) {
            playState = PlayState.Prepairing;
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(url);
            player.prepareAsync();
            player.setOnPreparedListener(this);
            player.setOnCompletionListener(this);
            player.setOnErrorListener(this);
        }
    }


    /*dùng để test khi mạng kém*/
    public void setResource(int path) throws IOException {
        Log.e("", "set raw");
        if (path !=0) {
            playState = PlayState.Prepairing;
            player = MediaPlayer.create(getApplicationContext(), path);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            player.prepareAsync();
            player.setOnPreparedListener(this);
            player.setOnCompletionListener(this);
            player.setOnErrorListener(this);
        }
    }

    public void setResource(Uri uri) throws IOException {
        Log.e("", "setURI");
        playState = PlayState.Prepairing;
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDataSource(this.getApplicationContext(), uri);
        player.prepare();
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
        startMedia();
        updateNotification();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playState = PlayState.Stopped;
        if (replayState == ReplayState.ReplayOne) {
            if (player != null) player.start();
            playState = PlayState.Playing;
        } else try {
            processNext();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateNotification();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        startMedia();
        updateNotification();
    }

    @Override
    public void onDestroy() {
        closeNotify();
        playState = PlayState.Stopped;
        stopSendTime();
        if (player != null) {
            player.release();
            player = null;
        }
        super.onDestroy();
    }

    public static void intentStart(Context activity, String action){
        Intent i = new Intent(activity, MusicService.class);
        i.setAction(action);
        activity.startService(i);
    }

}