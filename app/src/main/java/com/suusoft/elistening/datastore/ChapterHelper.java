package com.suusoft.elistening.datastore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ha on 5/2/2016.
 */
public class ChapterHelper extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME = "EBOOK";
    private static final int VERSION_DATA_BASE = 1;

    public static final String TABLE_CHAPTER = "CHAPTER";
    public static final String COL_BOOK_CHAPTER_ID = "ID";
    public static final String COL_INDEX = "INDEXCHAPTER";

    public static final String CREATE_TABLE_STUDENT =
            "CREATE TABLE " + TABLE_CHAPTER
                    + " (" + COL_BOOK_CHAPTER_ID + " TEXT, "
                    + COL_INDEX + " TEXT NOT NULL);";


    public ChapterHelper(Context context) {
        super(context, DATA_BASE_NAME, null, VERSION_DATA_BASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_CHAPTER);
        }
//        onCreate(db);
    }
}
