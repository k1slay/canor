package com.k2.musicdb.common;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/9/2019
 */

public class BackdropImageStore implements Parcelable {

    @DrawableRes
    private int imageRes = -1;
    private String imageUrl = null;

    public BackdropImageStore() {
        //
    }

    protected BackdropImageStore(Parcel in) {
        imageRes = in.readInt();
        imageUrl = in.readString();
    }

    public static final Creator<BackdropImageStore> CREATOR = new Creator<BackdropImageStore>() {
        @Override
        public BackdropImageStore createFromParcel(Parcel in) {
            return new BackdropImageStore(in);
        }

        @Override
        public BackdropImageStore[] newArray(int size) {
            return new BackdropImageStore[size];
        }
    };

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
        this.imageUrl = null;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        this.imageRes = -1;
    }

    @SuppressLint("ResourceType")
    public void restore(BackdropHolder backdropHolder) {
        if (imageRes > -1) {
            backdropHolder.setBackdrop(imageRes);
        } else if (imageUrl != null) {
            backdropHolder.setBackdrop(imageUrl);
        }
    }

    public boolean needsChange(int resource) {
        return resource != imageRes;
    }

    public boolean needsChange(String url) {
        return imageUrl == null || !imageUrl.equals(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageRes);
        dest.writeString(imageUrl);
    }
}
