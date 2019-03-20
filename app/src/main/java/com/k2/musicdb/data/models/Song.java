package com.k2.musicdb.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/2/2019
 */

public class Song extends DataObject implements Parcelable {

    @SerializedName("api_path")
    @Expose
    private String apiPath;

    @SerializedName("apple_music_id")
    @Expose
    private String appleMusicId;

    @SerializedName("apple_music_player_url")
    @Expose
    private String appleMusicPlayerUrl;

    @SerializedName("description")
    @Expose
    private Description description;

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

    @SerializedName("path")
    @Expose
    private String path;

    @SerializedName("recording_location")
    @Expose
    private String recordingLocation;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("song_art_image_thumbnail_url")
    @Expose
    private String songArtImageThumbnailUrl;

    @SerializedName("song_art_image_url")
    @Expose
    private String songArtImageUrl;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("album")
    @Expose
    private Album album;

    @SerializedName("custom_performances")
    @Expose
    private List<Performances> customPerformances = null;

    @SerializedName("featured_artists")
    @Expose
    private List<Artist> featuredArtists = null;

    @SerializedName("media")
    @Expose
    private List<Media> media = null;

    @SerializedName("primary_artist")
    @Expose
    private Artist primaryArtist;

    @SerializedName("producer_artists")
    @Expose
    private List<Artist> producerArtists = null;

    @SerializedName("writer_artists")
    @Expose
    private List<Artist> writers = null;

    Song() {
        //
    }

    protected Song(Parcel in) {
        apiPath = in.readString();
        appleMusicId = in.readString();
        appleMusicPlayerUrl = in.readString();
        description = in.readParcelable(Description.class.getClassLoader());
        fullTitle = in.readString();
        headerImageThumbnailUrl = in.readString();
        headerImageUrl = in.readString();
        id = in.readInt();
        lyricsState = in.readString();
        path = in.readString();
        recordingLocation = in.readString();
        releaseDate = in.readString();
        songArtImageThumbnailUrl = in.readString();
        songArtImageUrl = in.readString();
        title = in.readString();
        url = in.readString();
        album = in.readParcelable(Album.class.getClassLoader());
        customPerformances = in.createTypedArrayList(Performances.CREATOR);
        featuredArtists = in.createTypedArrayList(Artist.CREATOR);
        media = in.createTypedArrayList(Media.CREATOR);
        primaryArtist = in.readParcelable(Artist.class.getClassLoader());
        producerArtists = in.createTypedArrayList(Artist.CREATOR);
        writers = in.createTypedArrayList(Artist.CREATOR);
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getAppleMusicId() {
        return appleMusicId;
    }

    public String getAppleMusicPlayerUrl() {
        return appleMusicPlayerUrl;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public String getHeaderImageThumbnailUrl() {
        return headerImageThumbnailUrl;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public int getId() {
        return id;
    }

    public String getLyricsState() {
        return lyricsState;
    }

    public String getPath() {
        return path;
    }

    public String getRecordingLocation() {
        return recordingLocation;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getSongArtImageThumbnailUrl() {
        return songArtImageThumbnailUrl;
    }

    public String getSongArtImageUrl() {
        return songArtImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Album getAlbum() {
        return album;
    }

    public List<Performances> getCustomPerformances() {
        return customPerformances;
    }

    public List<Artist> getFeaturedArtists() {
        return featuredArtists;
    }

    public List<Media> getMedia() {
        return media;
    }

    public Artist getPrimaryArtist() {
        return primaryArtist;
    }

    public List<Artist> getProducerArtists() {
        return producerArtists;
    }

    public List<Artist> getWriters() {
        return writers;
    }

    public String getDescription() {
        if (description != null)
            return description.getDescription();
        return null;
    }

    @Nullable
    public Media getMediaByProvider(String provider) {
        Media media = null;
        if (getMedia() != null) {
            for (Media m : getMedia()) {
                if (m.getProvider().equals(provider)) {
                    media = m;
                }
            }
        }
        return media;
    }

    @Nullable
    public Media getMediaByType(String type) {
        Media media = null;
        if (getMedia() != null) {
            for (Media m : getMedia()) {
                if (m.getType().equals(type)) {
                    media = m;
                }
            }
        }
        return media;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apiPath);
        dest.writeString(appleMusicId);
        dest.writeString(appleMusicPlayerUrl);
        dest.writeParcelable(description, flags);
        dest.writeString(fullTitle);
        dest.writeString(headerImageThumbnailUrl);
        dest.writeString(headerImageUrl);
        dest.writeInt(id);
        dest.writeString(lyricsState);
        dest.writeString(path);
        dest.writeString(recordingLocation);
        dest.writeString(releaseDate);
        dest.writeString(songArtImageThumbnailUrl);
        dest.writeString(songArtImageUrl);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeParcelable(album, flags);
        dest.writeTypedList(customPerformances);
        dest.writeTypedList(featuredArtists);
        dest.writeTypedList(media);
        dest.writeParcelable(primaryArtist, flags);
        dest.writeTypedList(producerArtists);
        dest.writeTypedList(writers);
    }
}
