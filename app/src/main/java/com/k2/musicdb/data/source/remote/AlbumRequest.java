package com.k2.musicdb.data.source.remote;

import com.google.gson.Gson;
import com.k2.musicdb.data.models.Album;

import javax.inject.Inject;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/5/2019
 */

public class AlbumRequest extends Request<AlbumRequest, Album> {

    public static final String KEY = "album";

    @Inject
    public AlbumRequest(GeniusService geniusService, Gson gson) {
        super(geniusService, gson);
    }

    @Override
    Class<Album> getDataClass() {
        return Album.class;
    }

    @Override
    String getDataKey() {
        return KEY;
    }

}
