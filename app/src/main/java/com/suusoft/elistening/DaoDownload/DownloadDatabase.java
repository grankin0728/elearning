package com.suusoft.elistening.DaoDownload;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities={DownloadList.class},version = 1)
public abstract class DownloadDatabase extends RoomDatabase {
    private static DownloadDatabase database;

    public static DownloadDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(
                    context,
                    DownloadDatabase.class,
                    "downloaddb"
            )
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }

    public abstract DownloadDao downloadDao();
}
