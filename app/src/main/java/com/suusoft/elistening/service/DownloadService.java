package com.suusoft.elistening.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.util.Log;

import com.suusoft.elistening.AppController;
import com.suusoft.elistening.R;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.datastore.DataStoreManager;
import com.suusoft.elistening.listener.DataBroadcastReceiver;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.model.modelLesson.WatchLesson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DownloadService extends IntentService {
    int notifyID = 1;
    String CHANNEL_ID = "my_channel_01";
    final static String TAG = DownloadService.class.getSimpleName();


    public DownloadService() {
        super("DownloadService");
    }

    public interface IListenerDownloadComplete{
        public void onCompleteDownload();
    }


    private ArrayList<IListenerDownloadComplete> iListenerDownloadCompletes = new ArrayList<>();

    public void addIListenerDownloadComplete(IListenerDownloadComplete iListenerDownloadComplete) {
        this.iListenerDownloadCompletes.add(iListenerDownloadComplete) ;
    }

    public void notifiCompleteDownload(){
        for (IListenerDownloadComplete iListenerDownloadComplete: iListenerDownloadCompletes)
            iListenerDownloadComplete.onCompleteDownload();
    }

    public static boolean dl_continue;

    public static String getNameFileMp3(String titleLesson, int id){
        Log.e(TAG, "getNameFileMp3: "+titleLesson );
        return titleLesson.replace(" ","-") + "-" +  id + Constant.TYPE_FILE_MP3;

    }

    public static String getNameFileMp4(String titleLesson, int id){
        Log.e(TAG, "getNameFileMp4: "+titleLesson );
        return titleLesson.replace(" ","-") + "-" +  id + Constant.TYPE_FILE_MP4;

    }

    @Override
    public void onCreate() {
        super.onCreate();



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            processingDownloadLesson(intent);

        }
    }

    public boolean isExist(String s){
        if (!s.equals(""))
            return true;
        else return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processingDownloadLesson(Intent intent) {
        Bundle b = intent.getExtras();
        Lesson lesson = b.getParcelable(Constant.LESSON);
        WatchLesson watchLesson = DataStoreManager.getWatchLesson(lesson.getId());
        String urlAudio =  "";
        if (isExist(lesson.getAttachment())){
            urlAudio = lesson.getAttachment();
            Log.e("url audio","getUrlAudio = " +lesson.getImage() );
        } else if (isExist(lesson.getAttachment())){
            urlAudio = lesson.getAttachment();
            Log.e("url audio","getLink_url = " +lesson.getAttachment() );
        }
        String title = lesson.getName();
        int id = lesson.getId();
        if (isMounted()) {
            //SHOW notification downloading
            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            NotificationCompat.Builder builder =  new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .setContentTitle(Constant.DOWNLOAD_TITLE)
                    .setSmallIcon(android.R.drawable.stat_sys_download)
                    .setContentText(title)
                    .setContentIntent(PendingIntent.getService(this, 0, new Intent(), 0));

            Intent cancelIntent = new Intent(this, DataBroadcastReceiver.class);
            cancelIntent.setAction(Constant.ACTION_CANCEL_DOWNLOAD);
            builder.addAction(R.drawable.ic_stop, Constant.DOWNLOAD_CANCEL , PendingIntent.getBroadcast(this, 0, cancelIntent, 0));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel();
            }
            manager.notify(id, builder.build());

            //create path folder contain file .mp3
            String uriStorage = Environment.getExternalStorageDirectory() + File.separator + Constant.MY_DIR;
            new File(uriStorage).mkdirs();
            File file = new File(uriStorage, getNameFileMp3(lesson.getName(), id));
//            File f = new File(uriStorage);
//            File[] files = f.listFiles();
            Log.e(TAG, "processingDownloadLesson: "+ file );
            if (file.exists())
                file.delete();

            try {

                URL url = new URL(urlAudio);
                URLConnection connection = url.openConnection();
                connection.setConnectTimeout(Constant.TIMEOUT);
                connection.connect();

                //read and write file mp3 from sever url to folder
                int fileLength = connection.getContentLength();
                InputStream inputStream = new BufferedInputStream(url.openStream());
                OutputStream outputStream = new FileOutputStream(file);
                int read;
                int current = 0;
                int tmp = 0;
                int count = 0;
                byte[] bytes = new byte[2048];
                dl_continue = true;
                while ((read = inputStream.read(bytes)) != -1) {
                    current += read;
                    if (!dl_continue) {
                        outputStream.flush();
                        outputStream.close();
                        inputStream.close();
                        if (file.exists()) file.delete();
                        manager.cancel(id);
                        return;
                    }
                    outputStream.write(bytes, 0, read);
                    count++;
                    if (100 * (current - tmp) / fileLength > 2) {
                        builder.setProgress(100, 100 * current / fileLength, false);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            createNotificationChannel();
                        }
                        manager.notify(id, builder.build());
                        tmp = current;
                    }
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
//                lesson.setDownload(true);
//                DataStoreManager.updateDownload(lesson);
                watchLesson.setDownload(true);
                DataStoreManager.updateDownloadLesson(watchLesson);
                AppController.getInstance().removeDlIndex(lesson.getId());

                //SHOW notification download complete
                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, CHANNEL_ID);
                builder1.setAutoCancel(true)
                        .setSmallIcon(android.R.drawable.stat_sys_download_done)
                        .setContentTitle(Constant.DOWNLOAD_COMPLETE)
                        .setContentText(title);
                builder1.setContentIntent(PendingIntent.getService(this, 0, new Intent(), 0));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotificationChannel();
                }

                manager.notify(id, builder1.build());

            } catch (Exception e) {
                Log.e("Download", e.toString());
                AppController.getInstance().removeDlIndex(lesson.getId());

                //SHOW notification download fail
                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, CHANNEL_ID);
                builder1.setAutoCancel(true)
                        .setSmallIcon(android.R.drawable.stat_sys_download_done)
                        .setContentTitle(Constant.DOWNLOAD_FAIL)
                        .setContentText(title);
                builder1.setContentIntent(PendingIntent.getService(this, 0, new Intent(), 0));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotificationChannel();
                }
                manager.notify(id, builder1.build());
            }


        } else {// not mounted
        }
    }


    public boolean isMounted() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationManager
                mNotificationManager =
                (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);
        // The id of the channel.
        String id = CHANNEL_ID;
        // The user-visible name of the channel.
        CharSequence name = "Notification";
        // The user-visible description of the channel.
        String description = "Notification Application";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        // Configure the notification channel.
        mChannel.setDescription(description);
        mChannel.setShowBadge(false);
        mNotificationManager.createNotificationChannel(mChannel);
    }
}
