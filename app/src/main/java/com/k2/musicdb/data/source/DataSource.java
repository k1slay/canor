package com.k2.musicdb.data.source;

import com.k2.musicdb.data.models.Album;
import com.k2.musicdb.data.models.Artist;
import com.k2.musicdb.data.models.Song;
import com.k2.musicdb.data.DataListener;
import com.k2.musicdb.data.models.SearchResponse;

import androidx.annotation.NonNull;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/6/2019
 */

public interface DataSource {

    public void getSong(@NonNull String id, DataListener<Song> listener);

    public void getArtist(@NonNull String id, DataListener<Artist> listener);

    public void getAlbum(@NonNull String id, DataListener<Album> listener);

    public void getSearchResults(@NonNull String searchTerm, DataListener<SearchResponse> listener);

}
