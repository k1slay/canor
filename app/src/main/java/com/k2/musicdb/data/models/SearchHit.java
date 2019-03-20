package com.k2.musicdb.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Copyright (C) 2019 K2 Codeworks.
 * All rights reserved
 *
 * @author Kislay
 * @since 07/02/19
 */

public class SearchHit implements Parcelable {

    @SerializedName("index")
    @Expose
    private String index;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("result")
    @Expose
    private SearchResult result;

    SearchHit() {
        //
    }

    protected SearchHit(Parcel in) {
        index = in.readString();
        type = in.readString();
        result = in.readParcelable(SearchResult.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(index);
        dest.writeString(type);
        dest.writeParcelable(result, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchHit> CREATOR = new Creator<SearchHit>() {
        @Override
        public SearchHit createFromParcel(Parcel in) {
            return new SearchHit(in);
        }

        @Override
        public SearchHit[] newArray(int size) {
            return new SearchHit[size];
        }
    };

    public String getType() {
        return type;
    }

    public SearchResult getResult() {
        return result;
    }

}
