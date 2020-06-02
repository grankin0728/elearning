package com.suusoft.elistening.datastore.db;

public interface IDatabaseConfig {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "db.sqlite";
	public static final String TABLE_CACHING = "caching";

	//table CATEGORY
	public static final String TABLE_CATEGORY = "CATEGORY";
	public static final String TABLE_CATEGORY_COLUMN_ID = "ID";
	public static final String TABLE_CATEGORY_COLUMN_IMAGE = "IMAGE";
	public static final String TABLE_CATEGORY_COLUMN_NAME = "NAME";
	public static final String TABLE_CATEGORY_COLUMN_DESCRIPTION = "DESCRIPTION";

	//column english
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_LEVEL = "LEVEL";
	public static final String COLUMN_TITLE = "NAME";
	public static final String COLUMN_OVERVIEW = "OVERVIEW";
	public static final String COLUMN_DOWNLOAD = "DOWNLOAD";
	public static final String COLUMN_FAVORITE = "FAVORITE";
	public static final String COLUMN_VIEW = "VIEW";
	public static final String COLUMN_URL_IMAGE = "IMAGE";
	public static final String COLUMN_URL_AUDIO = "AUDIO";
	public static final String COLUMN_LINK_URL = "ATTACHMENT";
	public static final String COLUMN_CATEGORY_ID = "CATEGORY_ID";
	public static final String COLUMN_CONTENTS = "CONTENTS";
	public static final String COLUMN_TYYPE = "TYPE";
	public static final String COLUMN_QUESTION = "QUESTION";
	public static final String COLUMN_COMPLETE_PERCENT = "COMPLETE_PERCENT";
	public static final String COLUMN_NUMBER_OF_CORRECT_ANSWERS = "NUMBER_OF_CORRECT_ANSWERS";



	//table watch
	public static final String TABLE_WATCH = "watch";
	public static final String TABLE_WATCH_COLUMN_ID = "id";
	public static final String TABLE_WATCH_COLUMN_VIEW = "view";
	public static final String TABLE_WATCH_COLUMN_DOWNLOAD = "download";
	public static final String TABLE_WATCH_COLUMN_FAVORITE = "favorite";
	public static final String TABLE_WATCH_COLUMN_COMPLETE_PERCENT = "complete_percent";
	public static final String TABLE_WATCH_COLUMN_NUMBER_OF_CORRECT_ANSWERS = "number_of_correct_answers";



	public static final String TABLE_ENGLISH = "ENGLISH";
	public final String CREATE_TABLE = "CREATE TABLE "+ TABLE_ENGLISH + " ( "+
			COLUMN_ID+ " INTEGER PRIMARY KEY,"+
			COLUMN_LEVEL + " INTEGER,"+
			COLUMN_TITLE+ " TEXT,"+
			COLUMN_OVERVIEW + " TEXT,"+
			COLUMN_DOWNLOAD+ " INTEGER,"+
			COLUMN_FAVORITE + " INTEGER,"+
			COLUMN_VIEW+ " INTEGER,"+
			COLUMN_URL_AUDIO+ " TEXT,"+
			COLUMN_URL_IMAGE+ " TEXT,"+
			COLUMN_CONTENTS + " TEXT,";
	public final String DELETE_TABLE = "DROP TABLE IF EXISTS "+ TABLE_ENGLISH;

}
