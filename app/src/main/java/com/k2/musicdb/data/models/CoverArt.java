package com.k2.musicdb.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/3/2019
 */

public class CoverArt implements Parcelable {

    @SerializedName("api_path")
    @Expose
    private String apiPath;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("thumbnail_image_url")
    @Expose
    private String thumbnailImageUrl;

    @SerializedName("url")
    @Expose
    private String url;

    CoverArt() {
        //
    }

    protected CoverArt(Parcel in) {
        apiPath = in.readString();
        id = in.readInt();
        imageUrl = in.readString();
        thumbnailImageUrl = in.readString();
        url = in.readString();
    }

    public static final Creator<CoverArt> CREATOR = new Creator<CoverArt>() {
        @Override
        public CoverArt createFromParcel(Parcel in) {
            return new CoverArt(in);
        }

        @Override
        public CoverArt[] newArray(int size) {
            return new CoverArt[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apiPath);
        dest.writeInt(id);
        dest.writeString(imageUrl);
        dest.writeString(thumbnailImageUrl);
        dest.writeString(url);
    }
}
