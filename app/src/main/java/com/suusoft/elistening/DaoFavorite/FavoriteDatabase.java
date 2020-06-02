package com.suusoft.elistening.DaoFavorite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.suusoft.elistening.model.modelLesson.Lesson;

@Database(entities={Lesson.class},version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {
    private static FavoriteDatabase database;

    public static FavoriteDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(
                    context,
                    FavoriteDatabase.class,
                    "myfavdb"
            )
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }

    public abstract FavoriteDao favoriteDao();

}
