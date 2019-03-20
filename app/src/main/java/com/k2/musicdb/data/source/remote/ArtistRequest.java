package com.k2.musicdb.data.source.remote;

import com.google.gson.Gson;
import com.k2.musicdb.data.models.Artist;

import javax.inject.Inject;

/**
 * Copyright (C) 2019 K2 CODEWORKS
 * All rights reserved
 *
 * @author Kislay
 * @since 2/5/2019
 */

public class ArtistRequest extends Request<ArtistRequest, Artist> {

    public static final String KEY = "artist";

    @Inject
    public ArtistRequest(GeniusService geniusService, Gson gson) {
        super(geniusService, gson);
    }

    @Override
    Class<Artist> getDataClass() {
        return Artist.class;
    }

    @Override
    String getDataKey() {
        return KEY;
    }

}
