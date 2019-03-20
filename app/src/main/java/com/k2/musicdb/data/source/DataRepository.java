package com.k2.musicdb.data.source;

import com.k2.musicdb.data.DataListener;
import com.k2.musicdb.data.models.Album;
import com.k2.musicdb.data.models.Artist;
import com.k2.musicdb.data.models.SearchResponse;
import com.k2.musicdb.data.models.Song;
import com.k2.musicdb.data.source.remote.GeniusDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/6/2019
 */

// TODO: Add local cache
@Singleton
public class DataRepository implements DataSource {

    private GeniusDataSource geniusDataSource;

    @Inject
    public DataRepository(GeniusDataSource geniusDataSource) {
        this.geniusDataSource = geniusDataSource;
    }

    @Override
    public void getSong(@NonNull String id, DataListener<Song> listener) {
        geniusDataSource.getSong(id, listener);
    }

    @Override
    public void getArtist(@NonNull String id, DataListener<Artist> listener) {
        geniusDataSource.getArtist(id, listener);
    }

    @Override
    public void getAlbum(@NonNull String id, DataListener<Album> listener) {
        geniusDataSource.getAlbum(id, listener);
    }

    @Override
    public void getSearchResults(@NonNull String searchTerm, DataListener<SearchResponse> listener) {
        geniusDataSource.getSearchResults(searchTerm, listener);
    }

}
