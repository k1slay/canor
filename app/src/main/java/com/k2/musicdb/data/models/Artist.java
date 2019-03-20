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
 * @since 2/3/2019
 */

public class Artist extends DataObject implements Parcelable {

    @SerializedName("alternate_names")
    @Expose
    private List<String> alternateNames = null;

    @SerializedName("api_path")
    @Expose
    private String apiPath;

    @SerializedName("description")
    @Expose
    private Description description;

    @SerializedName("facebook_name")
    @Expose
    private String facebookName;

    @SerializedName("header_image_url")
    @Expose
    private String headerImageUrl;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("instagram_name")
    @Expose
    private String instagramName;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("twitter_name")
    @Expose
    private String twitterName;

    @SerializedName("url")
    @Expose
    private String url;

    Artist() {
        //
    }

    protected Artist(Parcel in) {
        alternateNames = in.createStringArrayList();
        apiPath = in.readString();
        description = in.readParcelable(Description.class.getClassLoader());
        facebookName = in.readString();
        headerImageUrl = in.readString();
        id = in.readInt();
        imageUrl = in.readString();
        instagramName = in.readString();
        name = in.readString();
        twitterName = in.readString();
        url = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public List<String> getAlternateNames() {
        return alternateNames;
    }

    public String getFacebookName() {
        return facebookName;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getInstagramName() {
        return instagramName;
    }

    public String getName() {
        return name;
    }

    public String getTwitterName() {
        return twitterName;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        if (description != null)
            return description.getDescription();
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(alternateNames);
        dest.writeString(apiPath);
        dest.writeParcelable(description, flags);
        dest.writeString(facebookName);
        dest.writeString(headerImageUrl);
        dest.writeInt(id);
        dest.writeString(imageUrl);
        dest.writeString(instagramName);
        dest.writeString(name);
        dest.writeString(twitterName);
        dest.writeString(url);
    }
}
