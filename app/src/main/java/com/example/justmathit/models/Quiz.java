package com.example.justmathit.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Quiz implements Parcelable {

    private String quizID, name, questionIDs;

    public Quiz() {}

    public Quiz(String quizID, String name, String questionIDs) {
        this.quizID = quizID;
        this.name = name;
        this.questionIDs = questionIDs;
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestionIDs() {
        return questionIDs;
    }

    public void setQuestionIDs(String questionIDs) {
        this.questionIDs = questionIDs;
    }

    protected Quiz(Parcel in) {
        quizID = in.readString();
        name = in.readString();
        questionIDs = in.readString();
    }

    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(quizID);
        parcel.writeString(name);
        parcel.writeString(questionIDs);
    }
}
