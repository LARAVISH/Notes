package com.example.notes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Notes implements Parcelable {

    private final String id;
    private final String name;
    private final String message;
    private Date currentDate;

    public Notes(String id, String name, String message, Date currentDate) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.currentDate = currentDate;
    }

    protected Notes(Parcel in) {
        id = in.readString();
        name = in.readString();
        message = in.readString();
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(message);
    }
}
