package com.suusoft.elistening.model.modelLesson;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Suusoft on 10/16/2017.
 */

public class Suggest implements Parcelable {

    private int id, questionId;
    private String image, question;
    private boolean isCorrect;

    public Suggest() {
    }

    protected Suggest(Parcel in) {
        id = in.readInt();
        questionId = in.readInt();
        image = in.readString();
        question = in.readString();
        isCorrect = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(questionId);
        dest.writeString(image);
        dest.writeString(question);
        dest.writeByte((byte) (isCorrect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Suggest> CREATOR = new Creator<Suggest>() {
        @Override
        public Suggest createFromParcel(Parcel in) {
            return new Suggest(in);
        }

        @Override
        public Suggest[] newArray(int size) {
            return new Suggest[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
