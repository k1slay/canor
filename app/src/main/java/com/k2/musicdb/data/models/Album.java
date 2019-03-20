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

public class Album extends DataObject implements Parcelable {

    @SerializedName("api_path")
    @Expose
    private String apiPath;

    @SerializedName("cover_art_url")
    @Expose
    private String coverArtUrl;

    @SerializedName("full_title")
    @Expose
    private String fullTitle;

    @SerializedName("header_image_url")
    @Expose
    private String headerImageUrl;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("artist")
    @Expose
    private Artist artist;

    @SerializedName("cover_arts")
    @Expose
    private List<CoverArt> coverArts = null;

    @SerializedName("song_performances")
    @Expose
    private List<Performances> songPerformances = null;

    Album() {
        //
    }

    protected Album(Parcel in) {
        apiPath = in.readString();
        coverArtUrl = in.readString();
        fullTitle = in.readString();
        headerImageUrl = in.readString();
        id = in.readInt();
        name = in.readString();
        releaseDate = in.readString();
        url = in.readString();
        artist = in.readParcelable(Artist.class.getClassLoader());
        coverArts = in.createTypedArrayList(CoverArt.CREATOR);
        songPerformances = in.createTypedArrayList(Performances.CREATOR);
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public String getCoverArtUrl() {
        return coverArtUrl;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getUrl() {
        return url;
    }

    public Artist getArtist() {
        return artist;
    }

    public List<CoverArt> getCoverArts() {
        return coverArts;
    }

    public List<Performances> getSongPerformances() {
        return songPerformances;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apiPath);
        dest.writeString(coverArtUrl);
        dest.writeString(fullTitle);
        dest.writeString(headerImageUrl);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(releaseDate);
        dest.writeString(url);
        dest.writeParcelable(artist, flags);
        dest.writeTypedList(coverArts);
        dest.writeTypedList(songPerformances);
    }

}
