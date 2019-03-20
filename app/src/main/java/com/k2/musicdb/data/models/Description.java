package com.k2.musicdb.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.k2.musicdb.data.source.remote.GeniusDataSource;

/**
 * Copyright (C) 2019 K2 Codeworks.
 * All rights reserved
 *
 * @author Kislay
 * @since 07/02/19
 */

public class Description implements Parcelable {

    @SerializedName(GeniusDataSource.DESCRIPTION_FORMAT)
    @Expose
    private String description;

    Description() {
        //
    }

    protected Description(Parcel in) {
        description = in.readString();
    }

    public String getDescription() {
        return description;
    }

    public static final Creator<Description> CREATOR = new Creator<Description>() {
        @Override
        public Description createFromParcel(Parcel in) {
            return new Description(in);
        }

        @Override
        public Description[] newArray(int size) {
            return new Description[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
    }
}
