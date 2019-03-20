package com.k2.musicdb.data.source.remote;

import com.google.gson.Gson;
import com.k2.musicdb.data.models.Song;

import javax.inject.Inject;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/5/2019
 */
public class SongRequest extends Request<SongRequest, Song> {

    public static final String KEY = "song";

    @Inject
    public SongRequest(GeniusService geniusService, Gson gson) {
        super(geniusService, gson);
    }

    @Override
    Class<Song> getDataClass() {
        return Song.class;
    }

    @Override
    String getDataKey() {
        return KEY;
    }

}
