package com.k2.musicdb.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/2/2019
 */
public class Performances implements Parcelable {

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("artists")
    @Expose
    private List<Artist> artists;

    Performances() {
        //
    }

    protected Performances(Parcel in) {
        label = in.readString();
        artists = in.createTypedArrayList(Artist.CREATOR);
    }

    public static final Creator<Performances> CREATOR = new Creator<Performances>() {
        @Override
        public Performances createFromParcel(Parcel in) {
            return new Performances(in);
        }

        @Override
        public Performances[] newArray(int size) {
            return new Performances[size];
        }
    };

    public String getLabel() {
        return label;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeTypedList(artists);
    }
}
