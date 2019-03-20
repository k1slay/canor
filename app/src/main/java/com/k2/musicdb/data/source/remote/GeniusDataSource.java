package com.k2.musicdb.data.source.remote;

import com.k2.musicdb.data.DataListener;
import com.k2.musicdb.data.models.Album;
import com.k2.musicdb.data.models.Artist;
import com.k2.musicdb.data.models.SearchResponse;
import com.k2.musicdb.data.models.Song;
import com.k2.musicdb.data.source.DataSource;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/6/2019
 */

public class GeniusDataSource implements DataSource {

    public static final String DESCRIPTION_FORMAT = "plain";
    private static final String QUERY_DESCRIPTION_FORMAT = "text_format";
    private final SongRequest songRequest;
    private final ArtistRequest artistRequest;
    private final AlbumRequest albumRequest;
    private final SearchRequest searchRequest;

    @Inject
    public GeniusDataSource(SongRequest songRequest, ArtistRequest artistRequest, AlbumRequest albumRequest, SearchRequest searchRequest) {
        this.songRequest = songRequest;
        this.artistRequest = artistRequest;
        this.albumRequest = albumRequest;
        this.searchRequest = searchRequest;
    }

    private void addFormatQuery(Map<String, String> queryMap) {
        queryMap.put(QUERY_DESCRIPTION_FORMAT, DESCRIPTION_FORMAT);
    }

    @Override
    public void getSong(@NonNull String id, DataListener<Song> listener) {
        final HashMap<String, String> map = new HashMap<>();
        addFormatQuery(map);
        songRequest.setType(RequestType.SONG)
                .setId(id)
                .setQueryParams(map)
                .fetch(new DataListener<Song>() {
                    @Override
                    public void onSuccess(Song song) {
                        listener.onSuccess(song);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        listener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void getArtist(@NonNull String id, DataListener<Artist> listener) {
        final HashMap<String, String> map = new HashMap<>();
        addFormatQuery(map);
        artistRequest.setType(RequestType.ARTIST)
                .setId(id)
                .setQueryParams(map)
                .fetch(new DataListener<Artist>() {
                    @Override
                    public void onSuccess(Artist artist) {
                        listener.onSuccess(artist);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        listener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void getAlbum(@NonNull String id, DataListener<Album> listener) {
        final HashMap<String, String> map = new HashMap<>();
        addFormatQuery(map);
        albumRequest.setType(RequestType.ALBUM)
                .setId(id)
                .setQueryParams(map)
                .fetch(new DataListener<Album>() {
                    @Override
                    public void onSuccess(Album album) {
                        listener.onSuccess(album);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        listener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void getSearchResults(@NonNull String searchTerm, DataListener<SearchResponse> listener) {
        final HashMap<String, String> map = new HashMap<>();
        map.put(SearchRequest.QUERY_KEY, searchTerm);
        searchRequest.setType(RequestType.SEARCH)
                .setQueryParams(map)
                .fetch(new DataListener<SearchResponse>() {
                    @Override
                    public void onSuccess(SearchResponse searchResponse) {
                        listener.onSuccess(searchResponse);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        listener.onFailure(throwable);
                    }
                });
    }

}
