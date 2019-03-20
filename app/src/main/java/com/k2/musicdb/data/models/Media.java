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
 * @since 2/2/2019
 */

public class Media implements Parcelable {

    @SerializedName("native_uri")
    @Expose
    private String nativeUri;

    @SerializedName("provider")
    @Expose
    private String provider;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("start")
    @Expose
    private int start;

    public Media() {
        //
    }

    public String getNativeUri() {
        return nativeUri;
    }

    public void setNativeUri(String nativeUri) {
        this.nativeUri = nativeUri;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nativeUri);
        dest.writeString(provider);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeInt(start);
    }

    protected Media(Parcel in) {
        nativeUri = in.readString();
        provider = in.readString();
        type = in.readString();
        url = in.readString();
        start = in.readInt();
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
}
