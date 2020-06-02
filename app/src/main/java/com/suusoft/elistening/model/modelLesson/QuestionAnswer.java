package com.suusoft.elistening.model.modelLesson;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;

/**
 * Created by Suusoft on 10/16/2017.
 */

public class QuestionAnswer implements Parcelable{
    private int id, lessonId;
    private String userAnswer;
    private String image, question, answer, explaination, tip, level;
    private ArrayList<Suggest> suggests;

    public QuestionAnswer() {
    }

    protected QuestionAnswer(Parcel in) {
        id = in.readInt();
        lessonId = in.readInt();
        image = in.readString();
        userAnswer = in.readString();
        question = in.readString();
        answer = in.readString();
        explaination = in.readString();
        tip = in.readString();
        level = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(lessonId);
        dest.writeString(image);
        dest.writeString(userAnswer);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(explaination);
        dest.writeString(tip);
        dest.writeString(level);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuestionAnswer> CREATOR = new Creator<QuestionAnswer>() {
        @Override
        public QuestionAnswer createFromParcel(Parcel in) {
            return new QuestionAnswer(in);
        }

        @Override
        public QuestionAnswer[] newArray(int size) {
            return new QuestionAnswer[size];
        }
    };

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public ArrayList<Suggest> getSuggest() {
        return suggests;
    }

    public void setSuggests(ArrayList<Suggest> suggests) {
        this.suggests = suggests;
    }
}
