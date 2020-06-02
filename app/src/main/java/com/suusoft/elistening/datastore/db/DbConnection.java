package com.suusoft.elistening.datastore.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.suusoft.elistening.configs.Constant;
import com.suusoft.elistening.model.modelLesson.Content;
import com.suusoft.elistening.model.modelLesson.Lesson;
import com.suusoft.elistening.model.modelLesson.QuestionAnswer;
import com.suusoft.elistening.model.modelLesson.Vocabulary;
import com.suusoft.elistening.model.modelLesson.WatchLesson;
import com.suusoft.elistening.util.ParserUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Class connects to Database, get all data here
 *
 *
 */
public class DbConnection implements IDatabaseConfig {

	private Context mContext;
	private DataBaseHelper mDBHelper;

	public DbConnection(Context mContext) {
		this.mContext = mContext;
		this.mDBHelper = new DataBaseHelper(this.mContext, DATABASE_NAME);
	}

	/**
	 * close database
	 */
	private void close() {
		mDBHelper.close();
	}

	/**
	 * open and connect to database
	 */
	private void openAndConnectDB() {
		try {
			mDBHelper.createDataBase();
			mDBHelper.openDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void closeDB() {
		this.mDBHelper.close();
	}

	private void execSQL(String sqlQuery) {
		mDBHelper.getmDatabase().execSQL(sqlQuery);
	}

	/**
	 * **************************************************************** Caching
	 */
	public String getCaching(String request){
		this.openAndConnectDB();
		String response = "";
		String query = "SELECT * FROM " + TABLE_CACHING + " WHERE "
				+ Constant.Caching.KEY_REQUEST + " = '%s' " ;
		Cursor mCursor = this.mDBHelper.rawQuery(String.format(query,request));
		if (mCursor.moveToFirst()){
			response = mCursor.getString(2);
		}

		return response;
	}

	public boolean saveCaching(String request, String response, String timeUpdated){
		synchronized (this) {

			openAndConnectDB();

			ContentValues contentValues = new ContentValues();
			contentValues.put(Constant.Caching.KEY_REQUEST, request);
			contentValues.put(Constant.Caching.KEY_RESPONSE, response);
			contentValues.put(Constant.Caching.KEY_TIME_UPDATED, timeUpdated);

			long result = mDBHelper.getmDatabase().insertWithOnConflict(TABLE_CACHING, null,
					contentValues, SQLiteDatabase.CONFLICT_REPLACE);
			closeDB();
			return (result >= 0) ? true : false;
		}
	}

	public HashMap<String,String> getAllCachingTime(){
		this.openAndConnectDB();
		HashMap<String, String> cachingTimes = new HashMap<>();
		String response = "";
		String query = "SELECT * FROM " + TABLE_CACHING ;
		Cursor mCursor = this.mDBHelper.rawQuery(query);
		if (mCursor.moveToFirst()){
			do {
				cachingTimes.put(mCursor.getString(1),mCursor.getString(3));
			}while (mCursor.moveToNext());
		}

		return cachingTimes;
	}

	/**
	 *
	 * set time updated = 0
	 * @param request is url
	 *
	 */
	public synchronized boolean resetCachingTime(String request){
		openAndConnectDB();
		String whereClause = Constant.Caching.KEY_REQUEST + " = '"+ request +"'";
		ContentValues values = new ContentValues();
		values.put(Constant.Caching.KEY_TIME_UPDATED,"0");
		long result = mDBHelper.update(TABLE_CACHING,values,whereClause,null);
		closeDB();
		return (result >= 0) ? true : false;
	}

	public void removeDataInTable( ){
		openAndConnectDB();
		String sql = "DELETE FROM " + TABLE_ENGLISH;
		mDBHelper.getmDatabase().execSQL(sql);
		//mDBHelper.getmDatabase().execSQL("ALTER TABLE foo ADD COLUMN " + IDatabaseConfig.COLUMN_CATEGORY_ID + " String DEFAULT 0");
	}

	/**
	 * get lesson by level
	 */
	public ArrayList<Lesson> getLessonAsLevel(String level) throws JSONException {
		openAndConnectDB();
		ArrayList<Lesson> arr = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE_ENGLISH + " WHERE " + COLUMN_LEVEL + " = '"+ level +"'";
		Lesson lesson;

		QuestionAnswer questionAnswer;
		Cursor cursor = mDBHelper.rawQuery(sql);
		while (cursor.moveToNext()) {
			lesson= new Lesson();
			lesson.setId((int) cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
			lesson.setLevel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LEVEL)));
			lesson.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
			lesson.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW)));
			lesson.setDownload(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DOWNLOAD)) == 1L);
			lesson.setFavorite(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_FAVORITE)) == 1L);
			lesson.setView(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_VIEW)) == 1L);
			lesson.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL_IMAGE)));
			lesson.setAttachment(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINK_URL)));
			lesson.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL_AUDIO)));
			String contents = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENTS));

			JSONArray jArrContents = new JSONArray(contents);
			JSONObject jContent;
			Content content ;
			ArrayList<Content> arrContents  = new ArrayList<>();
			ArrayList<Vocabulary> arrVocabularies = new ArrayList<>();

			//get arrContent
			for (int i = 0; i < jArrContents.length(); i++) {
				jContent = jArrContents.getJSONObject(i);

				content  = new Content();
				if (jContent.toString().contains(ParserUtility.KEY_TEXT)){
					content.setText(jContent.getString(ParserUtility.KEY_TEXT));
				}

				//get question
				if (jContent.toString().contains(ParserUtility.KEY_QUESTION_ANSWER)){
					JSONObject jQuestion = jContent.getJSONObject(ParserUtility.KEY_QUESTION_ANSWER);

					questionAnswer = new Gson().fromJson(jQuestion.toString(), QuestionAnswer.class);
					content.setQuestionAnswer(questionAnswer);
				}

				content.setTime(jContent.getInt(ParserUtility.KEY_TIME));
				arrContents.add(content);
			}

			//get arr voca
//			if (cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VOCABULARY))!=null){
//
//				String vocabulary = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VOCABULARY));
//				if (vocabulary!=null){
//					JSONArray jArrNW = new JSONArray(vocabulary);
//
//					for (int i = 0; i < jArrNW.length(); i++) {
//						jContent = jArrNW.getJSONObject(i);
//						arrVocabularies.add(new Gson().fromJson(jContent.toString(), Vocabulary.class));
//						lesson.setVocabularys(arrVocabularies);
//					}
//				}
//			}


			//lesson.setContents(arrContents);
			arr.add(lesson);
		}
		closeDB();
		cursor.close();
		return arr;
	}


	/**
	 * get lesson by category
	 */
	public ArrayList<Lesson> getLessonAsCategory(String idCategory) throws JSONException {
		openAndConnectDB();
		ArrayList<Lesson> arr = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE_ENGLISH + " WHERE " + COLUMN_CATEGORY_ID + " = '"+ idCategory +"'";
		Lesson lesson;

		QuestionAnswer questionAnswer;
		Cursor cursor = mDBHelper.rawQuery(sql);
		while (cursor.moveToNext()) {
			lesson= new Lesson();
			lesson.setId((int) cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
			lesson.setLevel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LEVEL)));
			lesson.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
			lesson.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW)));
			lesson.setDownload(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DOWNLOAD)) == 1L);
			lesson.setFavorite(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_FAVORITE)) == 1L);
			lesson.setView(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_VIEW)) == 1L);
			lesson.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL_IMAGE)));
			lesson.setAttachment(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINK_URL)));
			lesson.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL_AUDIO)));
			lesson.setCategoryId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)));
			String contents = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENTS));

			JSONArray jArrContents = new JSONArray(contents);
			JSONObject jContent;
			Content content ;
			ArrayList<Content> arrContents  = new ArrayList<>();
			ArrayList<Vocabulary> arrVocabularies = new ArrayList<>();

			//get arrContent
			for (int i = 0; i < jArrContents.length(); i++) {
				jContent = jArrContents.getJSONObject(i);

				content  = new Content();
				if (jContent.toString().contains(ParserUtility.KEY_TEXT)){
					content.setText(jContent.getString(ParserUtility.KEY_TEXT));
				}

				//get question
				if (jContent.toString().contains(ParserUtility.KEY_QUESTION_ANSWER)){
					JSONObject jQuestion = jContent.getJSONObject(ParserUtility.KEY_QUESTION_ANSWER);

					questionAnswer = new Gson().fromJson(jQuestion.toString(), QuestionAnswer.class);
					content.setQuestionAnswer(questionAnswer);
				}

				content.setTime(jContent.getInt(ParserUtility.KEY_TIME));
				arrContents.add(content);
			}

			//get arr voca
//			if (cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VOCABULARY))!=null){
//
//				String vocabulary = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VOCABULARY));
//				if (vocabulary!=null){
//					JSONArray jArrNW = new JSONArray(vocabulary);
//
//					for (int i = 0; i < jArrNW.length(); i++) {
//						jContent = jArrNW.getJSONObject(i);
//						arrVocabularies.add(new Gson().fromJson(jContent.toString(), Vocabulary.class));
//						lesson.setVocabularys(arrVocabularies);
//					}
//				}
//			}


			//lesson.setContents(arrContents);
			arr.add(lesson);
		}
		closeDB();
		cursor.close();
		return arr;
	}

	public ArrayList<Lesson> getLessonByAtt(String att) throws JSONException {
		openAndConnectDB();
		ArrayList<Lesson> arr = new ArrayList<>();

		String sql;
		if (att.equals(Constant.DOWNLOAD)) {
			sql = "SELECT * FROM " + TABLE_ENGLISH + " WHERE " + COLUMN_DOWNLOAD + " =1";
		} else {
			sql = "SELECT * FROM " + TABLE_ENGLISH + " WHERE " + COLUMN_FAVORITE + " =1";
		}
		Cursor cursor = mDBHelper.rawQuery(sql);
		while (cursor.moveToNext()) {
			Lesson lesson = new Lesson();

			lesson.setId((int) cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
			lesson.setLevel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LEVEL)));
			lesson.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
			lesson.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW)));
			lesson.setDownload(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DOWNLOAD)) == 1L);
			lesson.setFavorite(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_FAVORITE)) == 1L);
			lesson.setView(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_VIEW)) == 1L);
			lesson.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL_IMAGE)));
			lesson.setAttachment(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINK_URL)));
			lesson.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL_AUDIO)));
			String contents = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENTS));

			JSONArray jArrContents = new JSONArray(contents);
			JSONObject jContent;
			Content content ;
			QuestionAnswer questionAnswer;
			ArrayList<Content> arrContents = new ArrayList<>();
			ArrayList<Vocabulary> arrVocabularies = new ArrayList<>();
			for (int i = 0; i < jArrContents.length(); i++) {
				jContent = jArrContents.getJSONObject(i);

				content  = new Content();
				if (jContent.toString().contains(ParserUtility.KEY_TEXT)){
					content.setText(jContent.getString(ParserUtility.KEY_TEXT));
				}

				if (jContent.toString().contains(ParserUtility.KEY_QUESTION_ANSWER)){

					questionAnswer = new Gson().fromJson(jContent.getJSONObject(ParserUtility.KEY_QUESTION_ANSWER).toString(), QuestionAnswer.class);
					content.setQuestionAnswer(questionAnswer);
				}

				content.setTime(jContent.getInt(ParserUtility.KEY_TIME));
				arrContents.add(content);
			}

			//get arr voca
//			if (cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VOCABULARY))!=null){
//
//				String vocabulary = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VOCABULARY));
//				if (vocabulary!=null){
//					JSONArray jArrNW = new JSONArray(vocabulary);
//
//					for (int i = 0; i < jArrNW.length(); i++) {
//						jContent = jArrNW.getJSONObject(i);
//						arrVocabularies.add(new Gson().fromJson(jContent.toString(), Vocabulary.class));
//						lesson.setVocabularys(arrVocabularies);
//					}
//				}
//			}
//
//			lesson.setContents(arrContents);
			arr.add(lesson);
		}
		closeDB();
		cursor.close();
		return arr;
	}


	/*
	 * get list favorite or download khi sử dụng table watchlesson
	 * */
	public ArrayList<Lesson> getLessonByFavoriteOrDownload(String att) throws JSONException {
		ArrayList<Integer> listId = getLessonIds(att);
		ArrayList<Lesson> lessons = new ArrayList<>();

		for (int i = 0; i<listId.size(); i++){
			lessons.add(getLessonById(listId.get(i)));
		}
		return lessons;
	}

	/*
	 * get lesson as id in table lesson
	 * */
	public Lesson getLessonById(int id) throws JSONException {
		openAndConnectDB();
		Lesson lesson = new Lesson();
		String sql = "SELECT * FROM " + TABLE_ENGLISH + " WHERE " + COLUMN_ID + " = " + " '"+ id +"'";

		Cursor cursor = mDBHelper.rawQuery(sql);
		while (cursor.moveToNext()) {
			lesson.setId((int) cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
			lesson.setLevel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LEVEL)));
			lesson.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
			lesson.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW)));
			lesson.setDownload(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DOWNLOAD)) == 1L);
			lesson.setFavorite(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_FAVORITE)) == 1L);
			lesson.setView(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_VIEW)) == 1L);
			lesson.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL_IMAGE)));
			lesson.setAttachment(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINK_URL)));
			lesson.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL_AUDIO)));
			String contents = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENTS));

			JSONArray jArrContents = new JSONArray(contents);
			JSONObject jContent;
			Content content;
			QuestionAnswer questionAnswer;
			ArrayList<Content> arrContents = new ArrayList<>();
			ArrayList<Vocabulary> arrVocabularies = new ArrayList<>();
			for (int i = 0; i < jArrContents.length(); i++) {
				jContent = jArrContents.getJSONObject(i);

				content = new Content();
				if (jContent.toString().contains(ParserUtility.KEY_TEXT)) {
					content.setText(jContent.getString(ParserUtility.KEY_TEXT));
				}

				if (jContent.toString().contains(ParserUtility.KEY_QUESTION_ANSWER)) {

					questionAnswer = new Gson().fromJson(jContent.getJSONObject(ParserUtility.KEY_QUESTION_ANSWER).toString(), QuestionAnswer.class);
					content.setQuestionAnswer(questionAnswer);
				}

				content.setTime(jContent.getInt(ParserUtility.KEY_TIME));
				arrContents.add(content);
			}

			//get arr voca
//			if (cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VOCABULARY)) != null) {
//
//				String vocabulary = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VOCABULARY));
//				if (vocabulary != null) {
//					JSONArray jArrNW = new JSONArray(vocabulary);
//
//					for (int i = 0; i < jArrNW.length(); i++) {
//						jContent = jArrNW.getJSONObject(i);
//						arrVocabularies.add(new Gson().fromJson(jContent.toString(), Vocabulary.class));
//						lesson.setVocabularys(arrVocabularies);
//					}
//				}
//			}
//			lesson.setContents(arrContents);
		}
		closeDB();
		cursor.close();
		return lesson;
	}

	/*
	 * get list id lesson from table watchlesson
	 *
	 * */

	public ArrayList<Integer> getLessonIds(String att){
		openAndConnectDB();
		String sql;
		if (att.equals(Constant.DOWNLOAD)) {
			sql = "SELECT * FROM " + TABLE_WATCH + " WHERE " + TABLE_WATCH_COLUMN_DOWNLOAD + " =1";
		} else {
			sql = "SELECT * FROM " + TABLE_WATCH + " WHERE " + TABLE_WATCH_COLUMN_FAVORITE + " =1";
		}
		Cursor cursor = mDBHelper.rawQuery(sql);
		ArrayList<Integer> listIds = new ArrayList<>();
		if (cursor.getCount()>0){
			while (cursor.moveToNext()) {
				listIds.add((int) cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_WATCH_COLUMN_ID)));
			}
		}
		closeDB();
		cursor.close();
		return listIds;
	}


	public void updateDownload(Lesson lesson) {
		openAndConnectDB();
		ContentValues values = new ContentValues();
		values.put(COLUMN_DOWNLOAD, lesson.isDownload() ? 1 : 0);
		String whereClause = COLUMN_ID + " =?";
		String[] whereArgs = {lesson.getId() + ""};
		mDBHelper.update(TABLE_ENGLISH, values, whereClause, whereArgs);
		closeDB();
	}

	public void updateFavorite(Lesson lesson) {
		openAndConnectDB();
		ContentValues values = new ContentValues();
		values.put(COLUMN_FAVORITE, lesson.isFavorite() ? 1 : 0);
		String whereClause = COLUMN_ID + " =?";
		String[] whereArgs = {lesson.getId() + ""};
		mDBHelper.update(TABLE_ENGLISH, values, whereClause, whereArgs);
		closeDB();
	}

	public boolean isFavorite(Lesson lesson) {
		openAndConnectDB();
		String sql = "SELECT " + COLUMN_FAVORITE + " FROM " + TABLE_ENGLISH + " WHERE " + COLUMN_ID + " = " + "'" + lesson.getId() + "'";
		Cursor cursor = mDBHelper.rawQuery(sql);
		boolean dl = false;
		if (cursor.getCount()>0){
			cursor.moveToFirst();
			dl = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_FAVORITE))==1L;
		}
		closeDB();
		cursor.close();
		return dl;
	}


	public boolean saveLessonFrist(Lesson lesson){
		synchronized (this) {

			openAndConnectDB();

			ContentValues contentValues = new ContentValues();
			contentValues.put(COLUMN_ID, lesson.getId());
			contentValues.put(COLUMN_TITLE, lesson.getName());
			contentValues.put(COLUMN_URL_IMAGE, lesson.getImage());
			contentValues.put(COLUMN_URL_AUDIO, lesson.getImage());
			contentValues.put(COLUMN_LINK_URL, lesson.getAttachment());
			contentValues.put(COLUMN_LEVEL, lesson.getLevel());
			contentValues.put(COLUMN_OVERVIEW, lesson.getOverview());

//			Gson gson = new Gson();
//			if (lesson.getArrTranscript()!=null){
//				JsonArray contents = gson.toJsonTree(lesson.getArrTranscript()).getAsJsonArray();
//				contentValues.put(COLUMN_CONTENTS, contents.toString());
//			}
//
//			if (lesson.getVocabularys()!=null){
//				JsonArray arrNW = gson.toJsonTree(lesson.getVocabularys()).getAsJsonArray();
//				contentValues.put(COLUMN_VOCABULARY, arrNW.toString());
//			}

			long result = mDBHelper.getmDatabase().insertWithOnConflict(TABLE_ENGLISH, null,
					contentValues, SQLiteDatabase.CONFLICT_REPLACE);
			closeDB();
			return (result >= 0) ? true : false;
		}
	}


	public boolean saveLesson(Lesson lesson){
		synchronized (this) {

			openAndConnectDB();

			ContentValues contentValues = new ContentValues();
			contentValues.put(COLUMN_ID, lesson.getId());
			contentValues.put(COLUMN_TITLE, lesson.getName());
			contentValues.put(COLUMN_URL_IMAGE, lesson.getImage());
			contentValues.put(COLUMN_URL_AUDIO, lesson.getImage());
			contentValues.put(COLUMN_LINK_URL, lesson.getAttachment());
			contentValues.put(COLUMN_LEVEL, lesson.getLevel());
			contentValues.put(COLUMN_OVERVIEW, lesson.getOverview());
			contentValues.put(COLUMN_DOWNLOAD, lesson.isDownload()? 1:0);
			contentValues.put(COLUMN_FAVORITE, lesson.isFavorite()? 1:0);
			contentValues.put(COLUMN_VIEW, lesson.isView()? 1:0);

//			Gson gson = new Gson();
//			if (lesson.getArrTranscript()!=null){
//				JsonArray contents = gson.toJsonTree(lesson.getArrTranscript()).getAsJsonArray();
//				contentValues.put(COLUMN_CONTENTS, contents.toString());
//			}
//
//			if (lesson.getVocabularys()!=null){
//				JsonArray arrNW = gson.toJsonTree(lesson.getVocabularys()).getAsJsonArray();
//				contentValues.put(COLUMN_VOCABULARY, arrNW.toString());
//			}

			long result = mDBHelper.getmDatabase().insertWithOnConflict(TABLE_ENGLISH, null,
					contentValues, SQLiteDatabase.CONFLICT_REPLACE);
			closeDB();
			return (result >= 0) ? true : false;
		}
	}

	public boolean saveWatchLessonFirst(int idLesson) {
		openAndConnectDB();
		String sql = "SELECT " + COLUMN_ID + " FROM " + TABLE_WATCH + " WHERE " + COLUMN_ID + " = " + "'" + idLesson + "'";
		Cursor cursor = mDBHelper.rawQuery(sql);
		if (cursor.getCount() > 0) {
			closeDB();
			return false;
		}
		ContentValues values = new ContentValues();
		values.put(TABLE_WATCH_COLUMN_ID, idLesson);
		values.put(TABLE_WATCH_COLUMN_VIEW, 0);
		values.put(TABLE_WATCH_COLUMN_DOWNLOAD, 0);
		values.put(TABLE_WATCH_COLUMN_COMPLETE_PERCENT, 0);
		values.put(TABLE_WATCH_COLUMN_FAVORITE, 0);
		values.put(TABLE_WATCH_COLUMN_NUMBER_OF_CORRECT_ANSWERS, 0);
		long i = mDBHelper.insert(TABLE_WATCH, null, values);
		closeDB();
		cursor.close();
		return i > -1;
	}

	public WatchLesson getStatusWatchLesson(int idLesson){
		openAndConnectDB();
		String sql = "SELECT * FROM " + TABLE_WATCH + " WHERE " + TABLE_WATCH_COLUMN_ID + " = " + "'" +idLesson + "'";
		Cursor cursor = mDBHelper.rawQuery(sql);
		WatchLesson watchLesson = new WatchLesson();
		while (cursor.moveToNext()) {
			watchLesson.setFavorite(cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_WATCH_COLUMN_FAVORITE))==1L);
			watchLesson.setDownload(cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_WATCH_COLUMN_DOWNLOAD))==1L);
			watchLesson.setView(cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_WATCH_COLUMN_VIEW))==1L);
			watchLesson.setCompletePercent(cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_WATCH_COLUMN_FAVORITE)));
			watchLesson.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_WATCH_COLUMN_ID)));
			watchLesson.setNumberOfCorrect(cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_WATCH_COLUMN_NUMBER_OF_CORRECT_ANSWERS)));
		}
		closeDB();
		cursor.close();
		return watchLesson;
	}

	public void updateFavoriteLesson(WatchLesson lesson) {
		openAndConnectDB();
		ContentValues values = new ContentValues();
		values.put(TABLE_WATCH_COLUMN_FAVORITE, lesson.isFavorite() ? 1 : 0);
		String whereClause = TABLE_WATCH_COLUMN_ID + " =?";
		String[] whereArgs = {lesson.getId() + ""};
		mDBHelper.update(TABLE_WATCH, values, whereClause, whereArgs);
		closeDB();
	}

	public void updateDownloadLesson(WatchLesson watchLesson) {
		openAndConnectDB();
		ContentValues values = new ContentValues();
		values.put(TABLE_WATCH_COLUMN_DOWNLOAD, watchLesson.isDownload() ? 1 : 0);
		String whereClause = TABLE_WATCH_COLUMN_ID + " =?";
		String[] whereArgs = {watchLesson.getId() + ""};
		mDBHelper.update(TABLE_WATCH, values, whereClause, whereArgs);
		closeDB();
	}

	public void updateViewLesson(WatchLesson lesson) {
		openAndConnectDB();
		ContentValues values = new ContentValues();
		values.put(TABLE_WATCH_COLUMN_VIEW, lesson.isView() ? 1 : 0);
		String whereClause = TABLE_WATCH_COLUMN_ID + " =?";
		String[] whereArgs = {lesson.getId() + ""};
		mDBHelper.update(TABLE_WATCH, values, whereClause, whereArgs);
		closeDB();
	}

	public void updatePercentCompleteLesson(WatchLesson watchLesson) {
		openAndConnectDB();
		ContentValues values = new ContentValues();
		values.put(TABLE_WATCH_COLUMN_COMPLETE_PERCENT, watchLesson.getCompletePercent());
		String whereClause = TABLE_WATCH_COLUMN_ID + " =?";
		String[] whereArgs = {watchLesson.getId() + ""};
		mDBHelper.update(TABLE_WATCH, values, whereClause, whereArgs);
		closeDB();
	}

	public void updateNumberOfCorrectAnswerLesson(WatchLesson watchLesson) {
		openAndConnectDB();
		ContentValues values = new ContentValues();
		values.put(TABLE_WATCH_COLUMN_NUMBER_OF_CORRECT_ANSWERS, watchLesson.getNumberOfCorrect());
		String whereClause = TABLE_WATCH_COLUMN_ID + " =?";
		String[] whereArgs = {watchLesson.getId() + ""};
		mDBHelper.update(TABLE_WATCH, values, whereClause, whereArgs);
		closeDB();
	}


	public void saveNewData(ArrayList<Lesson> arrLesson) {
		for (int i = 0; i < arrLesson.size(); i++) {
			addLesson(arrLesson.get(i));
			saveWatchLessonFirst(arrLesson.get(i).getId());
		}
	}


	public void upDateListLesson(ArrayList<Lesson> arrLesson){
		for (int i = 0; i < arrLesson.size(); i++) {
			upDateLesson(arrLesson.get(i));
		}
	}

	public void upDateLesson(Lesson lesson){
		openAndConnectDB();
		ContentValues values = new ContentValues();
		values.put(COLUMN_LEVEL, lesson.getLevel());
		values.put(COLUMN_TITLE, lesson.getName());
		values.put(COLUMN_OVERVIEW, lesson.getOverview());
		values.put(COLUMN_URL_IMAGE, lesson.getImage());
		values.put(COLUMN_URL_AUDIO, lesson.getImage());
		values.put(COLUMN_LINK_URL, lesson.getAttachment());
		Gson contents = new Gson();
//		values.put(COLUMN_CONTENTS, contents.toJson(lesson.getArrTranscript()));
//		values.put(COLUMN_VOCABULARY, contents.toJson(lesson.getVocabularys()));
		String whereClause = COLUMN_ID + " =?";
		String[] whereArgs = {lesson.getId() + ""};
		mDBHelper.update(TABLE_ENGLISH, values, whereClause, whereArgs);
		closeDB();
	}

	public boolean addLesson(Lesson lesson) {
		openAndConnectDB();
		String sql = "SELECT " + COLUMN_ID + " FROM " + TABLE_ENGLISH + " WHERE " + COLUMN_ID + " = " + "'" + lesson.getId() + "'";
		Cursor cursor = mDBHelper.rawQuery(sql);
		if (cursor.getCount() > 0) {
			closeDB();
			return false;
		}
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, lesson.getId());
		values.put(COLUMN_LEVEL, lesson.getLevel());
		values.put(COLUMN_DOWNLOAD, lesson.isDownload() ? 1 : 0);
		values.put(COLUMN_FAVORITE, lesson.isFavorite() ? 1 : 0);
		values.put(COLUMN_VIEW, lesson.isView() ? 1 : 0);
		values.put(COLUMN_TITLE, lesson.getName());
		values.put(COLUMN_OVERVIEW, lesson.getOverview());
		values.put(COLUMN_URL_IMAGE, lesson.getImage());
		values.put(COLUMN_URL_AUDIO, lesson.getImage());
		values.put(COLUMN_LINK_URL, lesson.getAttachment());
		values.put(COLUMN_CATEGORY_ID, lesson.getCategoryId());
		Gson gson = new Gson();
		//JsonArray contents = gson.toJsonTree(lesson.getArrTranscript()).getAsJsonArray();
		//values.put(COLUMN_CONTENTS, contents.toString());
		//JsonArray arrNW = gson.toJsonTree(lesson.getVocabularys()).getAsJsonArray();
		//values.put(COLUMN_VOCABULARY, arrNW.toString());//
		long i = mDBHelper.insert(TABLE_ENGLISH, null, values);
		closeDB();
		cursor.close();
		return i > -1;
	}

	public void updateView(Lesson lesson) {
		openAndConnectDB();
		ContentValues values = new ContentValues();
		values.put(COLUMN_VIEW, 1);
		String whereClause = COLUMN_ID + " =?";
		String[] whereArgs = {lesson.getId() + ""};
		mDBHelper.update(TABLE_ENGLISH, values, whereClause, whereArgs);
		closeDB();
	}

	public boolean getDownload(Lesson lesson) {
		openAndConnectDB();
		String sql = "SELECT " + COLUMN_DOWNLOAD + " FROM " + TABLE_ENGLISH + " WHERE " + COLUMN_ID + " = " + "'" + lesson.getId() + "'";
		Cursor cursor = mDBHelper.rawQuery(sql);
		boolean dl = false;
		if (cursor.getCount()>0){
			cursor.moveToFirst();
			dl = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DOWNLOAD))==1L;
		}
		closeDB();
		cursor.close();
		return dl;
	}

	public long getRowCount() {
		openAndConnectDB();
		long rowCount  = DatabaseUtils.queryNumEntries( mDBHelper.getmDatabase(), TABLE_ENGLISH);
		closeDB();
		return rowCount;
	}


	public boolean isWatch(Lesson lesson) {
		openAndConnectDB();
		String sql = "SELECT " + COLUMN_VIEW + " FROM " + TABLE_ENGLISH + " WHERE " + COLUMN_ID + " = " + "'" + lesson.getId() + "'";
		Cursor cursor = mDBHelper.rawQuery(sql);
		boolean dl = false;
		if (cursor.getCount()>0){
			cursor.moveToFirst();
			dl = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_VIEW))==1L;
		}
		closeDB();
		cursor.close();
		return dl;
	}


}
