package com.example.justmathit.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Question implements Parcelable {

    private String questionID;
    private String pronun;
    private String option1, option2, option3;
    private String answer;

    public Question() {}

    public Question(String questionID, String pronun, String option1, String option2, String option3, String answer) {
        this.questionID = questionID;
        this.pronun = pronun;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answer = answer;
    }

    protected Question(Parcel in) {
        questionID = in.readString();
        pronun = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        answer = in.readString();
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

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getPronun() {
        return pronun;
    }

    public void setPronun(String pronun) {
        this.pronun = pronun;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(questionID);
        parcel.writeString(pronun);
        parcel.writeString(option1);
        parcel.writeString(option2);
        parcel.writeString(option3);
        parcel.writeString(answer);
    }
}
