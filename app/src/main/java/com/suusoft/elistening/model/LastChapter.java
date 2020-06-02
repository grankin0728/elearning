package com.suusoft.elistening.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LastChapter implements Parcelable {
        private String chapter_id;
        private String bookId;
        private String title_chapter;
        private String bookMarkIndex;
        private String image_chapter;
        private int type_chapter;
        private String time;
        private String chapterIndex;
        private String attachment;

        public String getChapter_id() {
            return chapter_id;
        }

        public String getBookId() {
            return bookId;
        }

        public String getTitle_chapter() {
            return title_chapter;
        }

        public String getBookMarkIndex() {
            return bookMarkIndex;
        }

        public String getImage_chapter() {
            return image_chapter;
        }

        public int getType_chapter() {
            return type_chapter;
        }

        public String getTime() {
            return time;
        }

        public String getChapterIndex() {
            return chapterIndex;
        }

        public String getAttachment() {
            return attachment;
        }

        @Override
        public String toString() {
            return "LastChapter{" +
                    "chapter_id='" + chapter_id + '\'' +
                    ", bookId='" + bookId + '\'' +
                    ", title_chapter='" + title_chapter + '\'' +
                    ", bookMarkIndex='" + bookMarkIndex + '\'' +
                    ", image_chapter='" + image_chapter + '\'' +
                    ", type_chapter='" + type_chapter + '\'' +
                    ", time='" + time + '\'' +
                    ", chapterIndex='" + chapterIndex + '\'' +
                    ", attachment='" + attachment + '\'' +
                    '}';
        }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.chapter_id);
        dest.writeString(this.bookId);
        dest.writeString(this.title_chapter);
        dest.writeString(this.bookMarkIndex);
        dest.writeString(this.image_chapter);
        dest.writeInt(this.type_chapter);
        dest.writeString(this.time);
        dest.writeString(this.chapterIndex);
        dest.writeString(this.attachment);
    }

    public LastChapter() {
    }

    protected LastChapter(Parcel in) {
        this.chapter_id = in.readString();
        this.bookId = in.readString();
        this.title_chapter = in.readString();
        this.bookMarkIndex = in.readString();
        this.image_chapter = in.readString();
        this.type_chapter = in.readInt();
        this.time = in.readString();
        this.chapterIndex = in.readString();
        this.attachment = in.readString();
    }

    public static final Parcelable.Creator<LastChapter> CREATOR = new Parcelable.Creator<LastChapter>() {
        @Override
        public LastChapter createFromParcel(Parcel source) {
            return new LastChapter(source);
        }

        @Override
        public LastChapter[] newArray(int size) {
            return new LastChapter[size];
        }
    };
}