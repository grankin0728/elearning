package com.suusoft.elistening.configs;

/**
 * Created by Suusoft on 6/14/2016.
 */
public class Constant {


    public class Caching {
        public static final String KEY_REQUEST = "request";
        public static final String KEY_RESPONSE = "response";
        public static final String KEY_TIME_UPDATED = "time_updated";
        public static final String CACHING_PARAMS_TIME_REQUEST = "caching_time_request";
    }

    public static final int TAB_SELECTED_PLAYING = 0;
    public static final int TAB_SELECTED_VOCA = 1;
    public static final int TAB_SELECTED_QUESTION = 2;
    public static final int TAB_SELECTED_PLAY_LIST = 3;

    // key model
    public static final String KEY_BOOK = "KEY_BOOK";
    public static final String KEY_OPEN_LAST_CHAPTER = "KEY_OPEN_LAST_CHAPTER";
    public static final String KEY_CATEGORY = "KEY_CATEGORY";
    public static final String KEY_CHAPTER = "KEY_CHAPTER";
    public static final String KEY_SEARCH = "KEY_SEARCH";
    public static final String KEY_TITLE_TOOLBAR = "KEY_TITLE_TOOLBAR";

    // key sharedPreferences
    public static final String LIST_BOOK_HISTORY = "LIST_BOOK_HISTORY";
    public static final String LIST_BOOK_MARKS = "LIST_BOOK_MARKS";

    // key folder sdcard
    public static final String FOLDER_EBOOK = "/ebook/";
    public static final String FOLDER_PDF = FOLDER_EBOOK + "pdf/";
    public static final String FOLDER_EPUB = FOLDER_EBOOK + "epub/";

    // tag fragment
    public static final String FRAGMENT_DETAILS_BOOK = "FRAGMENT_DETAILS_BOOK";
    public static final String FRAGMENT_CHAPTER = "FRAGMENT_CHAPTER";
    public static final String FRAGMENT_DETAILS_CATEGORY = "FRAGMENT_DETAILS_CATEGORY";
    public static final String FRAGMENT_SEARCH = "FRAGMENT_SEARCH";
    public static final String FRAGMENT_DETAILS_CHAPTER = "FRAGMENT_DETAILS_CHAPTER";
    public static final String FRAGMENT_COMENT = "FRAGMENT_COMENT";

    public static final String OPTIONS_FRAGMENT_LIST_LESSON = "OPTIONS_FRAGMENT_LIST_LESSON";
    public static final int FRAGMENT_HOME       = 0;
    public static final int FRAGMENT_FAVOURITE  = 1;
    public static final int FRAGMENT_DOWNLOAD   = 2;
    public static final int FRAGMENT_SETTING    = 3;
    public static final int FRAGMENT_FEEDBACK   = 4;
    public static final int FRAGMENT_MORE_APP   = 5;
    public static final int FRAGMENT_ABOUT      = 6;

    public static final int MENU_LEFT_HOME = 1;
    public static final int MENU_LEFT_FAVOURITE = 2;
    public static final int MENU_LEFT_DOWNLOAD = 3;
    public static final int MENU_LEFT_SETTING = 4;
    public static final int MENU_LEFT_FEEDBACK = 5;
    public static final int MENU_LEFT_MORE_APP = 6;
    public static final int MENU_LEFT_ABOUT = 7;

    public static final int SEARCH_BOOK = 8;

    public static final String STATUS_BOOK_HOT = "0";
    public static final String STATUS_BOOK_NEW = "1";
    public static final String STATUS_BOOK_MOST = "2";

    public static final String TYPE_ALL = "all";
    public static final String TYPE_ACTION_READ = "read";

    public static final String LISTEN_CHANGE_THEME = "listenChangeTheme";


    //Elistening
    public static final String ACTION_DOWNLOAD = "Download";
    public static final String DOWNLOAD_TITLE = "Downloading";
    public static final String DOWNLOAD_CANCEL = "Cancel";
    public static final String DOWNLOAD_COMPLETE = "Download Complete";
    public static final String DOWNLOAD_FAIL = "Download Fail";

    public static final String FOLDER_APP = "elistening";
    public static final String FOLDER_LESSION = "lession-downloaded";
    public static final String MY_DIR = FOLDER_APP;
    public static final String TYPE_FILE_MP3 = ".mp3";
    public static final String TYPE_FILE_MP4 = ".mp4";
    public static final int TIMEOUT = 15000;
    public static final String LESSON = "Lesson";
    public static final String POS = "pos";

    // Config background
    public static final String TYPE_BACKGROUND_LIGHT = "Light";
    public static final String TYPE_BACKGROUND_DARK = "Dark";

    public static final int DELAY_MILLIS = 1000;
    // Action
    public static final String ACTION_PLAY = "Play";
    public static final String ACTION_PREV = "Prev";
    public static final String ACTION_NEXT = "Next";
    public static final String ACTION_CLOSE = "Close";
    public static final String ACTION_STOP = "Stop";
    public static final String ACTION_REPLAY = "RePlay";
    public static final String ACTION_SEEK = "Seek";
    public static final String ACTION_OPEN = "Open";
    // broadcast
    public static final String BC_TIME = "Time";
    public static final String BC_TIMEALL = "TimeAll";
    public static final String BC_CHANGE = "Change";
    public static final String BC_REPLAY = "RePlay";
    // Key
    public static final String K_POS = "pos";
    public static final String K_DUR = "dur";
    public static final String K_SEEKTO = "seekto";
    public static final String K_REPLAY = "replay";
    // notification id
    public static final int notifyId = 1102;

    public static final String LEVER_BEGIN = "Begin";
    public static final String LEVER_INTERMEDIATE = "Intermediate";
    public static final String LEVER_ADVANCE = "Advance";
    public static final String FAVORITE = "FAVORITE";
    public static final String DOWNLOAD = "DOWNLOAD";
    public static final String ACTION_CANCEL_DOWNLOAD = "CancelDownload";
    public static final int TYPE_B = 0;
    public static final int TYPE_I = 1;
    public static final int TYPE_A = 2;

    //att lesson
    public static final int LESSON_All = 0;
    public static final int LESSON_FAVOR = 1;
    public static final int LESSON_DOWNLOAD = 2;


    //type lesson
    public static final String LEVEL_LESSON_BEGINING = "beginning";
    public static final String LEVEL_LESSON_INTERMEDIATE = "intermediate";
    public static final String LEVEL_LESSON_ADVANCED = "advanced";
}
