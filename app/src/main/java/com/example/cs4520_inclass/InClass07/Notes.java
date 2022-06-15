package com.example.cs4520_inclass.InClass07;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Notes implements Parcelable {
    ArrayList<String> notes;


    public Notes(ArrayList<String> notes) {
        this.notes = notes;
    }

    public Notes() {

    }

    protected Notes(Parcel in) {
        notes = in.createStringArrayList();
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

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "notes=" + notes +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(notes);
    }
}
