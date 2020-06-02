package com.suusoft.elistening.model.modelLesson;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ASUS on 4/3/2017.
 */

public class Vocabulary implements Parcelable {

    private String image;
    private String word;
    private String mean;
    private String pronunciation;

    public Vocabulary() {
    }

    public Vocabulary(String word, String mean, String pronunciation, String image) {

        this.word = word;
        this.image = image;
        this.mean = mean;
        this.pronunciation = pronunciation;
    }


    protected Vocabulary(Parcel in) {
        word = in.readString();
        mean = in.readString();
        image = in.readString();
        pronunciation = in.readString();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(mean);
        dest.writeString(image);
        dest.writeString(pronunciation);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Vocabulary> CREATOR = new Creator<Vocabulary>() {
        @Override
        public Vocabulary createFromParcel(Parcel in) {
            return new Vocabulary(in);
        }

        @Override
        public Vocabulary[] newArray(int size) {
            return new Vocabulary[size];
        }
    };
}
