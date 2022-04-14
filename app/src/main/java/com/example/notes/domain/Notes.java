package com.example.notes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

public class Notes implements Parcelable {

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
    private final String name;
    private @DrawableRes
    final int icon;

    public Notes(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    protected Notes(Parcel in) {
        name = in.readString();
        icon = in.readInt();
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(icon);
    }
}
