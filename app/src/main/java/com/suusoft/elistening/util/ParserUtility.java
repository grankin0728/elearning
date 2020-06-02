package com.suusoft.elistening.util;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suusoft.elistening.model.modelLesson.Content;
import com.suusoft.elistening.model.modelLesson.QuestionAnswer;
import com.suusoft.elistening.model.modelLesson.Suggest;
import com.suusoft.elistening.model.modelLesson.Vocabulary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class ParserUtility {
	//parse json in colum contents in ib

	//parse question in colum trans in db
	public static String KEY_TEXT = "text";
	public static String KEY_QUESTION_ANSWER = "questionAnswer";
	public static String KEY_TIME = "time";

	private static String KEY_QUESTION_ANSWER_IMAGE = "image";
	private static String KEY_QUESTION_ANSWER_QUESTION = "question";
	private static String KEY_ANSWER = "answer";
	private static String KEY_QUESTION_ANSWER_SUGGEST = "suggests";
	private static String KEY_SUGGEST_QUESTION = "suggests";


	public static ArrayList<Content> parseContent(String contents) throws JSONException {

		JSONArray jArrTran = new JSONArray(contents);
		ArrayList<Content> arrContents = new ArrayList<>();
		JSONObject object;
		Content content = null;
		for (int i = 0; i < jArrTran.length(); i++) {
			object = jArrTran.getJSONObject(i);

			content  = new Content();
			if (object.toString().contains(ParserUtility.KEY_TEXT)){
				content.setText(object.getString(ParserUtility.KEY_TEXT));
			}

			if (object.toString().contains(ParserUtility.KEY_QUESTION_ANSWER)){
				content.setQuestionAnswer(parseQuestion(object.getString(ParserUtility.KEY_QUESTION_ANSWER)));
				Log.e("DbConnection", "QuestionAnswer " + object.getString("QuestionAnswer").toString() );
			}

			content.setTime(object.getInt(ParserUtility.KEY_TIME));
			arrContents.add(content);
		}
		return arrContents;
	}

	private static QuestionAnswer parseQuestion(String jsonQuestion) {
		Gson gson = new GsonBuilder().create();
		QuestionAnswer questionAnswer = gson.fromJson(jsonQuestion, QuestionAnswer.class);
		return questionAnswer;
	}


	public static ArrayList<Vocabulary> parseListVocabulary(String json) throws JSONException {

		JSONObject object;
		ArrayList<Vocabulary> arrVocabularies = new ArrayList<>();
		JSONArray jArrNW = new JSONArray(json);
		for (int i = 0; i < jArrNW.length(); i++) {
			object = jArrNW.getJSONObject(i);
			arrVocabularies.add(new Gson().fromJson(object.toString(), Vocabulary.class));
		}
		return arrVocabularies;
	}




	// get question
	public static QuestionAnswer getQuestion(String json) {
		QuestionAnswer questionAnswer = new QuestionAnswer();

		try {
			JSONObject object = new JSONObject(json);
			if (object.getJSONArray(KEY_QUESTION_ANSWER_SUGGEST)!=null){

				JSONArray jsonArrSuggest = object.getJSONArray(KEY_QUESTION_ANSWER_SUGGEST);

				questionAnswer.setSuggests(parseSuggest(jsonArrSuggest));
			}

			questionAnswer.setImage(object.getString(KEY_QUESTION_ANSWER_IMAGE));
			questionAnswer.setQuestion(object.getString(KEY_QUESTION_ANSWER_QUESTION));
			questionAnswer.setAnswer(object.getString(KEY_ANSWER));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return questionAnswer;
	}

	private static ArrayList<Suggest >  parseSuggest(JSONArray json) throws JSONException {
		ArrayList<Suggest > suggests = new ArrayList<>();
		for (int i=0; i < json.length(); i ++){
			JSONObject jsonObject = (JSONObject) json.get(i);
			suggests.add(new Gson().fromJson(jsonObject.toString(), Suggest.class));
		}
		return suggests;
	}

	public static ArrayList<Content> sortedByTime(ArrayList<Content> trans) {
		ArrayList<Content> tr = new ArrayList<>();
		ArrayList<Integer> times = new ArrayList<>();

		for (Content content : trans){
			times.add(content.getTime()) ;
		}
		Collections.sort(times);

		ArrayList<Integer> timeNotDuplicateItem =  removeDuplicateItems(times);
		for (int a = 0; a < timeNotDuplicateItem.size(); a ++){

			for (int b = 0; b  < trans.size(); b++){
				if (trans.get(b).getTime() == timeNotDuplicateItem.get(a)){

					tr.add(trans.get(b));
				}
			}
		}
		return tr;
	}

	private static ArrayList<Integer> removeDuplicateItems(ArrayList<Integer> times) {
		ArrayList<Integer> arrTime= new ArrayList<>();

		for (int i = 0; i < times.size(); i ++){
			if (i==0) arrTime.add(times.get(i));
			if (i > 0)
				if (times.get(i)!=times.get(i-1)){
					arrTime.add(times.get(i));
				}
		}
		return arrTime;
	}

}
