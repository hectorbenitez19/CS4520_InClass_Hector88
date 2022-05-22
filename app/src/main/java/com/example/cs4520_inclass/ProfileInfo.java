package com.example.cs4520_inclass;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

//HECTOR BENITEZ ASSIGNMENT 2

public class ProfileInfo implements Parcelable {
    String name;
    String email;
    int mood;
    int phoneType;
    int avatar;

   public ProfileInfo(String name, String email, int mood, int avatar, int phonetype) {
        this.name = name;
        this.email = email;
        this.mood = mood;
        this.avatar = avatar;
        this.phoneType = phonetype;
    }

    public ProfileInfo() {

    }

    protected ProfileInfo(Parcel in) {
        name = in.readString();
        email = in.readString();
        mood = in.readInt();
        phoneType = in.readInt();
        avatar = in.readInt();
    }

    public static final Creator<ProfileInfo> CREATOR = new Creator<ProfileInfo>() {
        @Override
        public ProfileInfo createFromParcel(Parcel in) {
            return new ProfileInfo(in);
        }

        @Override
        public ProfileInfo[] newArray(int size) {
            return new ProfileInfo[size];
        }
    };

    @Override
    public String toString() {
        return "ProfileInfo{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mood=" + mood +
                ", phoneType=" + phoneType +
                ", avatar=" + avatar +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeString(name);
       dest.writeString(email);
       dest.writeInt(mood);
       dest.writeInt(phoneType);
       dest.writeInt(avatar);

    }
}
