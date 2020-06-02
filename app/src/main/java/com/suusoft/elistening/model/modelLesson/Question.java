package com.suusoft.elistening.model.modelLesson;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.suusoft.elistening.base.model.BaseModel;

import java.util.ArrayList;

public class Question extends BaseModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("lesson_id")
    @Expose
    private Integer lessonId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("correct_answer")
    @Expose
    private String correctAnswer;
    @SerializedName("sort_order")
    @Expose
    private Object sortOrder;
    @SerializedName("is_correct")
    @Expose
    private Object isCorrect;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("created_user")
    @Expose
    private Object createdUser;
    @SerializedName("application_id")
    @Expose
    private Object applicationId;
    @SerializedName("answer_response")
    @Expose
    private ArrayList<Answer> answerResponse;


    public Question() {

    }

    protected Question(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            lessonId = null;
        } else {
            lessonId = in.readInt();
        }
        image = in.readString();
        question = in.readString();
        answer = in.readString();
        correctAnswer = in.readString();
        createdDate = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
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

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Object getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Object sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Object getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Object isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Object createdUser) {
        this.createdUser = createdUser;
    }

    public Object getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Object applicationId) {
        this.applicationId = applicationId;
    }

    public ArrayList<Answer> getAnswerResponse() {
        return answerResponse;
    }

    public void setAnswerResponse(ArrayList<Answer> answerResponse) {
        this.answerResponse = answerResponse;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        if (lessonId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(lessonId);
        }
        parcel.writeString(image);
        parcel.writeString(question);
        parcel.writeString(answer);
        parcel.writeString(correctAnswer);
        parcel.writeString(createdDate);
    }
}
