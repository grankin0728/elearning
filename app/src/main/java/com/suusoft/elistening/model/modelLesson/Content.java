package com.suusoft.elistening.model.modelLesson;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Suusoft on 10/16/2017.
 */

public class Content  implements Parcelable{

    private int id;
    private int lessonId;
    private int QuestionId;
    private int time;
    private String text;
    private QuestionAnswer questionAnswer;

    public Content() {
    }

    public Content(int id, int lessonId, int questionId, String text, QuestionAnswer questionAnswer) {
        this.id = id;
        this.lessonId = lessonId;
        QuestionId = questionId;
        this.text = text;
        this.questionAnswer = questionAnswer;
    }

    protected Content(Parcel in) {
        id = in.readInt();
        lessonId = in.readInt();
        QuestionId = in.readInt();
        time = in.readInt();
        text = in.readString();
        questionAnswer = in.readParcelable(QuestionAnswer.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(lessonId);
        dest.writeInt(QuestionId);
        dest.writeInt(time);
        dest.writeString(text);
        dest.writeParcelable(questionAnswer, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

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

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int questionId) {
        QuestionId = questionId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuestionAnswer getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(QuestionAnswer questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
}
