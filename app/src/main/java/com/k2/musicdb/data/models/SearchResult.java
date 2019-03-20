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

public class SearchResult implements Parcelable {

    @SerializedName("api_path")
    @Expose
    private String apiPath;

    @SerializedName("full_title")
    @Expose
    private String fullTitle;

    @SerializedName("header_image_thumbnail_url")
    @Expose
    private String headerImageThumbnailUrl;

    @SerializedName("header_image_url")
    @Expose
    private String headerImageUrl;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("lyrics_state")
    @Expose
    private String lyricsState;

    @SerializedName("song_art_image_thumbnail_url")
    @Expose
    private String songArtImageThumbnailUrl;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("title_with_featured")
    @Expose
    private String titleWithFeatured;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("primary_artist")
    @Expose
    private Artist primaryArtist;

    SearchResult() {
        //
    }

    protected SearchResult(Parcel in) {
        apiPath = in.readString();
        fullTitle = in.readString();
        headerImageThumbnailUrl = in.readString();
        headerImageUrl = in.readString();
        id = in.readInt();
        lyricsState = in.readString();
        songArtImageThumbnailUrl = in.readString();
        title = in.readString();
        titleWithFeatured = in.readString();
        url = in.readString();
        primaryArtist = in.readParcelable(Artist.class.getClassLoader());
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    public String getFullTitle() {
        return fullTitle;
    }

    public String getHeaderImageThumbnailUrl() {
        return headerImageThumbnailUrl;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public String getLyricsState() {
        return lyricsState;
    }

    public String getSongArtImageThumbnailUrl() {
        return songArtImageThumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleWithFeatured() {
        return titleWithFeatured;
    }

    public String getUrl() {
        return url;
    }

    public Artist getPrimaryArtist() {
        return primaryArtist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apiPath);
        dest.writeString(fullTitle);
        dest.writeString(headerImageThumbnailUrl);
        dest.writeString(headerImageUrl);
        dest.writeInt(id);
        dest.writeString(lyricsState);
        dest.writeString(songArtImageThumbnailUrl);
        dest.writeString(title);
        dest.writeString(titleWithFeatured);
        dest.writeString(url);
        dest.writeParcelable(primaryArtist, flags);
    }
}
