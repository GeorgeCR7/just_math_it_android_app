package com.example.justmathit.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Theory implements Parcelable {

    private int theoryID;
    private String theoryContent;

    public Theory() {}

    public Theory(int theoryID, String theoryContent) {
        this.theoryID = theoryID;
        this.theoryContent = theoryContent;
    }

    protected Theory(Parcel in) {
        theoryID = in.readInt();
        theoryContent = in.readString();
    }

    public static final Creator<Theory> CREATOR = new Creator<Theory>() {
        @Override
        public Theory createFromParcel(Parcel in) {
            return new Theory(in);
        }

        @Override
        public Theory[] newArray(int size) {
            return new Theory[size];
        }
    };

    public int getTheoryID() {
        return theoryID;
    }

    public void setTheoryID(int theoryID) {
        this.theoryID = theoryID;
    }

    public String getTheoryContent() {
        return theoryContent;
    }

    public void setTheoryContent(String theoryContent) {
        this.theoryContent = theoryContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(theoryID);
        parcel.writeString(theoryContent);
    }
}
